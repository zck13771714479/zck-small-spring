package com.zck.context;

import com.zck.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;

    /**
     * 注册销毁bean的钩子
     */
    void registerShutdownHook();

    /**
     * 销毁bean
     */
    void close();
}
