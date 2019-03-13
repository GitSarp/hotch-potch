package com.example.boottaskdemo.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 10:54 AM 2019/3/13
 * @ Description：
 * @ Modified By：
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail teatQuartzDetail(){
        return JobBuilder.newJob(QuartzService.class).withIdentity("quartzService").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger(){
        //设置时间周期,单位秒
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
                .repeatForever();

        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("quartzService")
                .withSchedule(scheduleBuilder)
                .build();
    }

//    @Bean
//    public JobDetail teatQuartzDetail2(){
//        return JobBuilder.newJob(QuartzService2.class).withIdentity("quartzService2").storeDurably().build();
//    }
//
//    @Bean
//    public Trigger testQuartzTrigger2(){
//        //设置时间周期,单位秒
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
//                .repeatForever();
//
//        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail2())
//                .withIdentity("quartzService2")
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
}
