package com.zck.beans.factory.config;

import com.zck.beans.BeansException;

public interface AutowireCapableBeanFactory {

    /**
     * 初始化前的bean处理
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 初始化后的bean处理
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
