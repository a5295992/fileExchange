package com.example.hbaseSearch.web.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Component
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(Exception.class)
    public Map<String, Object> globalException(HttpServletResponse httpServletResponse, Exception exception) {
        log.error("捕获全局异常 --- {}", exception);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("msg", exception.getMessage());
        System.out.println(httpServletResponse);
        httpServletResponse.setStatus(500);
        return map;
    }
}
