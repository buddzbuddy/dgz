package com.webdatabase.dgz.excelUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.model.Ownership_type;


public class Ownewship_typeExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Id", "Название", "Создано", "Обновлено"};
	static String SHEET = "License types";
	
	public static boolean hasExcelFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}
	
	public static List<Ownership_type> excelToOwnership_types(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Ownership_type> ownership_types = new ArrayList<Ownership_type>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Ownership_type ownership_type = new Ownership_type();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();

	          switch (cellIndex) {
	          case 0:
	            ownership_type.setId((long) currentCell.getNumericCellValue());
	            break;
	          case 1:
	            ownership_type.setName(currentCell.getStringCellValue());
	            break;
	          case 2:
	            ownership_type.setCreatedAt(currentCell.getDateCellValue());
	            break;
	          case 3:
	            ownership_type.setUpdatedAt(currentCell.getDateCellValue());;
	            break;
	          default:
	            break;
	          }

	          cellIndex++;
	        }

	        ownership_types.add(ownership_type);
	      }

	      workbook.close();

	      return ownership_types;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
