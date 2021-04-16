package com.kk.docker.common.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
public class MD5Util {
    public static String encryptStr(String str){
        return  DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8)).substring(8,24);

    }
}
