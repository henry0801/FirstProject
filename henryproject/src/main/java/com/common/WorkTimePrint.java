package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dto.WorkdailyDto;
import com.dto.WorkmonthlyDto;

public class WorkTimePrint {

	Logger logger = LoggerFactory.getLogger(WorkTimePrint.class);


    public Workbook print(WorkmonthlyDto workmonthlyDto) throws IOException {

    	String workyear = workmonthlyDto.getWorkyear();
    	String workmonth = workmonthlyDto.getWorkmonth();
    	String username = workmonthlyDto.getUsername();

    	List<WorkdailyDto> workdailyDtoList = workmonthlyDto.getWorkdailyDtoList();

		Workbook workbook = null;

		String sheetNm = "協力会社社員勤休表";
		workmonth = String.valueOf(Integer.valueOf(workmonth));

		try {
			String filePath = Thread.currentThread().getContextClassLoader().getResource("work.xls").getFile();

			FileInputStream fileInputStream = new FileInputStream(new File(filePath));

			workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheet(sheetNm);

			if (workdailyDtoList !=null && workdailyDtoList.size()>0) {
				for (int i = 0; i < workdailyDtoList.size(); i++) {
					WorkdailyDto workdailyDto = workdailyDtoList.get(i);

					Cell cell_start = sheet.getRow(4+i).getCell(2);
					Cell cell_end = sheet.getRow(4+i).getCell(3);

					Cell cell_workHours = sheet.getRow(4+i).getCell(4);
					Cell cell_overHours = sheet.getRow(4+i).getCell(5);
					Cell cell_addOverHours = sheet.getRow(4+i).getCell(6);

					Cell cell_biko1 = sheet.getRow(4+i).getCell(7);
					Cell cell_biko2 = sheet.getRow(4+i).getCell(8);

					String start = workdailyDto.getStart_h()+":"+workdailyDto.getStart_m();
					String end = workdailyDto.getEnd_h()+":"+workdailyDto.getEnd_m();

					if (!":".equals(start)) {
						double time = DateUtil.convertTime(start);
						cell_start.setCellValue(time);
					}

					if (!":".equals(end)) {
						double time = DateUtil.convertTime(end);
						cell_end.setCellValue(time);
					}

					cell_workHours.setCellValue(String.valueOf(workdailyDto.getWorkhoursday()));
					cell_overHours.setCellValue(String.valueOf(workdailyDto.getOverhoursday()));
					cell_addOverHours.setCellValue(String.valueOf(workdailyDto.getAddoverhoursday()));
					cell_biko1.setCellValue(workdailyDto.getBiko1());
					cell_biko2.setCellValue(workdailyDto.getBiko2());


				}

				sheet.getRow(1).getCell(0).setCellValue(workyear+"年");
				sheet.getRow(1).getCell(2).setCellValue(workmonth+"月");
				sheet.getRow(3).getCell(0).setCellValue(workmonth+"月");
				sheet.getRow(2).getCell(7).setCellValue("作業者氏名　："+username);

				sheet.getRow(35).getCell(4).setCellValue(String.valueOf(workmonthlyDto.getWorkhoursmonth()));
				sheet.getRow(35).getCell(8).setCellValue(String.valueOf(workmonthlyDto.getOverhoursmonth()));
			}

		} catch (FileNotFoundException e) {
			logger.info(e.getMessage());
		} catch (EncryptedDocumentException e) {
			logger.info(e.getMessage());
		} catch (InvalidFormatException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}

		return workbook;
	}



}