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

import com.webdatabase.dgz.model.License;
import com.webdatabase.dgz.model.License_type;
import com.webdatabase.dgz.model.Supplier;

public class LicenseExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Номер", "Поставщик", "Дополнительная информация", "Дата истечения срока действия", "Дата выпуска", "Эмитент", "Тип лицензии", "Статус", "Создано", "Обновлено",};
	static String SHEET = "Licenses";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<License> excelToLicenses(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<License> licenses = new ArrayList<License>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        License license = new License();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		license.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		license.setNo(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		license.setSupplier((Supplier) currentCell.getRichStringCellValue());
	        	 	break;
	        	case 3:
	        		license.setAdditionalInfo(currentCell.getStringCellValue());
	        		break;
	        	case 4:
	        		license.setExpiryDate(currentCell.getDateCellValue());
	        		break;
	        	case 5:
	        		license.setIssueDate(currentCell.getDateCellValue());
	        		break;
	        	case 6:
	        		license.setIssuer(currentCell.getStringCellValue());
	        		break;
	        	case 7:
	        		license.setLicense_type((License_type) currentCell.getRichStringCellValue());
	        		break;
	        	case 8:
	        		license.setStatus(currentCell.getStringCellValue());
	        		break;
	        	case 9:
	        		license.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 10:
	        		license.setUpdatedAt(currentCell.getDateCellValue());
	        		break;
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        licenses.add(license);
	      }

	      workbook.close();

	      return licenses;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
