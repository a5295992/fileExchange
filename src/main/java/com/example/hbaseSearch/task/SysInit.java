package com.example.hbaseSearch.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@EnableScheduling

@Slf4j
public class SysInit {
    @Resource
    private FileScanTask fileScanTask;

    @Scheduled(fixedRate = 5000) //该方法是计划任务，使用fixedRate属性每隔固定时间执行。
    public void init() {
        log.info("每隔五秒执行一次{} ", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        fileScanTask.scan();
    }
}
