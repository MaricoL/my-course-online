package com.course.business.controller.admin;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CategoryService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "分类";

    @Resource
    private CategoryService categoryService;

    /**
     * 查询所有
     */
    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public ResponseDto<?> list(@RequestBody PageDto<CategoryDto> pageDto) {
        ResponseDto<PageDto<CategoryDto>> responseDto = new ResponseDto<>();
        LOG.info("pageDto:{}", pageDto);
        categoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增或更新
     */
    @PostMapping("/save")
    public ResponseDto<?> save(@RequestBody CategoryDto categoryDto) {
        LOG.info("categoryDto:{}", categoryDto);
        ResponseDto<CategoryDto> responseDto = new ResponseDto<>();

/**
 *下面的异常处理非常局限，使用{@link com.course.business.controller.ControllerExceptionHandler}统一处理异常
 */
        // 保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
        categoryService.save(categoryDto);
        responseDto.setContent(categoryDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> delete(@PathVariable("id") String categoryId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        LOG.info("categoryId:{}", categoryId);
        categoryService.delete(categoryId);
        responseDto.setContent(categoryId);
        return responseDto;
    }
}
