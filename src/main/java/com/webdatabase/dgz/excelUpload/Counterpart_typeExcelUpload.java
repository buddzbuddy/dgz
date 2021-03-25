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

import com.webdatabase.dgz.model.Counterpart_type;

public class Counterpart_typeExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Id", "Название", "Создано", "Обновлено"};
	static String SHEET = "Counterpart types";
	
	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}
	
	public static List<Counterpart_type> excelToCounterpart_types(InputStream iStream) {
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheet(SHEET);
			
			Iterator<Row> rows = sheet.iterator();
			
			List<Counterpart_type> counterpart_types = new ArrayList<Counterpart_type>();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				
				if(rowNumber == 0) {
					rowNumber++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				Counterpart_type counterpart_type = new Counterpart_type();
				
				int cellIndex = 0;
				
				while (cellsInRow.hasNext()) {
					Cell currentCell = (Cell) cellsInRow.next();
					
					switch (cellIndex) {
					case 0:
						counterpart_type.setId((long) currentCell.getNumericCellValue());
						break;
					case 1:
						counterpart_type.setName(currentCell.getStringCellValue());
						break;
					case 2:
						counterpart_type.setCreatedAt(currentCell.getDateCellValue());
						break;
					case 3:
						counterpart_type.setUpdatedAt(currentCell.getDateCellValue());
					default:
						break;
					}
					
					cellIndex++;
				}
				counterpart_types.add(counterpart_type);
			}
			workbook.close();
			return counterpart_types;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file" + e.getMessage());
		}
	}
}
