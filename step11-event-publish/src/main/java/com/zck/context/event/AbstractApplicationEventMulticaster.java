package com.zck.context.event;

import com.zck.beans.BeansException;
import com.zck.beans.factory.BeanFactory;
import com.zck.beans.factory.BeanFactoryAware;
import com.zck.context.ApplicationEvent;
import com.zck.context.ApplicationListener;
import com.zck.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    private Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();
    private BeanFactory beanFactory;

    /**
     * 添加事件的监听器
     *
     * @param applicationListener
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) applicationListener);
    }

    /**
     * 移除事件监听器
     *
     * @param applicationListener
     */
    @Override
    public void removeApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListeners.remove(applicationListener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 根据事件获取对应的所有事件监听器
     *
     * @param event
     * @return
     */
    protected Collection<ApplicationListener<ApplicationEvent>> getApplicationListeners(ApplicationEvent event) {
        List<ApplicationListener<ApplicationEvent>> listeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportEvents(listener, event)) {
                listeners.add(listener);
            }
        }
        return listeners;
    }

    /**
     * 判断是否监听事件
     *
     * @param listener 监听器
     * @param event    事件
     * @return
     */
    private boolean supportEvents(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = listener.getClass();
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        //获取监听器中真实监听的类型
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String typeName = actualTypeArgument.getTypeName();
        Class<?> listenEventClass;
        try {
            listenEventClass = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //判断监听的类型是不是event的父类
        return listenEventClass.isAssignableFrom(event.getClass());
    }
}
