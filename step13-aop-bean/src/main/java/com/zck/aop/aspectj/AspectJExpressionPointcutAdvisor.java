package com.zck.aop.aspectj;

import com.zck.aop.PointCut;
import com.zck.aop.PointCutAdvisor;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointCutAdvisor {
    private PointCut pointCut;
    private Advice advice;
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public PointCut getPointCut() {
        if (this.pointCut == null) {
            this.pointCut = new AspectJExpressionPointcut(this.expression);
        }
        return this.pointCut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
