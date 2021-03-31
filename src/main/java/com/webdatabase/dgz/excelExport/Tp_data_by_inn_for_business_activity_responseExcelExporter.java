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

import com.webdatabase.dgz.model.Tp_data_by_inn_for_business_activity_response;


public class Tp_data_by_inn_for_business_activity_responseExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Tp_data_by_inn_for_business_activity_response> listTp_data_by_inn_for_business_activity_responses;
	
	public Tp_data_by_inn_for_business_activity_responseExcelExporter(List<Tp_data_by_inn_for_business_activity_response> listTp_data_by_inn_for_business_activity_responses) {
		this.listTp_data_by_inn_for_business_activity_responses = listTp_data_by_inn_for_business_activity_responses;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines() {
		xssfSheet = xssfWorkbook.createSheet("Tp data by inn for business activity response");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Полное название", style);
		createCell(row, 2, "ИНН", style);
		createCell(row, 3, "Полный адрес", style);
		createCell(row, 4, "Код района", style);
		createCell(row, 5, "Zip", style);
		createCell(row, 6, "Создано", style);
		createCell(row, 7, "Обновлено", style);
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
		
		for(Tp_data_by_inn_for_business_activity_response tp_data_by_inn_for_business_activity_response : listTp_data_by_inn_for_business_activity_responses) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getId(), style);
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getFullName(), style);
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getInn(), style);
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getFullAddress(), style);
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getRayonCode(), style);
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getZip(), style);
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getCreatedAt(), style);
			createCell(row, columnCount++, tp_data_by_inn_for_business_activity_response.getUpdatedAt(), style);
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
