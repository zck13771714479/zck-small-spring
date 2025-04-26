import beans.IUserService;
import beans.UserService;
import beans.UserServiceInterceptor;
import com.zck.aop.AdvisedSupport;
import com.zck.aop.TargetSource;
import com.zck.aop.aspectj.AspectJExpressionPointcut;
import com.zck.aop.proxy.Cglib2AopProxy;
import com.zck.aop.proxy.JdkDynamicAopProxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class APITest {
    @Test
    public void testAspectJExpression() throws NoSuchMethodException {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut("execution(* beans.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getMethod("queryUserInfo");
        System.out.println(aspectJExpressionPointcut.matches(clazz));
        System.out.println(aspectJExpressionPointcut.matches(method, clazz));
    }

    @Test
    public void testProxy() throws Exception{
        IUserService userService = new UserService();
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* beans.IUserService.*(..))"));

        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);
        IUserService proxy1 = (IUserService) jdkDynamicAopProxy.getProxy();
        System.out.println(proxy1.queryUserInfo());
        System.out.println(proxy1.register("zck1"));

        Cglib2AopProxy cglib2AopProxy = new Cglib2AopProxy(advisedSupport);
        IUserService proxy2 = (IUserService) cglib2AopProxy.getProxy();
        System.out.println(proxy2.queryUserInfo());
        System.out.println(proxy2.register("zck2"));

    }
}
