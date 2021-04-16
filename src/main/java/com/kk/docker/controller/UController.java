package com.kk.docker.controller;

import com.kk.docker.common.exception.BaseException;
import com.kk.docker.model.vo.ShortUrlVo;
import com.kk.docker.service.URLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/

@Api(value = "公共系统", description = "短地址查询到长地址跳转接口")
@RestController
public class UController {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private URLService urlServiceImpl;

    @ApiOperation(value = "依据短地址查询到长地址跳转接口", notes = "依据短地址查询到长地址跳转接口")
    @GetMapping("/u/{shortUrl}")
    public void u(HttpServletResponse response,@ApiParam(name = "shortUrl", value = "短地址参数", required=true ) @PathVariable String shortUrl) throws IOException, BaseException {
        if (StringUtils.isNotBlank(shortUrl)){

            //update yck 20210416 start
            // String url =  redisTemplate.opsForValue().get(CommonCache.SHORT_URL+shortUrl);
            ShortUrlVo suv=  urlServiceImpl.restoreByTarget(shortUrl);
            String url =null != suv ? suv.getOrgUrl() : null;
            //update yck 20210416 end

            if (StringUtils.isNotBlank(url))
                    if (!(url.startsWith("http://")||url.startsWith("https://"))){
                url = "http://"+url;
               }
              response.sendRedirect(url);

            } else{
                response.getWriter().write("url is lose efficacy");
            }
        }

    }

