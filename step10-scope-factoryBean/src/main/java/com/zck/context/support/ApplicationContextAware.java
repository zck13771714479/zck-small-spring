package com.zck.context.support;

import com.zck.beans.BeansException;
import com.zck.beans.factory.Aware;
import com.zck.context.ApplicationContext;

/**
 * applicationContext的感知接口
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
