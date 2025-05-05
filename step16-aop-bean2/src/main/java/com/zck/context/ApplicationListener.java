package com.zck.context;

import java.util.EventListener;

/**
 * 事件监听器接口
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);

}
