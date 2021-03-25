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

import com.webdatabase.dgz.model.Msec_detail;



public class Msec_detailExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Группа инвалидности", "Дата осмотра", "Тип осмотра", "Повторный осмотр", "От", "До", "Название организации", "Член постащика", "Создано", "Обновлено",};
	static String SHEET = "Msec details";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Msec_detail> excelToMsec_details(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Msec_detail> msec_details = new ArrayList<Msec_detail>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Msec_detail msec_detail = new Msec_detail();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		msec_detail.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		msec_detail.setDisabilityGroup(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		msec_detail.setExaminationDate(currentCell.getDateCellValue());;
	        	 	break;
	        	case 3:
	        		msec_detail.setExaminationtype(currentCell.getStringCellValue());
	        		break;
	        	case 4:
	        		msec_detail.setReExamination(currentCell.getStringCellValue());
	        		break;
	        	case 5:
	        		msec_detail.setFromDate(currentCell.getDateCellValue());
	        		break;
	        	case 6:
	        		msec_detail.setToDate(currentCell.getDateCellValue());
	        		break;
	        	case 7:
	        		msec_detail.setOrgnizationName(currentCell.getStringCellValue());
	        		break;
	        	case 8:
	        		msec_detail.setSupplier_member((int) currentCell.getNumericCellValue());
	        		break;
	        	case 9:
	        		msec_detail.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 10:
	        		msec_detail.setUpdatedAt(currentCell.getDateCellValue());
	        		break;
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        msec_details.add(msec_detail);
	      }

	      workbook.close();

	      return msec_details;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
