package com.zck.context.event;

import com.zck.context.ApplicationEvent;
import com.zck.context.ApplicationListener;

/**
 * 事件广播器接口
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加事件的监听器
     * @param applicationListener
     */
    void addApplicationListener(ApplicationListener<?> applicationListener);


    /**
     * 移除事件监听器
     * @param applicationListener
     */
    void removeApplicationListener(ApplicationListener<?> applicationListener);

    /**
     * 发布事件
     * @param applicationEvent
     */
    void multicastEvent(ApplicationEvent applicationEvent);

}
