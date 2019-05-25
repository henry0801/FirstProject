package com.mapper;

import java.util.List;

import com.entity.Test;

public interface TestMapper {
    Test getTest(int userid);
    List<Test> getTestAll();

}
