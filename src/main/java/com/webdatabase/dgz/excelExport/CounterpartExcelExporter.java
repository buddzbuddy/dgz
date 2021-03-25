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

import com.webdatabase.dgz.model.Counterpart;

public class CounterpartExcelExporter {
	private XSSFWorkbook xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Counterpart> listCounterparts;
	
	public CounterpartExcelExporter(List<Counterpart> listCounterparts) {
		this.listCounterparts = listCounterparts;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		xssfSheet = xssfWorkbook.createSheet("Counterparts");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Название", style);
		createCell(row, 2, "Тип партнера", style);
		createCell(row, 3, "Контактные данные", style);
		createCell(row, 4, "Адрес", style);
		createCell(row, 5, "Банковские счета", style);
		createCell(row, 6, "Комментарии", style);
		createCell(row, 7, "Создано", style);
		createCell(row, 8, "Обновлено", style);
		
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
		
		for(Counterpart counterpart : listCounterparts) {
			Row row = xssfSheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, counterpart.getId(), style);
			createCell(row, columnCount++, counterpart.getName(), style);
			createCell(row, columnCount++, counterpart.getCounterpart_type(), style);
			createCell(row, columnCount++, counterpart.getContactData(), style);
			createCell(row, columnCount++, counterpart.getAddress(), style);
			createCell(row, columnCount++, counterpart.getBankAccountNo(), style);
			createCell(row, columnCount++, counterpart.getComments(), style);
			createCell(row, columnCount++, counterpart.getCreatedAt(), style);
			createCell(row, columnCount++, counterpart.getUpdatedAt(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		xssfWorkbook.write(outputStream);
		xssfWorkbook.close();
		
		outputStream.close();
	}
}
