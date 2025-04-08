package com.zck.factory;

import com.zck.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 获取bean对象
     *
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) {
        Object singleton = getSingleton(name);
        if (singleton != null){
            return singleton;
        }
        BeanDefinition beanDefinition  = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    /**
     * 创建bean
     * @param name
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition);

    /**
     * 获取beanDefinition
     * @param name
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String name);


}
