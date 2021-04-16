package com.kk.docker.controller;


import com.kk.docker.common.core.R;
import com.kk.docker.common.exception.BaseException;
import com.kk.docker.model.vo.ShortUrlVo;
import com.kk.docker.service.URLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/

@Api(value = "公共系统", description = "短地址接口服务接口")
@RestController
@RequestMapping("url")
public class URLController {

    @Autowired
    private URLService urlService;

    @Value("${short_url.valid}")
    private Long valid;

    @ApiOperation(value = "生成短地址接口", notes = "生成短地址接口")
    @PostMapping(value = "/generate")
    public R<Object> generate( @ApiParam(name = "url", value = "长地址参数", required=true ) @RequestParam String url ) throws BaseException {
        if (StringUtils.isEmpty(url)){
            return R.failed("url不能为空");
        }

        ShortUrlVo shortURL = urlService.generate(url,
                valid
        );

        return R.ok(shortURL);
    }


    @ApiOperation(value = "依据短地址查询出长地址接口", notes = "依据短地址查询出长地址接口")
    @PostMapping("/queryLongUrl")
    public R<ShortUrlVo> queryLongUrl(@ApiParam(name = "shortUrl", value = "短地址参数", required=true ) @RequestParam String shortUrl) throws BaseException {
        if (StringUtils.isEmpty(shortUrl)){
            return R.failed("url不能为空");
        }

        ShortUrlVo shortUrlVo = urlService.restoreByShortUrl(shortUrl);
        if (null != shortUrlVo && StringUtils.isNotBlank(shortUrlVo.getOrgUrl())){
            return R.ok(shortUrlVo);
        } else{
            return R.failed();
        }
    }


}
