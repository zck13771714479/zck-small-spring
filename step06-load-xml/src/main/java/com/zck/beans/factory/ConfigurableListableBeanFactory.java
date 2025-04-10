package com.zck.beans.factory;

import com.zck.beans.BeansException;
import com.zck.beans.factory.config.AutowireCapableBeanFactory;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}