package com.zck.beans.factory.config;

import com.zck.beans.BeansException;
import com.zck.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * bean实例化前处理
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * bean实例化后处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;


    /**
     * 属性依赖注入
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
