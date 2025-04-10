package com.zck.beans.factory.support;


import com.zck.beans.BeansException;
import com.zck.beans.factory.ConfigurableListableBeanFactory;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 容器核心实现类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 获取beanDefinition
     *
     * @param beanName
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    /**
     * 按照类型返回 Bean 实例
     *
     * @param type
     * @return
     * @throws BeansException
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
       Map<String, T> beansOfType = new HashMap<>();
       for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
           String beanName = entry.getKey();
           BeanDefinition beanDefinition = entry.getValue();
           if (type.isAssignableFrom(beanDefinition.getBeanClass())) {
                beansOfType.put(beanName, (T) getBean(beanName));
           }
       }
       return beansOfType;
    }

    /**
     * 获取所有bean的名字
     *
     * @return
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
