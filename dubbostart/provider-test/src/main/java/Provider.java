import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 下午3:28 2018/9/13
 * @ Description：
 * @ Modified By：
 */
public class Provider {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        System.out.println("Provider started.");
        System.in.read(); // press any key to exit
    }
}
