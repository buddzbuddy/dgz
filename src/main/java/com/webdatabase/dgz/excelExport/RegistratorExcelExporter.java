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

import com.webdatabase.dgz.model.Registrator;


public class RegistratorExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Registrator> listRegistrators;
	
	public RegistratorExcelExporter(List<Registrator> listRegistrators) {
		this.listRegistrators = listRegistrators;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines() {
		xssfSheet = xssfWorkbook.createSheet("Msec details");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Название", style);
		createCell(row, 2, "Контактные данные", style);
		createCell(row, 3, "Аналоги", style);
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
		
		for(Registrator registrator : listRegistrators) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, registrator.getId(), style);
			createCell(row, columnCount++, registrator.getName(), style);
			createCell(row, columnCount++, registrator.getContactData(), style);
			createCell(row, columnCount++, registrator.getCounterpart(), style);
			createCell(row, columnCount++, registrator.getCreatedAt(), style);
			createCell(row, columnCount++, registrator.getUpdatedAt(), style);
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
