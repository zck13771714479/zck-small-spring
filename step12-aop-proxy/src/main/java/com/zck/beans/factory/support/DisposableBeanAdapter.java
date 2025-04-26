package com.zck.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.zck.beans.BeansException;
import com.zck.beans.factory.DisposableBean;
import com.zck.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 销毁bean的适配器
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    /**
     * bean销毁前的钩子函数
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean)) {
            try {
                Method destoryMethod = bean.getClass().getMethod(destroyMethodName);
                destoryMethod.invoke(bean);
            } catch (NoSuchMethodException e) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
        }
    }
}
