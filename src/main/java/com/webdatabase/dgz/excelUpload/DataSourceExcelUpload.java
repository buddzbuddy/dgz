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

import com.webdatabase.dgz.model.Datasource;

public class DataSourceExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Id", "Название", "Описание", "Создано", "Обновлено"};
	static String SHEET = "Data Source";
	
	public static boolean hasExcelFormat(MultipartFile file) {
		if(!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}
	public static List<Datasource> excelToDataSources(InputStream iStream) {
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			
			List<Datasource> datasources = new ArrayList<Datasource>();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = (Row) rows.next();
				
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				Datasource datasource = new Datasource();
				
				int cellIndex = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = (Cell) cellsInRow.next();
					
					switch (cellIndex) {
					case 0:
						datasource.setId((long) currentCell.getNumericCellValue());
						break;
					case 1:
						datasource.setName(currentCell.getStringCellValue());
						break;
					case 2:
						datasource.setDescription(currentCell.getStringCellValue());
						break;
					case 3:
						datasource.setCreatedAt(currentCell.getDateCellValue());
						break;
					case 4:
						datasource.setUpdatedAt(currentCell.getDateCellValue());
						break;
					default:
						break;
					}
					cellIndex++;
				}
				datasources.add(datasource);
			}
			workbook.close();
			
			return datasources;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
