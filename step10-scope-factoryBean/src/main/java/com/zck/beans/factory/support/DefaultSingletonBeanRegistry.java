package com.zck.beans.factory.support;

import com.zck.beans.BeansException;
import com.zck.beans.factory.DisposableBean;
import com.zck.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    public Object NULL_OBJ = new Object();


    private final Map<String, Object> singletonObjects = new HashMap<String, Object>();
    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    /**
     * 根据bean的名字，获取单例bean
     *
     * @param beanName
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 执行注册的销毁bean的钩子函数
     */
    @Override
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeanMap.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeanMap.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    /**
     * 注册销毁的钩子函数
     *
     * @param beanName
     * @param disposableBean
     */
    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        disposableBeanMap.put(beanName, disposableBean);
    }


    /**
     * 添加单例bean
     *
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
