package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    @Resource
    private ChapterService chapterService;

    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public ResponseDto<?> chapter(@RequestBody PageDto<ChapterDto> pageDto) {
        ResponseDto<PageDto<ChapterDto>> responseDto = new ResponseDto<>();
        LOG.info("pageDto:{}", pageDto);
        chapterService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<?> save(@RequestBody ChapterDto chapterDto) {
        ResponseDto<ChapterDto> responseDto = new ResponseDto<>();
        LOG.info("chapterDto:{}", chapterDto);
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> delete(@PathVariable("id") String chapterId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        LOG.info("chapterId:{}", chapterId);
        chapterService.delete(chapterId);
        responseDto.setContent(chapterId);
        return responseDto;
    }
}
