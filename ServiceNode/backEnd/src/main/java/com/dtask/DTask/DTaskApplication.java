package com.dtask.DTask;

import com.dtask.common.util.CacheUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DTaskApplication.class, args);
		CacheUtil.setCacheManager();

		System.out.println("===                        =======        ==             ====       =     =");
		System.out.println("=     ==                      =          =  =          ==           =  =");
		System.out.println("=     ==     ===              =         ======            ==        ==");
		System.out.println("=     ==                      =        =      =              ==     =  =");
		System.out.println("===                           =       =        =        =====       =     =");
	}

}
