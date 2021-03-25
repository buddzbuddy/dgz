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

import com.webdatabase.dgz.model.Industry;
import com.webdatabase.dgz.model.Ownership_type;
import com.webdatabase.dgz.model.Supplier;



public class SupplierExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Название", "Название банка", "Банковский счёт", "Bic", "Фактический адрес", "Юридический адрес", "Промышленность", "ИНН", "Черный", "Резидент", "Код Района", "Телефон", "Zip", "Тип собственности", "Создано", "Обновлено",};
	static String SHEET = "Suppliers";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Supplier> excelToSuppliers(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Supplier> suppliers = new ArrayList<Supplier>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Supplier supplier = new Supplier();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		supplier.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		supplier.setName(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		supplier.setBankName(currentCell.getStringCellValue());
	        	 	break;
	        	case 3:
	        		supplier.setBankAccount(currentCell.getStringCellValue());
	        		break;
	        	case 4:
	        		supplier.setBic(currentCell.getStringCellValue());
	        		break;
	        	case 5:
	        		supplier.setFactAddress(currentCell.getStringCellValue());
	        		break;
	        	case 6:
	        		supplier.setLegalAddress(currentCell.getStringCellValue());
	        		break;
	        	case 7:
	        		supplier.setIndustry((Industry) currentCell.getRichStringCellValue());
	        		break;
	        	case 8:
	        		supplier.setInn(currentCell.getStringCellValue());
	        		break;
	        	case 9:
	        		supplier.setIsBlack(currentCell.getBooleanCellValue());
	        		break;
	        	case 10:
	        		supplier.setIsResident(currentCell.getBooleanCellValue());
	        		break;
	        	case 11:
	        		supplier.setRayonCode(currentCell.getStringCellValue());
	        		break;
	        	case 12:
	        		supplier.setTelephone(currentCell.getStringCellValue());
	        		break;
	        	case 13:
	        		supplier.setZip(currentCell.getStringCellValue());
	        		break;
	        	case 14:
	        		supplier.set_Ownership_type((Ownership_type) currentCell.getRichStringCellValue());
	        		break;
	        	case 15:
	        		supplier.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 16:
	        		supplier.setUpdatedAt(currentCell.getDateCellValue());
	        		break;
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        suppliers.add(supplier);
	      }

	      workbook.close();

	      return suppliers;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
