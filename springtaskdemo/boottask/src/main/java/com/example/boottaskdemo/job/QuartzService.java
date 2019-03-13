package com.example.boottaskdemo.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 10:52 AM 2019/3/13
 * @ Description：
 * @ Modified By：
 */
public class QuartzService extends QuartzJobBean {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("quartz task:"+dateFormat.format(new Date()));
    }
}
