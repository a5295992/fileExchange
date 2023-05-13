package com.example.hbaseSearch.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class RootController implements ErrorController {
    @RequestMapping("/")
    public void ssl(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException, ServletException {
        //log.info("------转发 index.html");
        httpServletRequest.getRequestDispatcher("index.html").forward(httpServletRequest,httpServletResponse);
    }
    @RequestMapping("${server.error.path:${error.path:/error}}")
    public void error(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException, ServletException {
        //log.info("------重定向 index.html");
        httpServletResponse.sendRedirect("https://swordfish.lkhaowu.com/");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
