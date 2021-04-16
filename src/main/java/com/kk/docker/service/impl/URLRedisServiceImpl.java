package com.kk.docker.service.impl;


import com.kk.docker.common.CommonCache;
import com.kk.docker.common.exception.BaseException;
import com.kk.docker.common.util.ShortUrlUtils;
import com.kk.docker.model.vo.ShortUrlVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;



/**
 * @description:
 * @Author  craeng@126.com
 * @Description //TODO 
 * @Date  1:53 1:53
 * @Param 
 * @return 
 **/
// @Service
public class URLRedisServiceImpl {

    @Value("${short_url.server}")
    private String shortUrlServer;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public ShortUrlVo generate(String srcUrl, Long valid) throws BaseException {
        if (valid == null){
            valid = 60L * 60L *4L ;  // 默认有效时间4个小时
        }
        if (valid!= -1 && valid <0){
            throw new BaseException("有效时间非法");
        }
        srcUrl = srcUrl.trim();
       //String shortURL =  MD5Util.encryptStr( srcUrl);
        String shortURL =ShortUrlUtils.shortUrl(srcUrl);
        String key = CommonCache.SHORT_URL+shortURL;

        redisTemplate.opsForValue().set(key,srcUrl);

       if (valid != -1){ // -1是永久有效
           redisTemplate.expire(key,valid, TimeUnit.SECONDS);
       }
        ShortUrlVo shortUrlVo = new ShortUrlVo();
       shortUrlVo.setOrgUrl(srcUrl)
               .setShortTarget(shortURL)
               .setValidTime(valid)
               .setShortUrl(generateShortUrl(shortURL));

        return shortUrlVo;
    }


    public ShortUrlVo restoreByTarget(String shortTarget) throws BaseException {
        Objects.requireNonNull(shortTarget);
        String srcUrl =  redisTemplate.opsForValue().get(CommonCache.SHORT_URL+shortTarget);
        if (StringUtils.isBlank(srcUrl)){
             throw new BaseException("短链接不存在或者已经失效");
        }
       Long validTime =  redisTemplate.getExpire(CommonCache.SHORT_URL+shortTarget);
       String shortUrl = generateShortUrl(shortTarget);
       ShortUrlVo shortUrlVo =new ShortUrlVo();
       shortUrlVo.setShortUrl(shortUrl)
               .setShortTarget(shortTarget)
               .setValidTime(validTime)
               .setOrgUrl(srcUrl);

        return shortUrlVo;
    }

    /**
     * 根据完整的短链接解析
     * @todo 存在域名变更问题
     * @author yuanchaoke
     * @param shortUrl:
     * @return
     */
    public ShortUrlVo restoreByShortUrl(String shortUrl) throws BaseException {
        Objects.requireNonNull(shortUrl);
        if (!shortUrl.contains("/u/")){
            throw new BaseException("短链接不合法");
        }
        String target = shortUrl.split("/u/")[1];

        return restoreByTarget(target);
    }
    private String generateShortUrl(String target){
        return shortUrlServer+"/u/"+target;
    }



}
