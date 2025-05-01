package com.zck.aop;

import java.lang.reflect.Method;

/**
 * 前置增强接口
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
