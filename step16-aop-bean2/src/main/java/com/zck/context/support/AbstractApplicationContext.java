package com.zck.context.support;

import com.zck.beans.BeansException;
import com.zck.beans.factory.ConfigurableListableBeanFactory;
import com.zck.beans.factory.config.BeanFactoryPostProcessor;
import com.zck.beans.factory.config.BeanPostProcessor;
import com.zck.context.ApplicationEvent;
import com.zck.context.ApplicationListener;
import com.zck.context.ConfigurableApplicationContext;
import com.zck.context.event.ApplicationEventMulticaster;
import com.zck.context.event.ContextClosedEvent;
import com.zck.context.event.ContextRefreshedEvent;
import com.zck.context.event.SimpleApplicationEventMulticaster;
import com.zck.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

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
        //3.手动添加applicationContext的感知的bean后处理器，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwarePostProcessor(this));
        //4.在bean实例化之前，执行BeanFactoryPostProcessor，对beanDefinition进行修改
        invokeBeanFactoryPostProcessors(beanFactory);
        //5.在bean实例化之前，注册所有bean后处理器
        registerBeanPostProcessors(beanFactory);
        //6.初始化事件发布器
        initMulticaster();
        //7.注册事件监听器
        registerEventListener();
        //8.提前实例化好所有单例bean
        beanFactory.preInstantiateSingletons();
        //9.完成refresh
        finishRefresh();
    }


    /**
     * 初始化事件发布器
     */
    private void initMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        ApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster(beanFactory);
        this.applicationEventMulticaster = multicaster;
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, multicaster);
    }

    /**
     * 注册事件监听器
     */
    private void registerEventListener() {
        Map<String, ApplicationListener> beansOfType = getBeansOfType(ApplicationListener.class);
        for (ApplicationListener listener : beansOfType.values()) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 完成refresh
     */
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    /**
     * 发布事件
     * @param event
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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
        //发布关闭容器事件
        publishEvent(new ContextClosedEvent(this));
        //执行销毁容器钩子
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

    /**
     * 获取指定类型的bean对象
     *
     * @param classType
     * @return
     */
    @Override
    public <T> T getBean(Class<T> classType) {
        return getBeanFactory().getBean(classType);
    }
}
