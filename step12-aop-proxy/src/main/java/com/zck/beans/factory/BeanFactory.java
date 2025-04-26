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


    /**
     * 获取指定类型的bean对象
     * @param beanName
     * @param classType
     * @return
     * @param <T>
     */
    <T> T getBean(String beanName, Class<T> classType);
}
