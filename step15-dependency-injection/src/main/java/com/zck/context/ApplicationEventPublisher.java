package com.zck.context;

public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
