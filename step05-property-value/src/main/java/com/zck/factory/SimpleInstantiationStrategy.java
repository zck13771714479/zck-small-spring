package com.zck.factory;

import com.zck.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * JDK反射创建bean对象
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    /**
     * 初始化bean
     *
     * @param beanName
     * @param beanDefinition
     * @param constructor    选用的构造函数
     * @param args           构造函数需要的参数
     * @return
     */
    @Override
    public Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> constructor, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            if (constructor == null) {
                return beanClass.newInstance();
            } else {
                return beanClass.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
