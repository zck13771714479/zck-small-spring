package com.zck.beans.factory.support;


import com.zck.beans.BeansException;
import com.zck.beans.factory.ConfigurableListableBeanFactory;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanDefinitionRegistry;
import com.zck.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
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

    /**
     * 提前实例化好所有的单例bean
     */
    @Override
    public void preInstantiateSingletons() {
        //遍历beanDefinition map,全部实例化
        for (String key : beanDefinitionMap.keySet()) {
            getBean(key);
        }
    }

    /**
     * 初始化前的bean处理
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        List<BeanPostProcessor> beanPostProcessorList = getBeanPostProcessors();
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 初始化后的bean处理
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        List<BeanPostProcessor> beanPostProcessorList = getBeanPostProcessors();
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

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
     * 获取指定类型的bean对象
     *
     * @param classType
     * @return
     */
    @Override
    public <T> T getBean(Class<T> classType) {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (classType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()) {
            return getBean(beanNames.get(0), classType);
        }

        throw new BeansException(classType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }
}
