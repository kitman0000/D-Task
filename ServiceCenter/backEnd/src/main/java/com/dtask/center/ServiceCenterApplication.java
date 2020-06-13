package com.dtask.center;

import com.dtask.common.config.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.dtask.common","com.dtask.common.*","com.dtask.center.*"})
@SpringBootApplication
public class ServiceCenterApplication {

//	@Autowired
//	static private RabbitSender sender;

	public static void main(String[] args) {
		SpringApplication.run(ServiceCenterApplication.class, args);
		//sender.send("hi");
	}

}
