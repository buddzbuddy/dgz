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

import com.webdatabase.dgz.model.Procuring_entity;


public class Procuring_entityExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Название", "Инн", "Контактные данные", "Адрес", "Создано", "Обновлено",};
	static String SHEET = "Procuring entity";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Procuring_entity> excelToProcuring_entities(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Procuring_entity> procuring_entities = new ArrayList<Procuring_entity>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Procuring_entity procuring_entity = new Procuring_entity();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		procuring_entity.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		procuring_entity.setName(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		procuring_entity.setInn(currentCell.getStringCellValue());
	        	 	break;
	        	case 3:
	        		procuring_entity.setContactData(currentCell.getStringCellValue());
	        		break;
	        	case 4:
	        		procuring_entity.setAddress(currentCell.getStringCellValue());
	        		break;
	        	case 5:
	        		procuring_entity.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 6:
	        		procuring_entity.setUpdatedAt(currentCell.getDateCellValue());
	        		break;
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        procuring_entities.add(procuring_entity);
	      }

	      workbook.close();

	      return procuring_entities;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
