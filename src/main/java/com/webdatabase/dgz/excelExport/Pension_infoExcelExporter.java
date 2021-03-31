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

import com.webdatabase.dgz.model.Pension_info;


public class Pension_infoExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Pension_info> listPension_infos;
	
	public Pension_infoExcelExporter(List<Pension_info> listPension_infos) {
		this.listPension_infos = listPension_infos;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines() {
		xssfSheet = xssfWorkbook.createSheet("Pension infos");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Категория пенсия", style);
		createCell(row, 2, "Дата от начала", style);
		createCell(row, 3, "Вид пенсии", style);
		createCell(row, 4, "Номер Досье", style);
		createCell(row, 5, "Пин пенсионера", style);
		createCell(row, 6, "Получатель PIN-кода", style);
		createCell(row, 7, "Rusf", style);
		createCell(row, 8, "Сумма", style);
		createCell(row, 9, "Член поставщика", style);
		createCell(row, 10, "Создано", style);
		createCell(row, 11, "Обновлено", style);
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
	
	private void writeDataLines() {
		int rowCount = 1;
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for(Pension_info pension_info : listPension_infos) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, pension_info.getId(), style);
			createCell(row, columnCount++, pension_info.getCategoryPension(), style);
			createCell(row, columnCount++, pension_info.getDateFromInitial(), style);
			createCell(row, columnCount++, pension_info.getKindOfPension(), style);
			createCell(row, columnCount++, pension_info.getNumDossier(), style);
			createCell(row, columnCount++, pension_info.getPinPensioner(), style);
			createCell(row, columnCount++, pension_info.getPinRecipient(), style);
			createCell(row, columnCount++, pension_info.getRusf(), style);
			createCell(row, columnCount++, pension_info.getSum(), style);
			createCell(row, columnCount++, pension_info.getSupplier_member(), style);
			createCell(row, columnCount++, pension_info.getCreatedAt(), style);
			createCell(row, columnCount++, pension_info.getUpdatedAt(), style);
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
