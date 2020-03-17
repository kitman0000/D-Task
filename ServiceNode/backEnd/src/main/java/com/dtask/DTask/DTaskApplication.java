package com.dtask.DTask;

import com.dtask.common.util.CacheUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.dtask.common.*","com.dtask.DTask.*"})
@SpringBootApplication
public class DTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DTaskApplication.class, args);

		CacheUtil.setCacheMode(1);

		System.out.println("===                        =======        ==             ====       =     =");
		System.out.println("=     ==                      =          =  =          ==           =  =");
		System.out.println("=     ==     ===              =         ======            ==        ==");
		System.out.println("=     ==                      =        =      =              ==     =  =");
		System.out.println("===                           =       =        =        =====       =     =");

	}

}
