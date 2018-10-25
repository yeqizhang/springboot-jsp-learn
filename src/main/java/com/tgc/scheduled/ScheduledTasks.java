
package com.tgc.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	@Scheduled(fixedRate = 20000)	//每隔10秒执行一次
	public void test() {
      // 执行任务调度方法
		System.out.println("我正在执行任务调度方法...");
	}

}
