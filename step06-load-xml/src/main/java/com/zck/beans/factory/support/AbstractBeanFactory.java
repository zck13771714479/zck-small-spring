package com.zck.beans.factory.support;


import com.zck.beans.factory.BeanFactory;
import com.zck.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 获取bean对象
     *
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName, null);
    }

    /**
     * 带参数的获取bean对象
     *
     * @param beanName
     * @param args
     * @return
     */
    @Override
    public Object getBean(String beanName, Object... args) {
        return doGetBean(beanName, args);
    }

    protected Object doGetBean(String beanName, Object[] args) {
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition, args);
    }

    /**
     * 获取指定类型的bean对象
     *
     * @param beanName
     * @param classType
     * @return
     */
    @Override
    public <T> T getBean(String beanName, Class<T> classType) {
        return (T) getBean(beanName);
    }

    /**
     * 创建bean
     *
     * @param name beanName
     * @param beanDefinition
     * @param args 构造函数携带的参数
     * @return
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args);

    /**
     * 获取beanDefinition
     *
     * @param name
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String name);


}
