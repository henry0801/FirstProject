package com.mapper;

import java.util.List;

import com.dto.StatisticDto;
import com.entity.Statistic;

public interface StatisticMapper {

    List<Statistic> getAllStatistic();

    void updateStatisticById(StatisticDto sDto);

    void insertStatisticById(StatisticDto sDto);

    void deleteStatisticById(StatisticDto sDto);

}
