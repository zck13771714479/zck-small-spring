package com.zck.factory;

import cn.hutool.core.bean.BeanUtil;
import com.zck.BeansException;
import com.zck.PropertyValue;
import com.zck.config.BeanDefinition;
import com.zck.config.BeanReference;

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
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        //加入单例map
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 填充属性值
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValue[] propertyValues = beanDefinition.getPropertyValues().getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            //获取所有属性
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            //判断是否需要依赖注入
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            //给bean添加属性，注入
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    /**
     * 创建bean实例
     *
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

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
