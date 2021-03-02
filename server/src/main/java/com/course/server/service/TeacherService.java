package com.course.server.service;

import com.course.server.domain.Teacher;
import com.course.server.domain.TeacherExample;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    public void list(PageDto<TeacherDto> pageDto){
        // 分页插件：获取第2页数据，每页5条
        // 对应SQL：limit 2 - 1 ， 5
//        PageHelper.startPage(2, 5);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        TeacherExample teacherexample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherexample);

        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());

        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);

        pageDto.setList(teacherDtoList);
    }

    // 新增/更新
    public void save(TeacherDto teacherDto) {
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        // StringUtils.isEmpty() 已被弃用
        if (StringUtils.hasText(teacherDto.getId())) {
            update(teacher);
        }else{
            insert(teacher);
        }
    }

    // 新增
    private void insert(Teacher teacher) {
        // 设置 短型UUID
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }

    // 更新
    private void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }

    // 删除
    public void delete(String teacherId) {
        teacherMapper.deleteByPrimaryKey(teacherId);
    }
}
