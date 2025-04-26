package com.zck.aop.aspectj;

import com.zck.aop.ClassFilter;
import com.zck.aop.MethodMatcher;
import com.zck.aop.PointCut;
import net.sf.cglib.transform.MethodFilter;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 匹配切点表达式的类
 */
public class AspectJExpressionPointcut implements ClassFilter, MethodMatcher, PointCut {
    private static final Set<PointcutPrimitive> pointcutPrimitives = new HashSet<PointcutPrimitive>();

    static {
        pointcutPrimitives.add(PointcutPrimitive.EXECUTION);
    }

    private PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        PointcutParser parser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(pointcutPrimitives, Thread.currentThread().getContextClassLoader());
        this.pointcutExpression = parser.parsePointcutExpression(expression);
    }

    /**
     * @param clazz
     * @return
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }


}
