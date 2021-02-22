package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    @Resource
    private ChapterService chapterService;

    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public PageDto<ChapterDto> chapter(@RequestBody PageDto<ChapterDto> pageDto) {
        LOG.info("pageDto:{}", pageDto);
        chapterService.list(pageDto);
        return pageDto;
    }

    @PostMapping("/save")
    public ChapterDto save(@RequestBody ChapterDto chapterDto) {
        LOG.info("chapterDto:{}", chapterDto);
        chapterService.save(chapterDto);
        return chapterDto;
    }
}
