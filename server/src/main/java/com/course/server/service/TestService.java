package com.course.server.service;

import com.course.server.domain.Test;
import com.course.server.domain.TestExample;
import com.course.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        TestExample example = new TestExample();
//        example.createCriteria().andIdEqualTo("1");
        example.setOrderByClause("id desc");
        return testMapper.selectByExample(example);
    }
}
