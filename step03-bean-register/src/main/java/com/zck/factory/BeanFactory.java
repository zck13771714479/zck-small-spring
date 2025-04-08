package com.zck.factory;

/**
 * 顶层容器
 */
public interface BeanFactory {

    /**
     * 获取bean对象
     * @param name
     * @return
     */
    Object getBean(String name);

}
