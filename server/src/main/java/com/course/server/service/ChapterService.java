package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    public void list(PageDto<ChapterDto> pageDto){
        // 分页插件：获取第2页数据，每页5条
        // 对应SQL：limit 2 - 1 ， 5
//        PageHelper.startPage(2, 5);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ChapterExample example = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(example);

        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);

        pageDto.setList(chapterDtoList);
    }

    // 新增/更新大章
    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        // StringUtils.isEmpty() 已被弃用
        if (StringUtils.hasText(chapterDto.getId())) {
            update(chapter);
        }else{
            insert(chapter);
        }
    }

    // 新增大章
    private void insert(Chapter chapter) {
        // 设置 短型UUID
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    // 更新大章
    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    // 删除大章
    public void delete(String chapterId) {
        chapterMapper.deleteByPrimaryKey(chapterId);
    }
}
