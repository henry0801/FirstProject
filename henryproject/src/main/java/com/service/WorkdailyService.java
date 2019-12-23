package com.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.WorkmonthDto;
import com.entity.Workmonth;
import com.mapper.WorkmonthMapper;

@Service
public class WorkmonthService {

	Logger logger = LoggerFactory.getLogger(WorkmonthService.class);

    @Autowired
    private WorkmonthMapper workmonthMapper;

    private List<WorkmonthDto> convertToDto(List<Workmonth> WorkmonthList) {
        List<WorkmonthDto> resultList = new LinkedList<WorkmonthDto>();
        for (Workmonth entity : WorkmonthList) {
        	WorkmonthDto dto = new WorkmonthDto();
            BeanUtils.copyProperties(entity, dto);
            resultList.add(dto);
        }
        return resultList;
    }

    public List<WorkmonthDto> getWorkmonthById(String userid,String year,String month) {
        List<Workmonth> WorkmonthList = workmonthMapper.getWorkmonthById(userid,year,month);
        List<WorkmonthDto> resultList = convertToDto(WorkmonthList);
        logger.info("Serach The Workmonth Over!");
        return resultList;
    }

    public void updateWorkmonthById(List<WorkmonthDto> workmonths) {
    	for (WorkmonthDto dto : workmonths) {
    		workmonthMapper.updateWorkmonthById(dto);
    		logger.info("Update The Workmonth Over!");
        }
    }

    public void insertWorkmonthById(List<WorkmonthDto> workmonths) {
    	for (WorkmonthDto dto : workmonths) {
    		workmonthMapper.insertWorkmonthById(dto);
    		logger.info("INSERT The Workmonth Over!");
        }
    }

    public void deleteWorkmonthById(String userid, String year, String month) {
    	workmonthMapper.deleteWorkmonthById(userid, year, month);
    	logger.info("DELETE The Workmonth Over!");
    }
}