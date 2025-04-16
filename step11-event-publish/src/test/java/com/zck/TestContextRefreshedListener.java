package com.zck;

import com.zck.context.ApplicationListener;
import com.zck.context.event.ContextRefreshedEvent;

public class TestContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("监听事件，容器刷新了");
    }
}
