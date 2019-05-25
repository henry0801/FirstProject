package com.mapper;

import java.util.List;

import com.dto.WorkmonthDto;
import com.entity.Workmonth;

public interface WorkmonthMapper {

    List<Workmonth> getWorkmonthById(String userid,String year,String month);

    void updateWorkmonthById(WorkmonthDto sDto);

    void insertWorkmonthById(WorkmonthDto sDto);

    void deleteWorkmonthById(WorkmonthDto sDto);

}
