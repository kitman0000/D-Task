package com.dtask.DTask;

import com.dtask.DTask.bindingModule.controller.BindingCl;
import com.dtask.DTask.bindingModule.service.IBinding;
import com.dtask.common.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.dtask.common","com.dtask.common.*","com.dtask.DTask.*"})
@SpringBootApplication
public class DTaskApplication {


	public static void main(String[] args) {
		SpringApplication.run(DTaskApplication.class, args);
		System.out.println("===                        =======        ==             ====       =     =");
		System.out.println("=   ==                        =          =  =          ==           =  =");
		System.out.println("=   ==       ===              =         ======            ==        ==");
		System.out.println("=   ==                        =        =      =              ==     =  =");
		System.out.println("===                           =       =        =        =====       =     =");
	}

}
