package com.kk.docker.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/

@Api(value = "公共系统", description = "短地址前端页面跳转")
@Controller
public class IndexController {

    //长地址生成短地址
    @RequestMapping("/")
    public String index(){
        return "/web/index.html";
    }

    //查询到短地址
    @RequestMapping("/querylongurl")
    public String querylongurl(){
        return "/web/querylongurl.html";
    }

}
