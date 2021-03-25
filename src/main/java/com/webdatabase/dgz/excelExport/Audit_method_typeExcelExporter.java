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

import com.webdatabase.dgz.model.Audit_method_type;

public class Audit_method_typeExcelExporter {
	private XSSFWorkbook xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Audit_method_type> listAuditMethodTypes;
	
	public Audit_method_typeExcelExporter(List<Audit_method_type> listAuditMethodTypes) {
		this.listAuditMethodTypes = listAuditMethodTypes;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		xssfSheet = xssfWorkbook.createSheet("Audit Method Types");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "id", style);
		createCell(row, 1, "Код", style);
		createCell(row, 2, "Название", style);
		createCell(row, 3, "Создано", style);
		createCell(row, 4, "Обновлено", style);
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
		font.setFontHeight(16);
		style.setFont(font);
		
		for(Audit_method_type audit_method_type : listAuditMethodTypes) {
			Row row = xssfSheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, audit_method_type.getId(), style);
			createCell(row, columnCount++, audit_method_type.getCode(), style);
			createCell(row, columnCount++, audit_method_type.getName(), style);
			createCell(row, columnCount++, audit_method_type.getCreatedAt(), style);
			createCell(row, columnCount++, audit_method_type.getUpdatedAt(), style);
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
