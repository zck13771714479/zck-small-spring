package com.zck.beans.factory.support;



import com.zck.beans.BeansException;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 容器核心实现类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 获取beanDefinition
     *
     * @param beanName
     * @return
     */
    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
