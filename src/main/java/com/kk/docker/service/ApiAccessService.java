package com.kk.docker.service;



public interface ApiAccessService {
    /**
     * 是否允许访问api
     * @author
     * @param ak:
     * @param sk:
     * @return boolean
     */
    boolean acceptAccess(String ak, String sk);
}
