package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ChapterService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    public static final String BUSINESS_NAME = "大章";

    @Resource
    private ChapterService chapterService;

    /**
     * 查询所有大章
     */
    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public ResponseDto<?> list(@RequestBody ChapterPageDto<ChapterDto> chapterPageDto) {
        ResponseDto<ChapterPageDto<ChapterDto>> responseDto = new ResponseDto<>();
        LOG.info("chapterPageDto:{}", chapterPageDto);
        ValidatorUtil.require(chapterPageDto.getCourseId(), "课程ID");
        chapterService.list(chapterPageDto);
        responseDto.setContent(chapterPageDto);
        return responseDto;
    }

    /**
     * 新增或更新大章
     */
    @PostMapping("/save")
    public ResponseDto<?> save(@RequestBody ChapterDto chapterDto) {
        LOG.info("chapterDto:{}", chapterDto);
        ResponseDto<ChapterDto> responseDto = new ResponseDto<>();

/**
 *下面的异常处理非常局限，使用{@link com.course.business.controller.ControllerExceptionHandler}统一处理异常
 */
//        // 保存校验
//        try {
//            ValidatorUtil.require(chapterDto.getName(), "名称");
//            ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
//            ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);
//        } catch (ValidatorException e) {
//            responseDto.setSuccess(false);
//            responseDto.setMessage(e.getMessage());
//            return responseDto;
//        }

        // 数据校验
        ValidatorUtil.require(chapterDto.getName(), "名称");
        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    /**
     * 删除大章
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> delete(@PathVariable("id") String chapterId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        LOG.info("chapterId:{}", chapterId);
        chapterService.delete(chapterId);
        responseDto.setContent(chapterId);
        return responseDto;
    }
}
