package com.zck.aop;

/**
 * 类匹配接口
 */
public interface ClassFilter {
    /**
     *
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
