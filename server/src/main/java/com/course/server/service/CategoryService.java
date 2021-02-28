package com.course.server.service;

import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public void list(PageDto<CategoryDto> pageDto){
        // 分页插件：获取第2页数据，每页5条
        // 对应SQL：limit 2 - 1 ， 5
//        PageHelper.startPage(2, 5);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryexample = new CategoryExample();
        categoryexample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryexample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());

        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);

        pageDto.setList(categoryDtoList);
    }

    // 新增/更新
    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);
        // StringUtils.isEmpty() 已被弃用
        if (StringUtils.hasText(categoryDto.getId())) {
            update(category);
        }else{
            insert(category);
        }
    }

    // 新增
    private void insert(Category category) {
        // 设置 短型UUID
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    // 更新
    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    // 删除
    public void delete(String categoryId) {
        categoryMapper.deleteByPrimaryKey(categoryId);
    }
}
