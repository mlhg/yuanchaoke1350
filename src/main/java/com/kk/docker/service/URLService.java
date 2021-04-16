package com.kk.docker.service;


import com.kk.docker.common.exception.BaseException;
import com.kk.docker.model.vo.ShortUrlVo;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
public interface URLService {

    /**
     * 生成短网址
     * @author
     * @param srcUrl :  原来的url
     * @param valid : 有效时间 -1为永久有效
     * @return
     */
    ShortUrlVo generate(String srcUrl, Long valid) throws BaseException;

    /**
     * 根据短网址target标记解析原网址
     * @author
     * @param shortTarget: 短网址的标记
     * @return
     */
    ShortUrlVo restoreByTarget(String shortTarget) throws BaseException;

    /**
     * 根据短网址target标记解析原网址
     * @author
     * @param shortUrl:  完整的短网址
     * @return
     */
    ShortUrlVo restoreByShortUrl(String shortUrl) throws BaseException;
}
