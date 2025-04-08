package com.zck.factory;

import com.zck.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * cglib的方式实例化bean
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
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
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == constructor) return enhancer.create();
        return enhancer.create(constructor.getParameterTypes(), args);
    }
}
