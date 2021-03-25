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

public class IndustryExcelUpload {
	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	 static String[] HEADERs = {"Id", "Название", "Создано", "Обновлено"};
	 static String SHEET = "Industries";
	 
	 public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}
	 
	 
	 public static List<Industry> excelToIndustries (InputStream iStream) {
		 try {
			 Workbook workbook = new XSSFWorkbook(iStream);
			 Sheet sheet = workbook.getSheet(SHEET);
			 
			 Iterator<Row> rows = sheet.iterator();
			 
			 List<Industry> industries = new ArrayList<Industry>();
			 
			 int rowNumber = 0;
			 while (rows.hasNext()) {
				Row currentRow = (Row) rows.next();
				
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				Industry industry = new Industry();
				
				int cellIndex = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = (Cell) cellsInRow.next();
					
					switch (cellIndex) {
					case 0:
						industry.setId((long) currentCell.getNumericCellValue());
						break;
					case 1:
						industry.setName(currentCell.getStringCellValue());
						break;
					case 2:
						industry.setCreatedAt(currentCell.getDateCellValue());
						break;
					case 3:
						industry.setUpdatedAt(currentCell.getDateCellValue());
						break;
					default:
						break;
					}
					
					cellIndex++;
					
				}
				
				industries.add(industry);
				
			}
			 workbook.close();
		
			 return industries;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		
		
	}
}
