package com.zck.core.io;

/**
 * 资源加载器接口
 */
public interface ResourceLoader {

    public String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 加载资源
     * @param location
     * @return
     */
    Resource getResource(String location);

}
