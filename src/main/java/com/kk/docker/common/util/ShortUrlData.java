package com.kk.docker.common.util;

import com.kk.docker.common.CommonCache;
import com.kk.docker.config.MvcExceptionHandler;
import com.kk.docker.model.vo.ShortUrlVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanchaoke
 * @version 1.0.0
 * @ClassName ShortUrlData.java
 * @Description TODO
 * @createTime 2021年04月16日 04:51:00
 */
@Configuration
public class ShortUrlData {

    private  static Long hashmapMax = 2000000l;

    private static Logger logger = LoggerFactory.getLogger(MvcExceptionHandler.class);

    private static ShortUrlData singleton;

    public static HashMap<String, ShortUrlVo> shortUrlMap = new HashMap<>();
    private static ScheduledExecutorService swapExpiredPool = new ScheduledThreadPoolExecutor(1);// 2线程去

    private static PriorityBlockingQueue<ShortUrlVo> queue = new PriorityBlockingQueue<>();// 按生成时间排序的队列


    public static synchronized ShortUrlData initial() {
        logger.info("开始");
        if (singleton == null) {
            singleton = new ShortUrlData();

            synchronized (ShortUrlData.class) {
                swapExpiredPool.scheduleWithFixedDelay(() -> {
                    long now = System.currentTimeMillis();
                    // TODO Auto-generated method stub
                    while (true) {
                        ShortUrlVo suv = queue.peek();
                        if (shortUrlMap.size() < hashmapMax) {
                            if (suv != null && suv.getValidTime() + suv.getCreateTime() - now > 0) {
                                return;
                            }
                            shortUrlMap.remove(CommonCache.SHORT_URL + suv.getShortUrl());
                            ShortUrlVo deleted = queue.poll();
                            logger.info("删除" + deleted.getOrgUrl());
                        } else {
                            shortUrlMap.remove(CommonCache.SHORT_URL + suv.getShortUrl());
                            ShortUrlVo deleted = queue.poll();
                            logger.info("删除" + deleted.getOrgUrl());
                        }
                    }
                }, 1, 1, TimeUnit.SECONDS);
            }
        }
        return singleton;
    }



    public static void put(String key, ShortUrlVo suv) {
        ShortUrlVo oldKeyInfo = shortUrlMap.put(key, suv);
        if (oldKeyInfo != null) {
            queue.remove(oldKeyInfo);
        }
        queue.add(suv);
    }

    public static ShortUrlVo get(String key) {
        ShortUrlVo suv = shortUrlMap.get(key);
        return suv;
    }

    public static void removeCache(String key) {
        shortUrlMap.remove(key);
    }


}
