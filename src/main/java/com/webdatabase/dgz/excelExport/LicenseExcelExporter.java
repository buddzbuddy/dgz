package com.webdatabase.dgz.excelExport;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.webdatabase.dgz.model.License;


public class LicenseExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<License> listLicenses;
	
	public LicenseExcelExporter(List<License> listLicenses) {
		this.listLicenses = listLicenses;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines() {
		xssfSheet = xssfWorkbook.createSheet();
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Номер", style);
		createCell(row, 2, "Поставщик", style);
		createCell(row, 3, "Дополнительная информация", style);
		createCell(row, 4, "Дата истечения срока действия", style);
		createCell(row, 5, "Дата выпуска", style);
		createCell(row, 6, "Эмитент", style);
		createCell(row, 7, "Тип лицензии", style);
		createCell(row, 8, "Статус", style);
		createCell(row, 9, "Создано", style);
		createCell(row, 10, "Обновлено", style);
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		xssfSheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		
		if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if(value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if(value instanceof Date) {
			cell.setCellValue((Date) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	private void writeDataLines() {
		int rowCount = 1;
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for(License license : listLicenses) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, license.getId(), style);
			createCell(row, columnCount++, license.getNo(), style);
			createCell(row, columnCount++, license.getSupplier(), style);
			createCell(row, columnCount++, license.getAdditionalInfo(), style);
			createCell(row, columnCount++, license.getExpiryDate(), style);
			createCell(row, columnCount++, license.getIssueDate(), style);
			createCell(row, columnCount++, license.getIssuer(), style);
			createCell(row, columnCount++, license.getLicense_type(), style);
			createCell(row, columnCount++, license.getStatus(), style);
			createCell(row, columnCount++, license.getCreatedAt(), style);
			createCell(row, columnCount++, license.getUpdatedAt(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLines();
		writeDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		xssfWorkbook.write(outputStream);
		xssfWorkbook.close();
		
		outputStream.close();
	}
}
