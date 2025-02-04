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

import com.webdatabase.dgz.model.Currency;

public class CurrencyExcelExporter {
	private XSSFWorkbook xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Currency> listCurrencies;
	
	public CurrencyExcelExporter(List<Currency> listCurrencies) {
		this.listCurrencies = listCurrencies;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		xssfSheet = xssfWorkbook.createSheet("Currencies");
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Название", style);
		createCell(row, 2, "Создано", style);
		createCell(row, 3, "Обновлено", style);
		
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
		} else if(value instanceof Long ) {
			cell.setCellValue((Long) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	private void wrireDataLines() {
		int rowCount = 1;
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for(Currency currency : listCurrencies) {
			Row row = xssfSheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, currency.getId(), style);
			createCell(row, columnCount++, currency.getName(), style);
			createCell(row, columnCount++, currency.getCreatedAt(), style);
			createCell(row, columnCount++, currency.getUpdatedAt(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		wrireDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		xssfWorkbook.write(outputStream);
		xssfWorkbook.close();
		
		outputStream.close();
	}
}
