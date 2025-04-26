package com.zck.beans.factory.support;

import com.zck.beans.BeansException;
import com.zck.beans.factory.config.BeanDefinitionRegistry;
import com.zck.core.io.Resource;
import com.zck.core.io.ResourceLoader;

/**
 * 扫描文件注册beanDefinition的接口
 */
public interface BeanDefinitionReader {

    /**
     * 获取beanDefinition的注册器
     *
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     *
     * @return
     */
    ResourceLoader getResourceLoader();

    /**
     * 加载资源转化为beanDefinition
     * @param resource
     * @throws BeansException
     */
    void loadBeanDefinition(Resource resource) throws BeansException;

    /**
     * 加载资源转化为beanDefinition
     * @param resources
     * @throws BeansException
     */
    void loadBeanDefinition(Resource... resources) throws BeansException;

    /**
     * 加载资源转化为beanDefinition
     * @param location
     * @throws BeansException
     */
    void loadBeanDefinition(String location) throws BeansException;
}
