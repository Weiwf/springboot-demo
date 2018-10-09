package com.wei.demo.quartz.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("------------ start quartz ------------- " +
                new SimpleDateFormat("yyyy-yy-dd HH:mm:ss").format(new Date()));
    }
}
