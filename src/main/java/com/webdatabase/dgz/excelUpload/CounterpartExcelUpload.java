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

import com.webdatabase.dgz.model.Counterpart;

public class CounterpartExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Id", "Название", "Тип партнера", "Контактные данные", "Адрес", "Банковские счета", "Комментарии", "Создано", "Обновлено"};
	static String SHEET = "Counterpart";
	
	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}
	public static List<Counterpart> excelToCounterparts(InputStream iStream) {
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			
			List<Counterpart> counterparts = new ArrayList<Counterpart>();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = (Row) rows.next();
				
				if(rowNumber == 0) {
					rowNumber++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				Counterpart counterpart = new Counterpart();
				
				int cellIndex = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = (Cell) cellsInRow.next();
					
					switch (cellIndex) {
					case 0:
						counterpart.setId((long) currentCell.getNumericCellValue());
						break;
					case 1:
						counterpart.setName(currentCell.getStringCellValue());
						break;
					case 2:
						counterpart.setCounterpart_type((int) currentCell.getNumericCellValue());
						break;
					case 3:
						counterpart.setContactData(currentCell.getStringCellValue());
						break;
					case 4:
						counterpart.setAddress(currentCell.getStringCellValue());
						break;
					case 5:
						counterpart.setBankAccountNo(currentCell.getStringCellValue());
						break;
					case 6:
						counterpart.setComments(currentCell.getStringCellValue());
						break;
					case 7:
						counterpart.setCreatedAt(currentCell.getDateCellValue());
						break;
					case 8:
						counterpart.setUpdatedAt(currentCell.getDateCellValue());
					default:
					
						break;
					}
					cellIndex++;
				}
				counterparts.add(counterpart);
			}
			workbook.close();
			return counterparts;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
