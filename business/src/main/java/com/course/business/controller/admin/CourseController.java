package com.course.business.controller.admin;

import com.course.server.domain.CourseContent;
import com.course.server.dto.*;
import com.course.server.service.CourseCategoryService;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 查询所有
     */
    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public ResponseDto<?> list(@RequestBody PageDto<CourseDto> pageDto) {
        ResponseDto<PageDto<CourseDto>> responseDto = new ResponseDto<>();
        LOG.info("pageDto:{}", pageDto);
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增或更新
     */
    @PostMapping("/save")
    public ResponseDto<?> save(@RequestBody CourseDto courseDto) {
        LOG.info("courseDto:{}", courseDto);
        ResponseDto<CourseDto> responseDto = new ResponseDto<>();

/**
 *下面的异常处理非常局限，使用{@link com.course.business.controller.ControllerExceptionHandler}统一处理异常
 */
        // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> delete(@PathVariable("id") String courseId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        LOG.info("courseId:{}", courseId);
        courseService.delete(courseId);
        responseDto.setContent(courseId);
        return responseDto;
    }

    // 查询该课程所有分类信息
    @PostMapping("/list-category/{courseId}")
    public ResponseDto<List<CourseCategoryDto>> listCategory(@PathVariable("courseId") String courseId) {
        ResponseDto<List<CourseCategoryDto>> responseDto = new ResponseDto<>();
        List<CourseCategoryDto> courseCategoryDtoList = courseCategoryService.listCategory(courseId);
        responseDto.setContent(courseCategoryDtoList);
        return responseDto;
    }

    // 查询该课程对应的课程内容
    @GetMapping("/find-content/{courseId}")
    public ResponseDto<CourseContentDto> findContent(@PathVariable("courseId") String courseId) {
        ResponseDto<CourseContentDto> responseDto = new ResponseDto<>();
        CourseContentDto courseContentDto = courseService.findContent(courseId);
        responseDto.setContent(courseContentDto);
        return responseDto;
    }

    // 保存该课程对应的课程内容
    @PostMapping("/save-content")
    public ResponseDto<CourseContentDto> saveContent(@RequestBody CourseContentDto courseContentDto) {
        ResponseDto<CourseContentDto> responseDto = new ResponseDto<>();
        int count = courseService.saveContent(courseContentDto);
        responseDto.setSuccess(count == 1);
        return responseDto;
    }

    // 更新课程排序
    @PostMapping("/sort")
    public ResponseDto<SortDto> sort(@RequestBody SortDto sortDto) {
        ResponseDto<SortDto> responseDto = new ResponseDto<>();
        courseService.sort(sortDto);
        return responseDto;
    }
}
