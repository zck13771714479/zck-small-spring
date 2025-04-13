package com.zck.beans.factory;

/**
 * bean初始化增强
 */
public interface InitializingBean {
    /**
     * 指定初始化方法调用
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
