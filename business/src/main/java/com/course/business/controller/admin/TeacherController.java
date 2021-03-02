package com.course.business.controller.admin;

import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.TeacherService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);
    public static final String BUSINESS_NAME = "讲师";

    @Resource
    private TeacherService teacherService;

    /**
     * 查询所有
     */
    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public ResponseDto<?> list(@RequestBody PageDto<TeacherDto> pageDto) {
        ResponseDto<PageDto<TeacherDto>> responseDto = new ResponseDto<>();
        LOG.info("pageDto:{}", pageDto);
        teacherService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增或更新
     */
    @PostMapping("/save")
    public ResponseDto<?> save(@RequestBody TeacherDto teacherDto) {
        LOG.info("teacherDto:{}", teacherDto);
        ResponseDto<TeacherDto> responseDto = new ResponseDto<>();

/**
 *下面的异常处理非常局限，使用{@link com.course.business.controller.ControllerExceptionHandler}统一处理异常
 */
        // 保存校验
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);
        teacherService.save(teacherDto);
        responseDto.setContent(teacherDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> delete(@PathVariable("id") String teacherId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        LOG.info("teacherId:{}", teacherId);
        teacherService.delete(teacherId);
        responseDto.setContent(teacherId);
        return responseDto;
    }
}
