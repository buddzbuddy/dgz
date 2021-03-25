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

import com.webdatabase.dgz.model.DataSource;

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
	public static List<DataSource> excelToDataSources(InputStream iStream) {
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			
			List<DataSource> dataSources = new ArrayList<DataSource>();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = (Row) rows.next();
				
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				DataSource dataSource = new DataSource();
				
				int cellIndex = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = (Cell) cellsInRow.next();
					
					switch (cellIndex) {
					case 0:
						dataSource.setId((long) currentCell.getNumericCellValue());
						break;
					case 1:
						dataSource.setName(currentCell.getStringCellValue());
						break;
					case 2:
						dataSource.setDescription(currentCell.getStringCellValue());
						break;
					case 3:
						dataSource.setCreatedAt(currentCell.getDateCellValue());
						break;
					case 4:
						dataSource.setUpdatedAt(currentCell.getDateCellValue());
						break;
					default:
						break;
					}
					cellIndex++;
				}
				dataSources.add(dataSource);
			}
			workbook.close();
			
			return dataSources;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
