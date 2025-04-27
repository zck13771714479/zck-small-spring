package com.zck.utils;

public class ClassUtils {
    /**
     * 获取默认类加载器
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    /**
     * Check whether the specified class is a CGLIB-generated class.
     * @param clazz the class to check
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    /**
     * Check whether the specified class name is a CGLIB-generated class.
     * @param className the class name to check
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }
}
