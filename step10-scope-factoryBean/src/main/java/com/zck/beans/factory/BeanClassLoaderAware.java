package com.zck.beans.factory;

/**
 * bean的类加载器感知接口
 */
public interface BeanClassLoaderAware extends Aware {
    public void setBeanClassLoader(ClassLoader classLoader);
}
