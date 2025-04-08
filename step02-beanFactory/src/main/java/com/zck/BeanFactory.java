package com.zck;

import java.util.HashMap;

public class BeanFactory {
    private HashMap<String, BeanDefinition> beanDefinitionMap;

    public BeanFactory() {
        this.beanDefinitionMap = new HashMap<>();
    }

    /**
     * 注册bean
     *
     * @param beanName
     * @param beanDefinition
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 获取bean
     * @param userService
     * @return
     */
    public Object getBean(String userService) {
        return this.beanDefinitionMap.get(userService).getBean();
    }
}
