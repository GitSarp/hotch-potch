package test;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 下午3:35 2018/9/17
 * @ Description：
 * @ Modified By：
 */
@Component
@Lazy(value = false)//懒加载就gg！
public class testJob {

    private static final Logger logger = Logger.getLogger(testJob.class);

    @Scheduled(cron = "0/2 * * * * ?")
    public void testLog(){
        logger.info("测试定时任务开始执行--"+new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()));
        System.out.println("6666666666");
    }
}
