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

import com.webdatabase.dgz.model.Country;

public class CountryExcelUpload {
		public static String TYPE = "application/vnd.openxmlformats-officedocumnet.spreadsheetml.sheet";
		static String [] HEADERs = {"Id", "Название", "Создано", "Оьновлено"};
		static String SHEET = "Countries";
		
		public static boolean hasExcelFormat(MultipartFile file) {
			if(!TYPE.equals(file.getContentType())) {
				return false;
			}
			return true;
		}
		
		public static List<Country> excelToCountries(InputStream iStream){
			try {
				Workbook workbook = new XSSFWorkbook(iStream);
				Sheet sheet = workbook.getSheet(SHEET);
				Iterator<Row> rows = sheet.iterator();
				
				List<Country> countries = new ArrayList<Country>();
				
				int rowNumber = 0;
				while (rows.hasNext()) {
					Row currentRow = (Row) rows.next();
					
					if(rowNumber == 0) {
						rowNumber++;
						continue;
					}
					
					Iterator<Cell> cellsInRow = currentRow.iterator();
					
					Country country = new Country();
					
					int cellIdx = 0;
					while (cellsInRow.hasNext()) {
						Cell currentCell = cellsInRow.next();
						
						switch (cellIdx) {
						case 0:
							country.setId((long) currentCell.getNumericCellValue());
							break;
						case 1:
							country.setName(currentCell.getStringCellValue());
							break;
						case 2:
							country.setCreatedAt(currentCell.getDateCellValue());
							break;
						case 3:
							country.setUpdatedAt(currentCell.getDateCellValue());
						default:
							break;
						}
						cellIdx++;
					}
					countries.add(country);
				}
				workbook.close();
				
				return countries;
			} catch (IOException e) {
				throw new RuntimeException("fail to parse Excel file: "+ e.getMessage());
			}
		}
}
