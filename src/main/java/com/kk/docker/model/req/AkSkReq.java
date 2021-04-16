package com.kk.docker.model.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
public class AkSkReq {
    @NotBlank(message = "ak不能为空")
    private  String ak ;
    @NotBlank(message = "sk不能为空")
    private  String sk ;

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }
}
