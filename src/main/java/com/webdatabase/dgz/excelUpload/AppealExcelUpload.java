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

import com.webdatabase.dgz.model.Appeal;
import com.webdatabase.dgz.model.Procuring_entity;
import com.webdatabase.dgz.model.Supplier;

public class AppealExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Id", "Описание", "Поставщик", "Закупающая организация", "Создано", "Обновлено"};
	static String SHEET = "Appeals";
	
	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}
	
	public static List<Appeal> excelToAppeals(InputStream iStream){
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			
			List<Appeal> appeals = new ArrayList<Appeal>();
			
			int rowNumber = 0;
			
			while(rows.hasNext()) {
				Row currentRow = rows.next();
				
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				Appeal appeal = new Appeal();
				
				int cellIdx = 0;
				while(cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					
					switch (cellIdx) {
					case 0:
						appeal.setId((long) currentCell.getNumericCellValue());
						break;
					case 1:
						appeal.setDescription(currentCell.getStringCellValue());
						break;
					case 2:
						appeal.setSupplier((Supplier) currentCell.getRichStringCellValue());
						break;
					case 3:
						appeal.setProcuring_entity((Procuring_entity) currentCell.getRichStringCellValue());;
						break;
					case 4:
						appeal.setCreatedAt(currentCell.getDateCellValue());
						break;
					case 5:
						appeal.setUpdatedAt(currentCell.getDateCellValue());
					default:
						break;
					}
					cellIdx++;
				}
				appeals.add(appeal);
			}
			workbook.close();
			
			return appeals;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
