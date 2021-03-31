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

import com.webdatabase.dgz.model.Tpb_usiness_activity_date_by_inn_response;


public class Tpb_usiness_activity_date_by_inn_responseExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Tpb_usiness_activity_date_by_inn_response> listTpb_usiness_activity_date_by_inn_responses;
	
	public Tpb_usiness_activity_date_by_inn_responseExcelExporter(List<Tpb_usiness_activity_date_by_inn_response> listTpb_usiness_activity_date_by_inn_responses) {
		this.listTpb_usiness_activity_date_by_inn_responses = listTpb_usiness_activity_date_by_inn_responses;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines() {
		xssfSheet = xssfWorkbook.createSheet("Tpb usiness activity date by inn responses");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Название", style);
		createCell(row, 2, "Юридический адрес", style);
		createCell(row, 3, "Код района", style);
		createCell(row, 4, "Название района", style);
		createCell(row, 5, "Код типа налога", style);
		createCell(row, 6, "Название типа налога", style);
		createCell(row, 7, "Дата вступления в силу налога", style);
		createCell(row, 8, "Tin", style);
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
		
		for(Tpb_usiness_activity_date_by_inn_response tpb_usiness_activity_date_by_inn_response : listTpb_usiness_activity_date_by_inn_responses) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getId(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getName(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getLegalAddress(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getRayonCode(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getRayonName(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getTaxTypeCode(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getTaxTypeName(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getTaxActiveDate(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getTin(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getCreatedAt(), style);
			createCell(row, columnCount++, tpb_usiness_activity_date_by_inn_response.getUpdatedAt(), style);
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
