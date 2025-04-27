package com.zck.context.event;

import com.zck.beans.factory.BeanFactory;
import com.zck.context.ApplicationEvent;
import com.zck.context.ApplicationListener;

import java.util.Collection;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     * 发布事件
     *
     * @param applicationEvent
     */
    @Override
    public void multicastEvent(ApplicationEvent applicationEvent) {
        Collection<ApplicationListener<ApplicationEvent>> applicationListeners = getApplicationListeners(applicationEvent);
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            applicationListener.onApplicationEvent(applicationEvent);
        }
    }
}
