package com.zck.aop.framework;

import com.zck.aop.AdvisedSupport;

public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    /**
     * 获取代理对象
     * @return
     */
    public Object getProxy(){
        return this.createAopProxy().getProxy();
    }

    /**
     * 创建代理对象
     * @return
     */
    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()){
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
