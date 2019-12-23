package com.mapper;

import com.dto.WorkmonthlyDto;
import com.entity.Workmonthly;


public interface WorkmonthlyMapper {

    Workmonthly getWorkmonthlyById(String userid,String workyear,String workmonth);

    void updateWorkmonthlyById(WorkmonthlyDto workmonthlyDto);

    void insertWorkmonthlyById(WorkmonthlyDto workmonthlyDto);

    void deleteWorkmonthlyById(String userid,String workyear,String workmonth);

}
