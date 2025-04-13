package com.zck.context.support;

import com.zck.beans.BeansException;
import com.zck.beans.factory.ConfigurableListableBeanFactory;
import com.zck.beans.factory.config.BeanFactoryPostProcessor;
import com.zck.beans.factory.config.BeanPostProcessor;
import com.zck.context.ConfigurableApplicationContext;
import com.zck.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        //1.创建beanFactory并且加载所有beanDefinition
        refreshBeanFactory();
        //2.获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //3.在bean实例化之前，执行BeanFactoryPostProcessor，对beanDefinition进行修改
        invokeBeanFactoryPostProcessors(beanFactory);
        //4.在bean实例化之前，注册所有bean后处理器
        registerBeanPostProcessors(beanFactory);
        //5.提前实例化好所有单例bean
        beanFactory.preInstantiateSingletons();
    }


    /**
     * 创建beanFactory并且加载所有beanDefinition
     */
    protected abstract void refreshBeanFactory();

    /**
     * 获取beanFactory
     *
     * @return
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 在bean实例化之前，执行BeanFactoryPostProcessor，对beanDefinition进行修改
     *
     * @param beanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> map = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : map.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册销毁bean的钩子
     */
    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    /**
     * 销毁bean
     */
    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }

    /**
     * 在bean实例化之前，注册所有bean后处理器
     *
     * @param beanFactory
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> map = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (Map.Entry<String, BeanPostProcessor> entry : map.entrySet()) {
            beanFactory.addBeanPostProcessor(entry.getValue());
        }
    }


    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

}
