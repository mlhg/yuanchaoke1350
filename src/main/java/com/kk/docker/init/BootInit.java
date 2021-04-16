package com.kk.docker.init;


import com.kk.docker.common.CommonCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
@Component
public class BootInit implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(BootInit.class);
    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    @Override
    public void afterPropertiesSet() throws Exception {
        //先放入一个可用的ak sk
        //后期版本进行丰富此功能
        redisTemplate.opsForHash().put(CommonCache.Ak_SK,"ak_123","sk_123");

        logger.info("default access api ak and sk is {ak_123},{sk_123}");

    }
}
