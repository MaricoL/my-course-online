package com.course.server.service;

import com.course.server.domain.Course;
import com.course.server.domain.CourseContent;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import java.util.Date;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;

    public void list(PageDto<CourseDto> pageDto){
        // 分页插件：获取第2页数据，每页5条
        // 对应SQL：limit 2 - 1 ， 5
//        PageHelper.startPage(2, 5);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseexample = new CourseExample();
        courseexample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseexample);

        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CourseDto> courseDtoList = CopyUtil.copyList(courseList, CourseDto.class);

        pageDto.setList(courseDtoList);
    }

    // 新增/更新
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        // StringUtils.isEmpty() 已被弃用
        if (StringUtils.hasText(courseDto.getId())) {
            update(course);
        }else{
            insert(course);
        }
        // 批量保存课程分类
        courseCategoryService.saveBatch(courseDto.getId(), courseDto.getCategorys());
    }

    // 新增
    private void insert(Course course) {
        course.setCreatedAt(new Date());
        course.setUpdatedAt(new Date());
        // 设置 短型UUID
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    // 更新
    private void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    // 删除
    public void delete(String courseId) {
        courseMapper.deleteByPrimaryKey(courseId);
    }

    // 跟新课程总时长
    public void updateTime(String courseId) {
        myCourseMapper.updateTime(courseId);
    }

    // 查询该课程对应的课程内容
    public CourseContentDto findContent(String courseId) {
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(courseId);
        return CopyUtil.copy(courseContent,CourseContentDto.class);
    }

    // 保存该课程对应的课程内容
    public int saveContent(CourseContentDto courseContentDto) {
        CourseContent courseContent = CopyUtil.copy(courseContentDto, CourseContent.class);
        // 先更新，如果数据库中没有该课程对应的信息，则改为添加
        int count = courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
        if (count == 0) {
            count = courseContentMapper.insert(courseContent);
        }
        return count;
    }
}
