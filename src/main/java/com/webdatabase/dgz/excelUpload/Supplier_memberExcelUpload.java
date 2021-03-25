package com.webdatabase.dgz.excelUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.model.Member_type;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.model.Supplier_member;


public class Supplier_memberExcelUpload {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Поставщик", "Идентификатор области", "Идентификатор региона", "Регион", "Идентификатор района", "Идентификатор подрайона", "Населенный пункт", "Идентификатор улицы", "Улица", "Идентификатор дома", "Дом", "Фамилия", "Имя", "Отчество", "Дата рождения", "Семейное положение", "Пол", "ИНН", "Национальность", "Паспортный орган", "Серия паспорта", "Номер паспорта", "ПИН", "Идентификатор типа участника", "Тип участника", "Дата выпуска", "Дата истечения", "Статус аннулирования", "Создано", "Обновлено",};
	static String SHEET = "Supplier members";

	public static boolean hasExcelFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	
	    return true;
	}

	public static List<Supplier_member> excelToSupplier_members(InputStream iStream) {
	    try {
	      Workbook workbook = new XSSFWorkbook(iStream);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();

	      List<Supplier_member> supplier_members = new ArrayList<Supplier_member>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Supplier_member supplier_member = new Supplier_member();

	        int cellIndex = 0;
	        while (cellsInRow.hasNext()) {
	        	Cell currentCell = cellsInRow.next();

	        	switch (cellIndex) {
	        	case 0:
	        		supplier_member.setId((long) currentCell.getNumericCellValue());
	        		break;
	        	case 1:
	        		supplier_member.set_supplier((Supplier) currentCell.getRichStringCellValue());
	        		break;
	        	case 2:
	        		supplier_member.setAreaId((int) currentCell.getNumericCellValue());
	        	 	break;
	        	case 3:
	        		supplier_member.setRegionId((int) currentCell.getNumericCellValue());
	        		break;
	        	case 4:
	        		supplier_member.setAddressRegion(currentCell.getStringCellValue());
	        		break;
	        	case 5:
	        		supplier_member.setDistrictId((int) currentCell.getNumericCellValue());
	        		break;
	        	case 6:
	        		supplier_member.setSubareaId((int) currentCell.getNumericCellValue());
	        		break;
	        	case 7:
	        		supplier_member.setAddressLocality(currentCell.getStringCellValue());
	        		break;
	        	case 8:
	        		supplier_member.setStreetId((int) currentCell.getNumericCellValue());
	        		break;
	        	case 9:
	        		supplier_member.setAddressStreet(currentCell.getStringCellValue());
	        		break;
	        	case 10:
	        		supplier_member.setHouseId((int) currentCell.getNumericCellValue());
	        		break;
	        	case 11:
	        		supplier_member.setAddressHouse(currentCell.getStringCellValue());
	        		break;
	        	case 12:
	        		supplier_member.setSurname(currentCell.getStringCellValue());
	        		break;
	        	case 13:
	        		supplier_member.setName(currentCell.getStringCellValue());
	        		break;
	        	case 14:
	        		supplier_member.setPatronymic(currentCell.getStringCellValue());
	        		break;
	        	case 15:
	        		supplier_member.setDateOfBirth(currentCell.getDateCellValue());
	        		break;
	        	case 16:
	        		supplier_member.setFamilyStatus((int) currentCell.getNumericCellValue());
	        		break;
	        	case 17:
	        		supplier_member.setGender(currentCell.getStringCellValue());
	        		break;
	        	case 18:
	        		supplier_member.setInn(currentCell.getStringCellValue());
	        		break;
	        	case 19:
	        		supplier_member.setNationality(currentCell.getStringCellValue());
	        		break;
	        	case 20:
	        		supplier_member.setPassportAuthority(currentCell.getStringCellValue());
	        		break;
	        	case 21:
	        		supplier_member.setPassportNumber(currentCell.getStringCellValue());
	        		break;
	        	case 22:
	        		supplier_member.setPassportSeries(currentCell.getStringCellValue());
	        		break;
	        	case 23:
	        		supplier_member.setPin(currentCell.getStringCellValue());
	        		break;
	        	case 24:
	        		supplier_member.setMemberTypeId((int) currentCell.getNumericCellValue());
	        		break;
	        	case 25:
	        		supplier_member.setMember_type((Member_type) currentCell.getRichStringCellValue());
	        		break;
	        	case 26:
	        		supplier_member.setIssuedDate(currentCell.getDateCellValue());
	        		break;
	        	case 27:
	        		supplier_member.setExpiredDate(currentCell.getDateCellValue());
	        		break;
	        	case 28:
	        		supplier_member.setVoidStatus((int) currentCell.getNumericCellValue());
	        		break;
	        	case 29:
	        		supplier_member.setCreatedAt(currentCell.getDateCellValue());
	        		break;
	        	case 30:
	        		supplier_member.setUpdatedAt(currentCell.getDateCellValue());
	        		break;
	        	default:
	        		break;
	          }

	          cellIndex++;
	        }

	        supplier_members.add(supplier_member);
	      }

	      workbook.close();

	      return supplier_members;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
}
