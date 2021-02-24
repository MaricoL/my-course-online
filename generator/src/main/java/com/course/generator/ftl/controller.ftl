package com.course.business.controller.admin;

import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.${Domain}Service;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
    public static final String BUSINESS_NAME = "大章";

    @Resource
    private ${Domain}Service ${domain}Service;

    /**
     * 查询所有
     */
    // @RequestBody：前端发送POST请求，数据为JSON格式，需要在行参上加上此注解
    @PostMapping("/list")
    public ResponseDto<?> list(@RequestBody PageDto<${Domain}Dto> pageDto) {
        ResponseDto<PageDto<${Domain}Dto>> responseDto = new ResponseDto<>();
        LOG.info("pageDto:{}", pageDto);
        ${domain}Service.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 新增或更新
     */
    @PostMapping("/save")
    public ResponseDto<?> save(@RequestBody ${Domain}Dto ${domain}Dto) {
        LOG.info("${domain}Dto:{}", ${domain}Dto);
        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();

/**
 *下面的异常处理非常局限，使用{@link com.course.business.controller.ControllerExceptionHandler}统一处理异常
 */
        // 保存校验
        ${domain}Service.save(${domain}Dto);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> delete(@PathVariable("id") String ${domain}Id) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        LOG.info("${domain}Id:{}", ${domain}Id);
        ${domain}Service.delete(${domain}Id);
        responseDto.setContent(${domain}Id);
        return responseDto;
    }
}
