package com.zck.beans.factory;

/**
 * bean销毁前的钩子函数
 */
public interface DisposableBean {

    /**
     * bean销毁前的钩子函数
     * @throws Exception
     */
    void destroy() throws Exception;

}
