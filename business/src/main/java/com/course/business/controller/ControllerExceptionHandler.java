package com.course.business.controller;

import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto<?> validatorExeptionHandler(ValidatorException exception) {
        LOG.warn(exception.getMessage());
        ResponseDto<?> responseDto = new ResponseDto<>();
        responseDto.setSuccess(false);
        // 返回给前台页面的错误信息尽量模糊，防止暴露后台字段是如何设计的
//        responseDto.setMessage(exception.getMessage());
        responseDto.setMessage("请求参数异常！");
        return responseDto;
    }
}
