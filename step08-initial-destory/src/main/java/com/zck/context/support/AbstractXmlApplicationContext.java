package com.zck.context.support;

import com.zck.beans.factory.support.DefaultListableBeanFactory;
import com.zck.beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    /**
     * 加载beanDefinition
     *
     * @param beanFactory
     */
    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] locations = configLocations();
        for (String location : locations) {
            beanDefinitionReader.loadBeanDefinition(location);
        }
    }

    /**
     * 获取配置文件路径数组
     *
     * @return
     */
    protected abstract String[] configLocations();

}
