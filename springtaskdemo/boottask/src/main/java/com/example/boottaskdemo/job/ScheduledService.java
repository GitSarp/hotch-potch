package com.example.boottaskdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 10:33 AM 2019/3/13
 * @ Description：
 * @ Modified By：
 */
@Async  //每个任务使用独立的线程执行
@Slf4j
@Component
public class ScheduledService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info("=====>>>>>使用cron  {}",dateFormat.format(new Date()));
    }
    //上一次开始执行时间点之后5秒再执行
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", dateFormat.format(new Date()));
    }

    //上一次执行完毕时间点之后5秒再执行
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",dateFormat.format(new Date()));
    }

    //第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void scheduled3() {
        log.info("=====>>>>>initialDelay{}",dateFormat.format(new Date()));
    }
}
