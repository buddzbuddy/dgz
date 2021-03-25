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

import com.webdatabase.dgz.model.Procuring_entity;



public class Procuring_entityExcelExporter {
	private XSSFWorkbook xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Procuring_entity> listProcuring_entities;
	
	public Procuring_entityExcelExporter(List<Procuring_entity> listProcuring_entities) {
		this.listProcuring_entities = listProcuring_entities;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		xssfSheet = xssfWorkbook.createSheet("Procuring entityes");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Название", style);
		createCell(row, 2, "Инн", style);
		createCell(row, 3, "Контактные данные", style);
		createCell(row, 4, "Адрес", style);
		createCell(row, 5, "Создано", style);
		createCell(row, 6, "Обновлено", style);
		
		
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
		style.setFont(font);
		font.setFontHeight(14);
		
		for(Procuring_entity procuring_entity : listProcuring_entities) {
			Row row = xssfSheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, procuring_entity.getId(), style);
			createCell(row, columnCount++, procuring_entity.getName(), style);
			createCell(row, columnCount++, procuring_entity.getInn(), style);
			createCell(row, columnCount++, procuring_entity.getContactData(), style);
			createCell(row, columnCount++, procuring_entity.getAddress(), style);
			createCell(row, columnCount++, procuring_entity.getCreatedAt(), style);
			createCell(row, columnCount++, procuring_entity.getUpdatedAt(), style);

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
