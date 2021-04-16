package com.kk.docker.controller;


import com.kk.docker.common.core.R;
import com.kk.docker.common.exception.BaseException;
import com.kk.docker.model.req.ApiGenerateReq;
import com.kk.docker.model.vo.ShortUrlVo;
import com.kk.docker.service.ApiAccessService;
import com.kk.docker.service.URLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
@Api(value = "公共系统", description = "有验证的短地址查询跳转接口")
@RestController
@RequestMapping("/api/v1")
public class ApiURLController {
    @Autowired
    private ApiAccessService apiAccessService;
    @Autowired
    private URLService urlService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    /**
     * 生成一个短链接
     * @author yuanchaoke
     * @param apiGenerateReq:
     * @return
     */
    @ApiOperation(value = "有验证的生成短地址接口", notes = "有验证的生成短地址接口")
    @PostMapping("/generate")
    public R<ShortUrlVo> generate(@Valid @RequestBody ApiGenerateReq apiGenerateReq) throws BaseException {
          ShortUrlVo shortURL = urlService.generate(apiGenerateReq.getUrl(),apiGenerateReq.getValid());
          return R.ok(shortURL);
    }


   /**
     * 根据短链接的target解析原地址
     * @author yuanchaoke
     * @param target:
     * @return
     */
    @GetMapping("/restore/{target}")
    public R<ShortUrlVo> restore(@PathVariable String target) throws BaseException {

        return R.ok(urlService.restoreByTarget(target));
    }
    /**
     * 根据完整的短链接解析原地址
     * @author yuanchaoke
     * @param url:
     * @return
     */
    @GetMapping("/restoreFullUrl/{url}")
    public R<ShortUrlVo> restoreFullUrl(@PathVariable String url) throws BaseException {

        return R.ok(urlService.restoreByTarget(url));
    }

}
