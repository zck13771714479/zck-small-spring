package com.zck.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.zck.beans.BeansException;
import com.zck.beans.PropertyValue;
import com.zck.beans.factory.*;
import com.zck.beans.factory.config.*;
import com.zck.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * 实例化bean的策略
     */
    protected InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 创建bean
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean;
        try {
            //判断是否需要代理
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (bean != null) {
                return bean;
            }
            //bean实例化
            bean = createBeanInstance(beanName, beanDefinition, args);
            //属性值填充，依赖注入
            applyPropertyValues(beanName, bean, beanDefinition);
            //bean初始化
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        //注册bean销毁钩子
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        //加入单例map
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    /**
     * 判断是否需要代理
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        List<BeanPostProcessor> beanPostProcessorList = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 创建bean实例
     *
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     */
    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        if (args == null) {
            //无参构造函数
            return instantiationStrategy.instantiate(beanName, beanDefinition, null, null);
        }
        Constructor[] constructors = beanDefinition.getBeanClass().getDeclaredConstructors();
        Constructor cons = null;
        for (Constructor constructor : constructors) {
            if (constructor.getParameterTypes().length == args.length) {
                //如果参数数量相等判断为相等
                //todo 实际上还要参数判断类型是否相同
                cons = constructor;
                break;
            }
        }
        //使用带参构造函数实例化
        return instantiationStrategy.instantiate(beanName, beanDefinition, cons, args);
    }

    /**
     * 填充属性值
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValue[] propertyValues = beanDefinition.getPropertyValues().getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            //获取所有属性
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            //判断是否需要依赖注入
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            //给bean添加属性，注入
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    /**
     * bean初始化
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @return
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // invokeAwareMethods
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        //bean后处理器，初始化前处理
        Object wrapperBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        try {
            //调用初始化方法
            invokeInitMethod(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //bean后处理器，初始化后处理
        Object resultBean = applyBeanPostProcessorsAfterInitialization(wrapperBean, beanName);
        return resultBean;
    }

    /**
     * 调用构造函数，对bean进行初始化
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void invokeInitMethod(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        //优先执行接口初始化方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean)) {
            //防止重复初始化，没有接口的初始化再执行xml配置的初始化
            Class beanClass = beanDefinition.getBeanClass();
            try {
                Method initMethod = beanClass.getMethod(initMethodName);
                initMethod.invoke(bean);
            } catch (NoSuchMethodException e) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");

            }
        }
    }


    /**
     * 注册销毁bean的钩子函数
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (!beanDefinition.isSingleton()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }
}
