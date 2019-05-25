package com.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.StatisticController;
import com.dto.StatisticDto;
import com.entity.Statistic;
import com.mapper.StatisticMapper;

@Service
public class StatisticService {

	Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private StatisticMapper StatisticMappper;

    private List<StatisticDto> convertToDto(List<Statistic> statisticList) {
        List<StatisticDto> resultList = new LinkedList<StatisticDto>();
        for (Statistic entity : statisticList) {
        	StatisticDto dto = new StatisticDto();
            BeanUtils.copyProperties(entity, dto);
            resultList.add(dto);
        }
        return resultList;
    }

    public List<StatisticDto> getAllStatistic() {
        List<Statistic> statisticList = StatisticMappper.getAllStatistic();
        List<StatisticDto> resultList = convertToDto(statisticList);
        logger.info("Serach The Statistic Over!");
        return resultList;
    }

    public void updateStatisticById(List<StatisticDto> statistics) {
    	for (StatisticDto dto : statistics) {
    		StatisticMappper.updateStatisticById(dto);
    		logger.info("Update The Statistic Over!");
        }
    }

    public void insertStatisticById(List<StatisticDto> statistics) {
    	for (StatisticDto dto : statistics) {
    		StatisticMappper.insertStatisticById(dto);
    		logger.info("INSERT The Statistic Over!");
        }
    }

    public void deleteStatisticById(List<StatisticDto> statistics) {
    	for (StatisticDto dto : statistics) {
    		StatisticMappper.deleteStatisticById(dto);
    		logger.info("DELETE The Statistic Over!");
        }
    }

}