package com.zck.beans.factory;

import com.zck.beans.BeansException;

/**
 * beanFactory感知接口
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
