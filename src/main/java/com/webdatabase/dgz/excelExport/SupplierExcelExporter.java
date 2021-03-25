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

import com.webdatabase.dgz.model.Supplier;

public class SupplierExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Supplier> listSuppliers;
	
	public SupplierExcelExporter(List<Supplier> listSuppliers) {
		this.listSuppliers = listSuppliers;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines() {
		xssfSheet = xssfWorkbook.createSheet("Suppliers");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 1, "Название", style);
		createCell(row, 2, "Название банка", style);
		createCell(row, 3, "Банковский счёт", style);
		createCell(row, 4, "Bic", style);
		createCell(row, 5, "Фактический адрес", style);
		createCell(row, 6, "Юридический адрес", style);
		createCell(row, 7, "Промышленность", style);
		createCell(row, 8, "ИНН", style);
		createCell(row, 9, "Черный", style);
		createCell(row, 10, "Резидент", style);
		createCell(row, 11, "Код Района", style);
		createCell(row, 12, "Телефон", style);
		createCell(row, 13, "Zip", style);
		createCell(row, 14, "Тип собственности", style);
		createCell(row, 15, "Создано", style);
		createCell(row, 16, "Обновлено", style);
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
		
		for(Supplier supplier : listSuppliers) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, supplier.getId(), style);
			createCell(row, columnCount++, supplier.getName(), style);
			createCell(row, columnCount++, supplier.getBankName(), style);
			createCell(row, columnCount++, supplier.getBankAccount(), style);
			createCell(row, columnCount++, supplier.getBic(), style);
			createCell(row, columnCount++, supplier.getFactAddress(), style);
			createCell(row, columnCount++, supplier.getLegalAddress(), style);
			createCell(row, columnCount++, supplier.getIndustry(), style);
			createCell(row, columnCount++, supplier.getInn(), style);
			createCell(row, columnCount++, supplier.getIsBlack(), style);
			createCell(row, columnCount++, supplier.getIsResident(), style);
			createCell(row, columnCount++, supplier.getRayonCode(), style);
			createCell(row, columnCount++, supplier.getTelephone(), style);
			createCell(row, columnCount++, supplier.getZip(), style);
			createCell(row, columnCount++, supplier.get_Ownership_type(), style);
			createCell(row, columnCount++, supplier.getCreatedAt(), style);
			createCell(row, columnCount++, supplier.getUpdatedAt(), style);
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
