package com.zck.beans.factory.config;

import com.zck.beans.BeansException;

/**
 * Bean后处理器接口
 */
public interface BeanPostProcessor {

    /**
     * bean初始化前处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;


    /**
     * bean初始化后处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
