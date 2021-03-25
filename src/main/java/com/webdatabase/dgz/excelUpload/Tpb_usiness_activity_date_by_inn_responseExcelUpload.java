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

import com.webdatabase.dgz.model.Tpb_usiness_activity_date_by_inn_response;



public class Tpb_usiness_activity_date_by_inn_responseExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Название", "Юридический адрес", "Код района", "Название района", "Код типа налога", "Название типа налога", "Дата вступления в силу налога", "Tin", "Создано", "Обновлено",};
	static String SHEET = "Tpb_usiness_activity_date_by_inn_responses";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Tpb_usiness_activity_date_by_inn_response> excelToTpb_usiness_activity_date_by_inn_responses(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Tpb_usiness_activity_date_by_inn_response> tpb_usiness_activity_date_by_inn_responses = new ArrayList<Tpb_usiness_activity_date_by_inn_response>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Tpb_usiness_activity_date_by_inn_response tpb_usiness_activity_date_by_inn_response = new Tpb_usiness_activity_date_by_inn_response();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		tpb_usiness_activity_date_by_inn_response.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		tpb_usiness_activity_date_by_inn_response.setName(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		tpb_usiness_activity_date_by_inn_response.setLegalAddress(currentCell.getStringCellValue());
	        	 	break;
	        	case 3:
	        		tpb_usiness_activity_date_by_inn_response.setRayonCode(currentCell.getStringCellValue());
	        		break;
	        	case 4:
	        		tpb_usiness_activity_date_by_inn_response.setRayonName(currentCell.getStringCellValue());
	        		break;
	        	case 5:
	        		tpb_usiness_activity_date_by_inn_response.setTaxTypeCode(currentCell.getStringCellValue());
	        		break;
	        	case 6:
	        		tpb_usiness_activity_date_by_inn_response.setTaxTypeName(currentCell.getStringCellValue());
	        		break;
	        	case 7:
	        		tpb_usiness_activity_date_by_inn_response.setTaxActiveDate(currentCell.getDateCellValue());
	        		break;
	        	case 8:
	        		tpb_usiness_activity_date_by_inn_response.setTin(currentCell.getStringCellValue());
	        		break;
	        	case 9:
	        		tpb_usiness_activity_date_by_inn_response.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 10:
	        		tpb_usiness_activity_date_by_inn_response.setUpdatedAt(currentCell.getDateCellValue());
	        		break;
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        tpb_usiness_activity_date_by_inn_responses.add(tpb_usiness_activity_date_by_inn_response);
	      }

	      workbook.close();

	      return tpb_usiness_activity_date_by_inn_responses;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
