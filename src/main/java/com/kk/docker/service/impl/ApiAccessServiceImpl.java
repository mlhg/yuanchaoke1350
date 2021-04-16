package com.kk.docker.service.impl;

import com.kk.docker.common.CommonCache;
import com.kk.docker.service.ApiAccessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
@Service
public class ApiAccessServiceImpl implements ApiAccessService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean acceptAccess(String ak, String sk) {
        if (StringUtils.isEmpty(ak)|| StringUtils.isEmpty(sk))
            return false;
        Object accSk= redisTemplate.opsForHash().get(CommonCache.Ak_SK,ak);
        System.out.println("accSk:"+accSk);
        return accSk!=null && accSk.equals(sk);
    }
}
