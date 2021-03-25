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

import com.webdatabase.dgz.model.Audit_method_type;

public class Audit_method_typeExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Id", "Код", "Название", "Создано", "Обновлено"};
	static String SHEET = "Audit method types";
	
	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}
	
	public static List<Audit_method_type> excelToAudit_method_types(InputStream iStream) {
		try {
			Workbook workbook = new XSSFWorkbook(iStream);
			
			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			
			List<Audit_method_type> audit_method_types = new ArrayList<Audit_method_type>();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				
				Audit_method_type audit_method_type = new Audit_method_type();
				
				int cellIndex = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					
					switch (cellIndex) {
					case 0:
						audit_method_type.setId((long)currentCell.getNumericCellValue());
						break;
					case 1:
						audit_method_type.setCode(currentCell.getStringCellValue());
						break;
					case 2:
						audit_method_type.setName(currentCell.getStringCellValue());
						break;
					case 3:
						audit_method_type.setCreatedAt(currentCell.getDateCellValue());
						break;
					case 4:
						audit_method_type.setUpdatedAt(currentCell.getDateCellValue());
						break;
					default:
						break;
					}
					cellIndex++;
				}
				audit_method_types.add(audit_method_type);
			}
			workbook.close();
			
			return audit_method_types;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
