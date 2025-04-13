package com.zck.beans.factory;

/**
 * bean name感知接口
 */
public interface BeanNameAware extends Aware{
    void setBeanName(String beanName);
}
