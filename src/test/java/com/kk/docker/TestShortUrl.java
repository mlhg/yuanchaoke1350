package com.kk.docker;

import com.alibaba.fastjson.JSON;
import com.kk.docker.common.core.R;
import com.kk.docker.model.vo.ShortUrlVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author yuanchaoke
 * @version 1.0.0
 * @ClassName TestShortUrl.java
 * @Description TODO
 * @createTime 2021年04月16日 02:20:00
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DockertestApplication.class, MockServletContext.class })
public class TestShortUrl {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }


    @Test
    public void testGenerate(){

        try {
        String url = "www.baidu.com";
        System.out.println("长地址："+url);
        String result  = mockMvc.perform(
                post("/url/generate")
                        .param("url",url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                       )
                .andReturn().getResponse().getContentAsString();
            System.out.println("result1:"+result);
            R r =  JSON.parseObject(result,R.class);
            ShortUrlVo shortUrlVo = JSON.parseObject(r.getData().toString(),ShortUrlVo.class);
            System.out.println("依据长地址生成的短地址"+shortUrlVo.getShortUrl());

            result  = mockMvc.perform(
                    post("/url/queryLongUrl")
                            .param("shortUrl",shortUrlVo.getShortUrl())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            )
                    .andReturn().getResponse().getContentAsString();

            System.out.println("result2:"+result);

            r =  JSON.parseObject(result,R.class);
             shortUrlVo = JSON.parseObject(r.getData().toString(),ShortUrlVo.class);
            System.out.println("依据短地址查询到的长地址："+shortUrlVo.getOrgUrl());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void testShortUrl(){
        try {
            String url = "www.baidu.com";

            String result  = mockMvc.perform(
                    post("/url/queryLongUrl")
                            .param("shortUrl",url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            )
                    .andReturn().getResponse().getContentAsString();

            System.out.println("result2:"+result);

            R r =  JSON.parseObject(result,R.class);
            ShortUrlVo shortUrlVo = JSON.parseObject(r.getData().toString(),ShortUrlVo.class);
            System.out.println("依据短地址查询到的长地址："+shortUrlVo.getOrgUrl());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
