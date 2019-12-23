package com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.WorkdailyDto;
import com.dto.WorkmonthlyDto;
import com.entity.Workdaily;
import com.entity.Workmonthly;
import com.mapper.WorkdailyMapper;
import com.mapper.WorkmonthlyMapper;

@Service
public class WorkmonthlyService {

	Logger logger = LoggerFactory.getLogger(WorkmonthlyService.class);

	@Autowired
	private WorkmonthlyMapper workmonthlyMapper;

	@Autowired
	private WorkdailyMapper workdailyMapper;

	private WorkmonthlyDto workmonthlyConvertToDto(Workmonthly workmonthly) {
		WorkmonthlyDto workmonthlyDto = new WorkmonthlyDto();
		BeanUtils.copyProperties(workmonthly, workmonthlyDto);
		return workmonthlyDto;
	}

	public WorkmonthlyDto getWorkmonthlyById(String userid, String workyear, String workmonth) {
		Workmonthly workmonthly = workmonthlyMapper.getWorkmonthlyById(userid, workyear, workmonth);
		WorkmonthlyDto result = null;
		if (workmonthly != null)
			result = workmonthlyConvertToDto(workmonthly);

		logger.info("Serach The Workmonthly Over!");
		return result;
	}

	public void updateWorkmonthlyById(WorkmonthlyDto workmonthlyDto) {
		workmonthlyMapper.updateWorkmonthlyById(workmonthlyDto);
		logger.info("Update The Workmonthly Over!");
	}

	public void insertWorkmonthlyById(WorkmonthlyDto workmonthlyDto) {
		workmonthlyMapper.insertWorkmonthlyById(workmonthlyDto);
		logger.info("INSERT The Workmonthly Over!");
	}

	public void deleteWorkmonthlyById(String userid, String workyear, String workmonth) {
		workmonthlyMapper.deleteWorkmonthlyById(userid, workyear, workmonth);
		logger.info("DELETE The Workmonthly Over!");
	}

	@Transactional(rollbackFor = Exception.class)
	public void makeNewWorkMonthDayById(WorkmonthlyDto workmonthlyDto) {

		List<WorkdailyDto> workdailyDtoList_New = workmonthlyDto.getWorkdailyDtoList();
		String userid = workmonthlyDto.getUserid();
		String workyear = workmonthlyDto.getWorkyear();
		String workmonth =workmonthlyDto.getWorkmonth();

		//既存のデータがあれば、削除する
		List<Workdaily> workdailyList = workdailyMapper.getWorkdailyById(userid, workyear, workmonth);
		if (workdailyList != null && workdailyList.size() > 0) {
			workdailyMapper.deleteWorkdailyById(userid, workyear, workmonth);
			workmonthlyMapper.deleteWorkmonthlyById(userid, workyear, workmonth);
		}

		workmonthlyMapper.insertWorkmonthlyById(workmonthlyDto);
		for (WorkdailyDto dto : workdailyDtoList_New) {
			workdailyMapper.insertWorkdailyById(dto);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void updateWorkMonthDayById(WorkmonthlyDto workmonthlyDto) {

		List<WorkdailyDto> workdailyDtoList = workmonthlyDto.getWorkdailyDtoList();
		for (WorkdailyDto dto : workdailyDtoList) {
    		workdailyMapper.updateWorkdailyById(dto);
        }
		workmonthlyMapper.updateWorkmonthlyById(workmonthlyDto);

	}
}