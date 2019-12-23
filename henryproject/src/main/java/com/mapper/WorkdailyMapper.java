package com.mapper;

import java.util.List;

import com.dto.WorkdailyDto;
import com.entity.Workdaily;

public interface WorkdailyMapper {

    List<Workdaily> getWorkdailyById(String userid,String workyear,String workmonth);

    void updateWorkdailyById(WorkdailyDto workdailyDto);

    void insertWorkdailyById(WorkdailyDto workdailyDto);

    void deleteWorkdailyById(String userid,String workyear,String workmonth);

}
