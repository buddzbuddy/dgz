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

import com.webdatabase.dgz.model.Taxe_code;


public class Taxe_codeExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Id", "Название", "Название детали", "Создано", "Обновлено"};
	static String SHEET = "Taxe codes";
	
	public static boolean hasExcelFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}
	
	public static List<Taxe_code> excelToTaxe_codes(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Taxe_code> taxe_codes = new ArrayList<Taxe_code>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Taxe_code taxe_code = new Taxe_code();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
		          Cell currentCell = cellsInRow.next();
		          switch (cellIndex) {
			          case 0:
				            taxe_code.setId((long) currentCell.getNumericCellValue());
				            break;
			          case 1:
				            taxe_code.setName(currentCell.getStringCellValue());
				            break;
			          case 2:
			        	  taxe_code.setDetailName(currentCell.getStringCellValue());
			        	  break;
			          case 3:
				            taxe_code.setCreatedAt(currentCell.getDateCellValue());
				            break;
			          case 4:
				            taxe_code.setUpdatedAt(currentCell.getDateCellValue());;
				            break;
			          default:
			        	  	break;
			       }	

		          cellIndex++;
	        	}

	        	taxe_codes.add(taxe_code);
	      	}

	      workbook.close();

	      return taxe_codes;
	    } catch (IOException e) {
	    	throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
