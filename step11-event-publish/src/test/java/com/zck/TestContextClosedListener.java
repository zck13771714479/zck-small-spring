package com.zck;

import com.zck.context.ApplicationListener;
import com.zck.context.event.ContextClosedEvent;

public class TestContextClosedListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("监听事件，容器关闭了");
    }
}
