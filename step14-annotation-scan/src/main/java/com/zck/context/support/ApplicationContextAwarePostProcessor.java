package com.zck.context.support;

import com.zck.beans.BeansException;
import com.zck.beans.factory.config.BeanPostProcessor;
import com.zck.context.ApplicationContext;

public class ApplicationContextAwarePostProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    public ApplicationContextAwarePostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * bean初始化前处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware)bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    /**
     * bean初始化后处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
