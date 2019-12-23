package com.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dto.TestDto;
import com.dto.WorkdailyDto;
import com.dto.WorkmonthlyDto;
import com.entity.Test;
import com.mapper.TestMapper;
import com.mapper.WorkdailyMapper;
import com.mapper.WorkmonthlyMapper;

@Service
public class TestService {

	Logger logger = LoggerFactory.getLogger(WorkmonthlyService.class);

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private WorkmonthlyMapper workmonthlyMapper;

    @Autowired
    private WorkdailyMapper workdailyMapper;

    @Autowired
    private PlatformTransactionManager txManager;

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

    /**
	 * 明示的トランザクションテスト
	 * @param workmonthlyDto
	 */
	public void transactionTest1(WorkmonthlyDto workmonthlyDto,List<WorkdailyDto> workdailyDtoList) {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("InsertWorkmonthdayData");
		def.setReadOnly(false);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);

		try {
			workmonthlyMapper.insertWorkmonthlyById(workmonthlyDto);
			if (workdailyDtoList !=null && workdailyDtoList.size()>0) {
				for (WorkdailyDto dto : workdailyDtoList) {
		    		workdailyMapper.insertWorkdailyById(dto);
		        }
			}else {
				throw new Exception();
			}

			txManager.commit(status);

		} catch (Exception e) {
			txManager.rollback(status);
			throw new DataAccessException("error! workmonthly test") {
			};
		}
	}

	/**
	 * 宣言的トランザクションテスト
	 * @param workmonthlyDto
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void transactionTest2(WorkmonthlyDto workmonthlyDto,List<WorkdailyDto> workdailyDtoList) throws Exception {

			workmonthlyMapper.insertWorkmonthlyById(workmonthlyDto);
			if (workdailyDtoList !=null && workdailyDtoList.size()>0) {
				for (WorkdailyDto dto : workdailyDtoList) {
		    		workdailyMapper.insertWorkdailyById(dto);
		        }
			}else {
				throw new Exception();
			}

	}

}