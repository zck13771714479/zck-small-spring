package com.zck.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配接口
 */
public interface MethodMatcher {
    /**
     *
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method,Class<?> targetClass);
}
