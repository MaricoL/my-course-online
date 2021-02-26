package com.course.${MODULE}.controller.admin;

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
    public static final String BUSINESS_NAME = "${tableNameCn}";

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
 *下面的异常处理非常局限，使用{@link com.course.${MODULE}.controller.ControllerExceptionHandler}统一处理异常
 */
        // 保存校验
        <#list fieldList as field>
          <#if field.nameHump != "id" && field.nameHump != "createdAt" && field.nameHump != "updatedAt" && field.nameHump != "sort">
              <#if !field.nullable>
        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
              </#if>
              <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
              </#if>
          </#if>
        </#list>
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
