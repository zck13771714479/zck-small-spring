package com.zck.aop;

import com.zck.utils.ClassUtils;

public class TargetSource {
    private Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClasses() {
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}
