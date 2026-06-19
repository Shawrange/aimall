package com.aimall.controller;

import com.aimall.entity.enums.ResponseCodeEnum;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class AGlobalExceptionHandlerController extends ABaseController {

    private static final Logger logger = LoggerFactory.getLogger(AGlobalExceptionHandlerController.class);

    @ExceptionHandler(value = Exception.class)
    Object handleException(Exception e, HttpServletRequest request) {
        logger.error("璇锋眰閿欒锛岃姹傚湴鍧€{},閿欒淇℃伅:", request.getRequestURL(), e);
        ResponseVO responseVO = new ResponseVO();
        //404
        if (e instanceof NoHandlerFoundException || e instanceof NoResourceFoundException) {
            responseVO.setCode(ResponseCodeEnum.CODE_404.getCode());
            responseVO.setInfo(ResponseCodeEnum.CODE_404.getMsg());
            responseVO.setStatus(STATUC_ERROR);
        } else if (e instanceof BusinessException) {
            //涓氬姟閿欒
            BusinessException biz = (BusinessException) e;
            responseVO.setCode(biz.getCode() == null ? ResponseCodeEnum.CODE_600.getCode() : biz.getCode());
            responseVO.setInfo(biz.getMessage());
            responseVO.setStatus(STATUC_ERROR);
        } else if (e instanceof BindException || e instanceof MethodArgumentTypeMismatchException || e instanceof ConstraintViolationException) {
            //鍙傛暟绫诲瀷閿欒
            responseVO.setCode(ResponseCodeEnum.CODE_600.getCode());
            responseVO.setInfo(ResponseCodeEnum.CODE_600.getMsg());
            responseVO.setStatus(STATUC_ERROR);
        } else if (e instanceof DuplicateKeyException) {
            //涓婚敭鍐茬獊
            responseVO.setCode(ResponseCodeEnum.CODE_601.getCode());
            responseVO.setInfo(ResponseCodeEnum.CODE_601.getMsg());
            responseVO.setStatus(STATUC_ERROR);
        } else {
            responseVO.setCode(ResponseCodeEnum.CODE_500.getCode());
            responseVO.setInfo(ResponseCodeEnum.CODE_500.getMsg());
            responseVO.setStatus(STATUC_ERROR);
        }
        return responseVO;
    }
}

