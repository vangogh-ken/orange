package com.van.halley.job.common;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class AutoShutdownTask {

	// @Scheduled(cron="*/60 * *  * * ?")
	// @Scheduled(cron="0 15 10 ? * 6")
	public void shutdown() {
		String dir = System.getProperty("user.dir");
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(dir + "/shutdown.bat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
