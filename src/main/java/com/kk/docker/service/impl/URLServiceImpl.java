package com.kk.docker.service.impl;


import com.kk.docker.common.CommonCache;
import com.kk.docker.common.exception.BaseException;
import com.kk.docker.common.util.ShortUrlData;
import com.kk.docker.common.util.ShortUrlUtils;
import com.kk.docker.model.vo.ShortUrlVo;
import com.kk.docker.service.URLService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @description:
 * @Author  craeng@126.com
 * @Description //TODO 
 * @Date  1:53 1:53
 * @Param 
 * @return 
 **/
@Service
public class URLServiceImpl implements URLService {

    @Value("${short_url.server}")
    private String shortUrlServer;


    @Override
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

        //redisTemplate.opsForValue().set(key,srcUrl);

      //if (valid != -1){ // -1是永久有效
        //   redisTemplate.expire(key,valid, TimeUnit.SECONDS);
       //}
        ShortUrlVo shortUrlVo = new ShortUrlVo();
       shortUrlVo.setOrgUrl(srcUrl)
               .setShortTarget(shortURL)
               .setValidTime(valid)
               .setShortUrl(generateShortUrl(shortURL)).setCreateTime(System.currentTimeMillis());
        ShortUrlData.initial().put(key,shortUrlVo);
        return shortUrlVo;
    }

    @Override
    public ShortUrlVo restoreByTarget(String shortTarget) throws BaseException {
        Objects.requireNonNull(shortTarget);

        ShortUrlVo shortUrlVo = ShortUrlData.initial().get(CommonCache.SHORT_URL+shortTarget);

        return shortUrlVo;
    }

    /**
     * 根据完整的短链接解析
     * @todo 存在域名变更问题
     * @author yuanchaoke
     * @param shortUrl:
     * @return
     */
    @Override
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
