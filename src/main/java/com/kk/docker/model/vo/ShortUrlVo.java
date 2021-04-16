package com.kk.docker.model.vo;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
public class ShortUrlVo implements Comparable<ShortUrlVo>{
    private String orgUrl; //原url
    private String shortUrl; //短网址url
    private String shortTarget; //短网址标记
    private Long   validTime;//有效时间
    private Long   createTime;//创建时间

    public String getOrgUrl() {
        return orgUrl;
    }

    public ShortUrlVo setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
        return this;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public ShortUrlVo setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
        return this;
    }

    public String getShortTarget() {
        return shortTarget;
    }

    public ShortUrlVo setShortTarget(String shortTarget) {
        this.shortTarget = shortTarget;
        return this;
    }

    public Long getValidTime() {
        return validTime;
    }

    public ShortUrlVo setValidTime(Long validTime) {
        this.validTime = validTime;
        return this;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    @Override
    public int compareTo(ShortUrlVo o) {
        // TODO Auto-generated method stub
        if(this.orgUrl != null && o.getOrgUrl() != null) {
            long r = this.orgUrl.hashCode() - o.orgUrl.hashCode();
            if (r > 0) {
                return 1;
            }
            if (r < 0) {
                return -1;
            }
            return 0;
        }else {
           if(this.orgUrl==null && o.getOrgUrl() == null) {
               return 0;
           } else {
               return  1;
           }
        }
    }

}
