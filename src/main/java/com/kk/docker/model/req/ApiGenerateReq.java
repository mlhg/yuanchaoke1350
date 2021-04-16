package com.kk.docker.model.req;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
public class ApiGenerateReq  {


    @NotBlank(message = "url不能为空")
   private  String url;

    @Value("${short_url.valid}")
   private Long valid;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getValid() {
        return valid;
    }

    public void setValid(Long valid) {
        this.valid = valid;
    }
}
