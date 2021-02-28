package com.course.server.service;

import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CourseCategoryService {

    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    public void list(PageDto<CourseCategoryDto> pageDto) {
        // 分页插件：获取第2页数据，每页5条
        // 对应SQL：limit 2 - 1 ， 5
//        PageHelper.startPage(2, 5);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryExample courseCategoryexample = new CourseCategoryExample();
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryexample);

        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CourseCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);

        pageDto.setList(courseCategoryDtoList);
    }

    // 新增/更新
    public void save(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategory.class);
        // StringUtils.isEmpty() 已被弃用
        if (StringUtils.hasText(courseCategoryDto.getId())) {
            update(courseCategory);
        } else {
            insert(courseCategory);
        }
    }

    // 新增
    private void insert(CourseCategory courseCategory) {
        // 设置 短型UUID
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    // 更新
    private void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    // 删除
    public void delete(String courseCategoryId) {
        courseCategoryMapper.deleteByPrimaryKey(courseCategoryId);
    }

    // 批量保存该课程所对应的分类列表信息
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> categoryDtoList) {
        // 先删除该课程所对应的所有分类
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(courseCategoryExample);
        // 再插入新的分类信息
        categoryDtoList.forEach(categoryDto -> {
            CourseCategory courseCategory = new CourseCategory();
            // 在这里 setId() 方法可以省略，下方调用的 insert() 方法中也会 setId()
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        });
    }

    public List<CourseCategoryDto> listCategory(String courseId) {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        return CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
    }
}
