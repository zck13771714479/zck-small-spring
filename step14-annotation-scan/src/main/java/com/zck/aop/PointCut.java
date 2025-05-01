package com.zck.aop;

/**
 * 切点接口
 */
public interface PointCut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
