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

import com.webdatabase.dgz.model.Tp_data_by_inn_for_business_activity_response;


public class Tp_data_by_inn_for_business_activity_responseExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Полное название", "ИНН", "Полный адрес", "Код района", "Zip", "Создано", "Обновлено",};
	static String SHEET = "Tp_data_by_inn_for_business_activity_responses";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Tp_data_by_inn_for_business_activity_response> excelToTp_data_by_inn_for_business_activity_responses(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Tp_data_by_inn_for_business_activity_response> tp_data_by_inn_for_business_activity_responses = new ArrayList<Tp_data_by_inn_for_business_activity_response>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Tp_data_by_inn_for_business_activity_response tp_data_by_inn_for_business_activity_response = new Tp_data_by_inn_for_business_activity_response();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		tp_data_by_inn_for_business_activity_response.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		tp_data_by_inn_for_business_activity_response.setFullName(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		tp_data_by_inn_for_business_activity_response.setInn(currentCell.getStringCellValue());
	        	 	break;
	        	case 3:
	        		tp_data_by_inn_for_business_activity_response.setFullAddress(currentCell.getStringCellValue());
	        		break;
	        	case 4:
	        		tp_data_by_inn_for_business_activity_response.setRayonCode(currentCell.getStringCellValue());
	        		break;
	        	case 9:
	        		tp_data_by_inn_for_business_activity_response.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 10:
	        		tp_data_by_inn_for_business_activity_response.setUpdatedAt(currentCell.getDateCellValue());
	        		break;
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        tp_data_by_inn_for_business_activity_responses.add(tp_data_by_inn_for_business_activity_response);
	      }

	      workbook.close();

	      return tp_data_by_inn_for_business_activity_responses;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
