package com.course.business.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.dto.SectionDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.service.SectionService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public static final String BUSINESS_NAME = "小节";

    @Resource
    private SectionService sectionService;

    /**
     * 查询所有
     */
    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public ResponseDto<?> list(@RequestBody SectionPageDto<SectionDto> sectionPageDto) {
        ResponseDto<SectionPageDto<SectionDto>> responseDto = new ResponseDto<>();
        ValidatorUtil.require(sectionPageDto.getCourseId(), "课程ID");
        ValidatorUtil.require(sectionPageDto.getChapterId(), "大章ID");

        LOG.info("sectionPageDto:{}", sectionPageDto);
        sectionService.list(sectionPageDto);
        responseDto.setContent(sectionPageDto);
        return responseDto;
    }

    /**
     * 新增或更新
     */
    @PostMapping("/save")
    public ResponseDto<?> save(@RequestBody SectionDto sectionDto) {
        LOG.info("sectionDto:{}", sectionDto);
        ResponseDto<SectionDto> responseDto = new ResponseDto<>();

/**
 *下面的异常处理非常局限，使用{@link com.course.business.controller.ControllerExceptionHandler}统一处理异常
 */
        // 保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> delete(@PathVariable("id") String sectionId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        LOG.info("sectionId:{}", sectionId);
        sectionService.delete(sectionId);
        responseDto.setContent(sectionId);
        return responseDto;
    }
}
