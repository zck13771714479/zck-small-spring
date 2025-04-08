package com.zck.factory;

import com.zck.BeansException;
import com.zck.config.BeanDefinition;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 实例化bean的策略
     */
    protected InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 创建bean
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean;
        try {
            //创建bean对象
           bean = createBeanInstance(beanName, beanDefinition, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        //加入单例map
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 创建bean实例
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     */
    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        if (args == null) {
            //无参构造函数
            return instantiationStrategy.instantiate(beanName, beanDefinition, null, null);
        }
        Constructor[] constructors = beanDefinition.getBeanClass().getDeclaredConstructors();
        Constructor cons = null;
        for (Constructor constructor : constructors) {
            if (constructor.getParameterTypes().length == args.length) {
                //如果参数数量相等判断为相等
                //todo 实际上还要参数判断类型是否相同
                cons = constructor;
                break;
            }
        }
        //使用带参构造函数实例化
        return instantiationStrategy.instantiate(beanName, beanDefinition, cons, args);
    }
}
