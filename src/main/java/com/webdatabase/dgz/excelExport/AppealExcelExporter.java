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

import com.webdatabase.dgz.model.Appeal;

public class AppealExcelExporter {
	private XSSFWorkbook xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Appeal> listAppeals;
	
	public AppealExcelExporter(List<Appeal> listAppeals) {
		this.listAppeals = listAppeals;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		xssfSheet = xssfWorkbook.createSheet("Appeals");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Описание", style);
		createCell(row, 2, "Поставщик", style);
		createCell(row, 3, "Закупающая организация", style);
		createCell(row, 4, "Создано", style);
		createCell(row, 5, "Обновлено", style);
		
		
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		xssfSheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if(value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else if(value instanceof Long ) {
			cell.setCellValue((Long) value);
		} else if(value instanceof Date) {
			cell.setCellValue((Date) value);
		} else {
			cell.setCellStyle(style);
		}
		cell.setCellStyle(style);
	}
	
	private void writeDataLines() {
		int rowCount = 1;
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for(Appeal appeal : listAppeals) {
			Row row = xssfSheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, appeal.getId(), style);
			createCell(row, columnCount++, appeal.getDescription(), style);
			createCell(row, columnCount++, appeal.getSupplier(), style);
			createCell(row, columnCount++, appeal.getProcuring_entity(), style);
			createCell(row, columnCount++, appeal.getCreatedAt(), style);
			createCell(row, columnCount++, appeal.getUpdatedAt(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		xssfWorkbook.write(outputStream);
		xssfWorkbook.close();
		
		outputStream.close();
		
	}
	
	
}
