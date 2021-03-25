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

import com.webdatabase.dgz.model.Msec_detail;

public class Msec_detailExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Msec_detail> listMsec_details;
	
	public Msec_detailExcelExporter(List<Msec_detail> listMsec_details) {
		this.listMsec_details = listMsec_details;
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
		createCell(row, 1, "Группа инвалидности", style);
		createCell(row, 2, "Дата осмотра", style);
		createCell(row, 3, "Тип осмотра", style);
		createCell(row, 4, "Повторный осмотр", style);
		createCell(row, 5, "От", style);
		createCell(row, 6, "До", style);
		createCell(row, 7, "Название организации", style);
		createCell(row, 8, "Член постащика", style);
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
		
		for(Msec_detail msec_detail : listMsec_details) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, msec_detail.getId(), style);
			createCell(row, columnCount++, msec_detail.getDisabilityGroup(), style);
			createCell(row, columnCount++, msec_detail.getExaminationDate(), style);
			createCell(row, columnCount++, msec_detail.getExaminationtype(), style);
			createCell(row, columnCount++, msec_detail.getReExamination(), style);
			createCell(row, columnCount++, msec_detail.getFromDate(), style);
			createCell(row, columnCount++, msec_detail.getToDate(), style);
			createCell(row, columnCount++, msec_detail.getOrgnizationName(), style);
			createCell(row, columnCount++, msec_detail.getSupplier_member(), style);
			createCell(row, columnCount++, msec_detail.getCreatedAt(), style);
			createCell(row, columnCount++, msec_detail.getUpdatedAt(), style);
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
