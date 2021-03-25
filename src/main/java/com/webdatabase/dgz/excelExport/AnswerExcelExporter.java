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

import com.webdatabase.dgz.model.Answer;

public class AnswerExcelExporter {
	private XSSFWorkbook xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Answer> listAnswers;
	
	public AnswerExcelExporter(List<Answer> listAnswers) {
		this.listAnswers = listAnswers;
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
		createCell(row, 1, "Вопросы", style);
		createCell(row, 2, "Текст", style);
		createCell(row, 3, "Создано", style);
		createCell(row, 0, "Обновлено", style);
		
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		xssfSheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if(value instanceof Integer){
			cell.setCellValue((Integer) value);
		} else if(value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if(value instanceof Date){
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
		
		for(Answer answer : listAnswers) {
			Row row = xssfSheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, answer.getId(), style);
			createCell(row, columnCount++, answer.getQuestion(), style);
			createCell(row, columnCount++, answer.getText(), style);
			createCell(row, columnCount++, answer.getCreatedAt(), style);
			createCell(row, columnCount++, answer.getUpdatedAt(), style);
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
