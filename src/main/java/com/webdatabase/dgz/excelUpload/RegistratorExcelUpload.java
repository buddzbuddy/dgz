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

import com.webdatabase.dgz.model.Registrator;


public class RegistratorExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Название", "Контактные данные", "Аналоги", "Создано", "Обновлено",};
	static String SHEET = "Registrators";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Registrator> excelToRegistrators(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Registrator> registrators = new ArrayList<Registrator>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Registrator registrator = new Registrator();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		registrator.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		registrator.setName(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		registrator.setContactData(currentCell.getStringCellValue());;
	        	 	break;
	        	case 3:
	        		registrator.setCounterpart((int) currentCell.getNumericCellValue());
	        		break;
	        	case 4:
	        		registrator.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 5:
	        		registrator.setUpdatedAt(currentCell.getDateCellValue());
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        registrators.add(registrator);
	      }

	      workbook.close();

	      return registrators;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
