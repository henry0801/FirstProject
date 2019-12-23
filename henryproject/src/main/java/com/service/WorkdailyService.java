package com.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.WorkdailyDto;
import com.entity.Workdaily;
import com.mapper.WorkdailyMapper;

@Service
public class WorkdailyService {

	Logger logger = LoggerFactory.getLogger(WorkdailyService.class);

    @Autowired
    private WorkdailyMapper workdailyMapper;

    private List<WorkdailyDto> workdailyConvertToDto(List<Workdaily> WorkdailyList) {
        List<WorkdailyDto> resultList = new LinkedList<WorkdailyDto>();
        for (Workdaily entity : WorkdailyList) {
        	WorkdailyDto dto = new WorkdailyDto();
            BeanUtils.copyProperties(entity, dto);
            resultList.add(dto);
        }
        return resultList;
    }

    public List<WorkdailyDto> getWorkdailyById(String userid,String year,String month) {
        List<Workdaily> WorkdailyList = workdailyMapper.getWorkdailyById(userid,year,month);
        List<WorkdailyDto> resultList = workdailyConvertToDto(WorkdailyList);
        logger.info("Serach The Workdaily Over!");
        return resultList;
    }

    public void updateWorkdailyById(List<WorkdailyDto> workdailyDtoList) {
    	for (WorkdailyDto dto : workdailyDtoList) {
    		workdailyMapper.updateWorkdailyById(dto);
        }
    	logger.info("Update The Workdaily Over!");
    }

    public void insertWorkdailyById(List<WorkdailyDto> workdailyDtoList) {
    	for (WorkdailyDto dto : workdailyDtoList) {
    		workdailyMapper.insertWorkdailyById(dto);
        }
    	logger.info("INSERT The Workdaily Over!");
    }

    public void deleteWorkdailyById(String userid, String workyear, String workmonth) {
    	workdailyMapper.deleteWorkdailyById(userid, workyear, workmonth);
    	logger.info("DELETE The Workdaily Over!");
    }
}