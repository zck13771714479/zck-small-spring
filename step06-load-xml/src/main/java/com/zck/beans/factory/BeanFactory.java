package com.zck.beans.factory;

/**
 * 顶层容器
 */
public interface BeanFactory {

    /**
     * 获取bean对象
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

    /**
     * 带参数的获取bean对象
     * @param beanName
     * @param args
     * @return
     */
    Object getBean(String beanName, Object... args);
}
