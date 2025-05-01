package com.zck.beans.factory.support;


import com.zck.beans.BeansException;
import com.zck.beans.factory.FactoryBean;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanPostProcessor;
import com.zck.beans.factory.config.ConfigurableBeanFactory;
import com.zck.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends FactoryBeanSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    /**
     * 注入值解析器，@Value解析
     *
     * @param resolver
     */
    @Override
    public void addEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolvers.add(resolver);
    }

    /**
     * @param value
     * @return
     * @Value解析模板值
     */
    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : embeddedValueResolvers) {
            result = resolver.resolveStringValue(value);
        }
        return result;
    }


    protected <T> T doGetBean(String beanName, Object[] args) {
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return (T) getObjectForBeanInstance(singleton, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, beanName);
    }

    /**
     * factoryBean特殊处理
     *
     * @param bean
     * @param beanName
     * @return
     */
    private Object getObjectForBeanInstance(Object bean, String beanName) {
        if (!(bean instanceof FactoryBean)) {
            return bean;
        }
        //factoryBean需要通过getObject方法创建
        Object cachedFactoryBean = getCachedFactoryBean(beanName);
        if (cachedFactoryBean == null) {
            cachedFactoryBean = getObjectFromFactoryBean(beanName, (FactoryBean<?>) bean);
        }
        return cachedFactoryBean;
    }



    /**
     * 创建bean
     *
     * @param name           beanName
     * @param beanDefinition
     * @param args           构造函数携带的参数
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

    /**
     * 添加bean后处理器
     *
     * @param beanPostProcessor
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}
