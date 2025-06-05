package com.example;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception e) {
        logger.error("服务器内部错误：", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "服务器内部错误");
        modelAndView.addObject("error", e.getMessage());
        modelAndView.setViewName("error/500");
        return modelAndView;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(EntityNotFoundException e) {
        logger.warn("资源未找到：", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "请求的资源不存在");
        modelAndView.addObject("error", e.getMessage());
        modelAndView.setViewName("error/404");
        return modelAndView;
    }
}
