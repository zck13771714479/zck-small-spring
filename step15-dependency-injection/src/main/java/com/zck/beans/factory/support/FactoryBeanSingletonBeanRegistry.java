package com.zck.beans.factory.support;

import com.zck.beans.BeansException;
import com.zck.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanSingletonBeanRegistry extends DefaultSingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();


    public Object getCachedFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJ ? object : null);
    }

    public Object getObjectFromFactoryBean(String beanName, FactoryBean factoryBean) {
        if (factoryBean.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(beanName, factoryBean);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJ));
            }
            return (object != NULL_OBJ ? object : null);
        } else {
            return doGetObjectFromFactoryBean(beanName, factoryBean);
        }
    }

    public Object doGetObjectFromFactoryBean(String beanName, FactoryBean factoryBean) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
