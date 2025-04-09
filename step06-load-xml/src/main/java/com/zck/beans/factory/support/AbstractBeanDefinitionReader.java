package com.zck.beans.factory.support;

import com.zck.beans.factory.config.BeanDefinitionRegistry;
import com.zck.core.io.DefaultResourceLoader;
import com.zck.core.io.ResourceLoader;

/**
 * beanDefinition读取器的抽象父类
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private ResourceLoader resourceLoader;
    private BeanDefinitionRegistry registry;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    /**
     * 获取beanDefinition的注册器
     *
     * @return
     */
    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    /**
     * 获取资源加载器
     *
     * @return
     */
    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }
}
