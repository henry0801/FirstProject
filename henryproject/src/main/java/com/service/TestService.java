package com.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.TestDto;
import com.entity.Test;
import com.mapper.TestMapper;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public TestDto getTest(Integer id) {
        TestDto dto = new TestDto();
        Test entity = testMapper.getTest(id);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public List<TestDto> getTestAll() {
        List<Test> testList = testMapper.getTestAll();
        List<TestDto> resultList = convertToDto(testList);
        return resultList;
    }

    private List<TestDto> convertToDto(List<Test> testList) {
        List<TestDto> resultList = new LinkedList<TestDto>();
        for (Test entity : testList) {
            TestDto dto = new TestDto();
            BeanUtils.copyProperties(entity, dto);
            resultList.add(dto);
        }
        return resultList;
    }

}