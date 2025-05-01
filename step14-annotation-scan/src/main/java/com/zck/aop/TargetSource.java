package com.zck.aop;

public class TargetSource {
    private Object target;
    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClasses() {
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}
