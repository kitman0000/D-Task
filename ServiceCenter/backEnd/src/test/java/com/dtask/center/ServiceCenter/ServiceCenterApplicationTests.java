package com.dtask.center.ServiceCenter;

import com.dtask.common.config.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@Component
@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceCenterApplicationTests {

	@Autowired
	RabbitSender rabbitSender;

	Logger logger = LoggerFactory.getLogger(ServiceCenterApplicationTests.class);

	// 16
	@Test
	public void getNodeID() {
		String res = rabbitSender.send("dtask.getNodeID","{\"nodeName\":\"demoData\"}");
		logger.info(res);
	}

	@Test
	public void getAllNodes(){
		String res = rabbitSender.send("dtask.getAllNodes","");
		logger.info(res);
	}

	//BIND_SUCCESS BIND_FAILED_1 BIND_FAILED_2
	@Test
	public void askBinding(){
		String res = rabbitSender.send("dtask.binding.ask","{\"nodeID\":18,\"requestBindID\":15}");
		logger.info(res);
	}

	//HANDLE_SUCCESS
	@Test
	public void handleRequest(){
		String res = rabbitSender.send("dtask.binding.handle","{\"requestID\":9,\"accept\":true,\"nodeID\":15}");
		logger.info(res);
	}

	//UNBIND_SUCCESS UNBIND_FAILED
	@Test
	public void unbind(){
		String res = rabbitSender.send("dtask.binding.unbind","{\"nodeID\":\"1\"}");
		logger.info(res);
	}

	@Test
	public void getBindRequest(){
		String res = rabbitSender.send("dtask.binding.getBindRequest","{\"nodeID\":15}");
		logger.info(res);
	}

	//ROOT_EXIST SET_SUCCESS
	@Test
	public void setRoot(){
		String res = rabbitSender.send("dtask.binding.setRoot","{\"nodeID\":15}");
		logger.info(res);
	}

	//dtask.test
	@Test
	public void test(){
		String res =rabbitSender.send("dtask.test","aaaa");
		logger.info("2:"+res);
	}

	@Test
	public void test2(){
		String res =rabbitSender.send("dtask.syncUserInfo","aaaa");
		logger.info("2:"+res);
	}
}
