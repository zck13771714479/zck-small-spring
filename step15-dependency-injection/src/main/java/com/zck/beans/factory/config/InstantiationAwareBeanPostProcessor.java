package com.zck.beans.factory.config;

import com.zck.beans.BeansException;
import com.zck.beans.PropertyValues;

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
