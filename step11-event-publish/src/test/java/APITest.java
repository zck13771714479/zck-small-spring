import com.zck.CustomEvent;
import com.zck.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class APITest {
    @Test
    public void testEvent() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.publishEvent(new CustomEvent(context,3111611L,"发布自定义消息"));
        context.registerShutdownHook();
    }
}
