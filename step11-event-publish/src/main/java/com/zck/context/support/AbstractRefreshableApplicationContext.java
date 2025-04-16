package com.zck.context.support;

import com.zck.beans.factory.ConfigurableListableBeanFactory;
import com.zck.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 创建beanFactory并且加载所有beanDefinition
     */
    @Override
    protected void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinition(beanFactory);
        this.beanFactory = beanFactory;
    }


    /**
     * 创建容器
     * @return
     */
    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载beanDefinition
     * @param beanFactory
     */
    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory);
    /**
     * 获取beanFactory
     *
     * @return
     */
    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }
}
