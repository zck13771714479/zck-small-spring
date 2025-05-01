package com.zck.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.context.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;


public class ClassPathScanningCandidateComponentProvider {
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> beanDefinitionMap = new LinkedHashSet<BeanDefinition>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> aClass : classes) {
            beanDefinitionMap.add(new BeanDefinition(aClass));
        }
        return beanDefinitionMap;
    }
}
