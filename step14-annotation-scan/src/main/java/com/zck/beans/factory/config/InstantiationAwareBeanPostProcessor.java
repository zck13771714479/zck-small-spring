package com.zck.beans.factory.config;

import com.zck.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * bean初始化前处理
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

}
