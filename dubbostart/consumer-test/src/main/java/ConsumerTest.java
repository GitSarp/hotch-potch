import interfaces.BaseInterface;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 下午2:58 2018/9/13
 * @ Description：
 * @ Modified By：
 */
public class ConsumerTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        BaseInterface demoService = (BaseInterface) context.getBean("demoService");
        // Executing remote methods
        String hello = demoService.sayHello("world");
        // Display the call result
        System.out.println(hello);
    }


}
