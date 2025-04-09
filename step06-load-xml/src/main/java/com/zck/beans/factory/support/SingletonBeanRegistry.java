package com.zck.beans.factory.support;

/**
 * 注册单例bean的接口
 */
public interface SingletonBeanRegistry {


    /**
     * 根据bean的名字，获取单例bean
     * @param beanName
     */
    Object getSingleton(String beanName);

}
