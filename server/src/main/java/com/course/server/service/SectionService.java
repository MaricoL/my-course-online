package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import java.util.Date;

@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    public void list(SectionPageDto<SectionDto> sectionPageDto) {
        // 分页插件：获取第2页数据，每页5条
        // 对应SQL：limit 2 - 1 ， 5
//        PageHelper.startPage(2, 5);
        PageHelper.startPage(sectionPageDto.getPage(), sectionPageDto.getSize());
        SectionExample sectionexample = new SectionExample();
        SectionExample.Criteria criteria = sectionexample.createCriteria();
        if (StringUtils.hasText(sectionPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if (StringUtils.hasText(sectionPageDto.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        sectionexample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionexample);

        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageDto.setTotal(pageInfo.getTotal());

        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);

        sectionPageDto.setList(sectionDtoList);
    }

    // 新增/更新
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        // StringUtils.isEmpty() 已被弃用
        if (StringUtils.hasText(sectionDto.getId())) {
            update(section);
        } else {
            insert(section);
        }
    }

    // 新增
    private void insert(Section section) {
        section.setCreatedAt(new Date());
        section.setUpdatedAt(new Date());
        // 设置 短型UUID
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    // 更新
    private void update(Section section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    // 删除
    public void delete(String sectionId) {
        sectionMapper.deleteByPrimaryKey(sectionId);
    }
}
