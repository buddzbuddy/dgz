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

import com.webdatabase.dgz.model.Pension_info;
import com.webdatabase.dgz.model.Supplier_member;


public class Pension_infoExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Категория пенсия", "Дата от начала", "Вид пенсии", "Номер Досье", "Пин пенсионера", "Получатель PIN-кода", "Rusf", "Сумма", "Член поставщика", "Создано", "Обновлено",};
	static String SHEET = "Pension infos";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Pension_info> excelToPension_infos(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Pension_info> pension_infos = new ArrayList<Pension_info>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Pension_info pension_info = new Pension_info();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		pension_info.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		pension_info.setCategoryPension(currentCell.getStringCellValue());
	        		break;
	        	case 2:
	        		pension_info.setDateFromInitial(currentCell.getStringCellValue());;
	        	 	break;
	        	case 3:
	        		pension_info.setKindOfPension(currentCell.getStringCellValue());
	        		break;
	        	case 4:
	        		pension_info.setNumDossier(currentCell.getStringCellValue());
	        		break;
	        	case 5:
	        		pension_info.setPinPensioner(currentCell.getStringCellValue());
	        		break;
	        	case 6:
	        		pension_info.setPinRecipient(currentCell.getStringCellValue());
	        		break;
	        	case 7:
	        		pension_info.setRusf(currentCell.getStringCellValue());
	        		break;
	        	case 8:
	        		pension_info.setSum(currentCell.getStringCellValue());
	        		break;
	        	case 9:
	        		pension_info.setSupplier_member((Supplier_member) currentCell.getRichStringCellValue());
	        		break;
	        	case 10:
	        		pension_info.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 11:
	        		pension_info.setUpdatedAt(currentCell.getDateCellValue());
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        pension_infos.add(pension_info);
	      }

	      workbook.close();

	      return pension_infos;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
