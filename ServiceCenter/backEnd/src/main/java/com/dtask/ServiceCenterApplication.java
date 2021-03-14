package com.dtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.dtask.common","com.dtask.common.*","com.dtask.center.*","com.MQClouder","com.dtask.*.*"})
@SpringBootApplication
public class ServiceCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCenterApplication.class, args);
	}

}
