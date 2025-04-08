package com.zck.factory;

import com.zck.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    /**
     * 创建bean
     *
     * @param name
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) {
        Object bean;
        try {
            //创建bean对象
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //加入单例map
        addSingleton(name, bean);
        return bean;
    }
}
