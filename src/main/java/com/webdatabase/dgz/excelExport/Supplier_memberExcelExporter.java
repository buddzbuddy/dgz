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

import com.webdatabase.dgz.model.Supplier_member;


public class Supplier_memberExcelExporter {
	private XSSFWorkbook  xssfWorkbook;
	private XSSFSheet xssfSheet;
	private List<Supplier_member> listSupplier_members;
	
	public Supplier_memberExcelExporter(List<Supplier_member> listSupplier_members) {
		this.listSupplier_members = listSupplier_members;
		xssfWorkbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines() {
		xssfSheet = xssfWorkbook.createSheet("Supplier member");
		
		Row row = xssfSheet.createRow(0);
		
		CellStyle style = xssfWorkbook.createCellStyle();
		XSSFFont font = xssfWorkbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Id", style);
		createCell(row, 2, "Поставщик", style);
		createCell(row, 3, "Идентификатор области", style);
		createCell(row, 4, "Идентификатор региона", style);
		createCell(row, 5, "Регион", style);
		createCell(row, 6, "Идентификатор района", style);
		createCell(row, 7, "Идентификатор подрайона", style);
		createCell(row, 8, "Населенный пункт", style);
		createCell(row, 9, "Идентификатор улицы", style);
		createCell(row, 10, "Улица", style);
		createCell(row, 11, "Идентификатор дома", style);
		createCell(row, 12, "Дом", style);
		createCell(row, 13, "Фамилия", style);
		createCell(row, 14, "Имя", style);
		createCell(row, 15, "Отчество", style);
		createCell(row, 16, "Дата рождения", style);
		createCell(row, 17, "Семейное положение", style);
		createCell(row, 18, "Пол", style);
		createCell(row, 19, "ИНН", style);
		createCell(row, 20, "Национальность", style);
		createCell(row, 21, "Паспортный орган", style);
		createCell(row, 22, "Серия паспорта", style);
		createCell(row, 23, "Номер паспорта", style);
		createCell(row, 24, "ПИН", style);
		createCell(row, 25, "Идентификатор типа участника", style);
		createCell(row, 26, "Тип участника", style);
		createCell(row, 27, "Дата выпуска", style);
		createCell(row, 28, "Дата истечения", style);
		createCell(row, 29, "Статус аннулирования", style);
		createCell(row, 30, "Создано", style);
		createCell(row, 31, "Обновлено", style);
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
		
		for(Supplier_member supplier_member : listSupplier_members) {
			Row row = xssfSheet.createRow(rowCount++);
			
			int columnCount = 0;
			
			createCell(row, columnCount++, supplier_member.getId(), style);
			createCell(row, columnCount++, supplier_member.get_supplier(), style);
			createCell(row, columnCount++, supplier_member.getAreaId(), style);
			createCell(row, columnCount++, supplier_member.getRegionId(), style);
			createCell(row, columnCount++, supplier_member.getAddressRegion(), style);
			createCell(row, columnCount++, supplier_member.getDistrictId(), style);
			createCell(row, columnCount++, supplier_member.getSubareaId(), style);
			createCell(row, columnCount++, supplier_member.getAddressLocality(), style);
			createCell(row, columnCount++, supplier_member.getStreetId(), style);
			createCell(row, columnCount++, supplier_member.getAddressStreet(), style);
			createCell(row, columnCount++, supplier_member.getHouseId(), style);
			createCell(row, columnCount++, supplier_member.getAddressHouse(), style);
			createCell(row, columnCount++, supplier_member.getSurname(), style);
			createCell(row, columnCount++, supplier_member.getName(), style);
			createCell(row, columnCount++, supplier_member.getPatronymic(), style);
			createCell(row, columnCount++, supplier_member.getDateOfBirth(), style);
			createCell(row, columnCount++, supplier_member.getFamilyStatus(), style);
			createCell(row, columnCount++, supplier_member.getGender(), style);
			createCell(row, columnCount++, supplier_member.getInn(), style);
			createCell(row, columnCount++, supplier_member.getNationality(), style);
			createCell(row, columnCount++, supplier_member.getPassportAuthority(), style);
			createCell(row, columnCount++, supplier_member.getPassportNumber(), style);
			createCell(row, columnCount++, supplier_member.getPassportSeries(), style);
			createCell(row, columnCount++, supplier_member.getPin(), style);
			createCell(row, columnCount++, supplier_member.getMemberTypeId(), style);
			createCell(row, columnCount++, supplier_member.getMember_type(), style);
			createCell(row, columnCount++, supplier_member.getIssuedDate(), style);
			createCell(row, columnCount++, supplier_member.getExpiredDate(), style);
			createCell(row, columnCount++, supplier_member.getVoidStatus(), style);
			createCell(row, columnCount++, supplier_member.getCreatedAt(), style);
			createCell(row, columnCount++, supplier_member.getUpdatedAt(), style);
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
