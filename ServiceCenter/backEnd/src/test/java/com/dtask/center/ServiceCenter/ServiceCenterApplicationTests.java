package com.dtask.center.ServiceCenter;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.center.remoteTaskModule.entity.GetRemoteTaskListEntity;
import com.dtask.center.remoteTaskModule.entity.RemoteTaskSearchEntity;
import com.dtask.common.util.JsonUtil;
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
	EncryptRabbitSender rabbitSender;

	Logger logger = LoggerFactory.getLogger(ServiceCenterApplicationTests.class);

	@Test
	public void addNode(){
		String sendJsonMsg = "{\"nodeName\":"+"\"北京节点\""+"}";
		rabbitSender.encryptSendWithoutResponse("dtask.addNode",sendJsonMsg);
		sendJsonMsg = "{\"nodeName\":"+"\"深圳节点\""+"}";
		rabbitSender.encryptSendWithoutResponse("dtask.addNode",sendJsonMsg);
		sendJsonMsg = "{\"nodeName\":"+"\"广东节点\""+"}";
		rabbitSender.encryptSendWithoutResponse("dtask.addNode",sendJsonMsg);
		sendJsonMsg = "{\"nodeName\":"+"\"杭州节点\""+"}";
		rabbitSender.encryptSendWithoutResponse("dtask.addNode",sendJsonMsg);
		sendJsonMsg = "{\"nodeName\":"+"\"薿峬哕椟节点\""+"}";
		rabbitSender.encryptSendWithoutResponse("dtask.addNode",sendJsonMsg);
	}

	// 16
	@Test
	public void getNodeID() {
		String res = rabbitSender.encryptSend("dtask.getNodeID","{\"nodeName\":\"demoData\"}");
		logger.info(res);
	}

	@Test
	public void getAllNodes(){
		String res = rabbitSender.encryptSend("dtask.getAllNodes","");
		logger.info(res);
	}

	//BIND_SUCCESS BIND_FAILED_1 BIND_FAILED_2
	@Test
	public void askBinding(){
		String res = rabbitSender.encryptSend("dtask.binding.ask","{\"nodeID\":130,\"requestBindID\":1}");
		logger.info(res);

	}

	//HANDLE_SUCCESS
	@Test
	public void handleRequest(){
		String res = rabbitSender.encryptSend("dtask.binding.handle","{\"requestID\":11,\"accept\":true,\"nodeID\":1}");
		logger.info(res);

		res = rabbitSender.encryptSend("dtask.binding.handle","{\"requestID\":12,\"accept\":true,\"nodeID\":1}");
		logger.info(res);

		res = rabbitSender.encryptSend("dtask.binding.handle","{\"requestID\":13,\"accept\":true,\"nodeID\":1}");
		logger.info(res);

		res = rabbitSender.encryptSend("dtask.binding.handle","{\"requestID\":14,\"accept\":true,\"nodeID\":131}");
		logger.info(res);

		res = rabbitSender.encryptSend("dtask.binding.handle","{\"requestID\":15,\"accept\":true,\"nodeID\":131}");
		logger.info(res);
	}

	//UNBIND_SUCCESS UNBIND_FAILED
	@Test
	public void unbind(){
		String res = rabbitSender.encryptSend("dtask.binding.unbind","{\"nodeID\":\"1\"}");
		logger.info(res);
	}

	@Test
	public void getBindRequest(){
		String res = rabbitSender.encryptSend("dtask.binding.getBindRequest","{\"nodeID\":15}");
		logger.info(res);
	}

	//ROOT_EXIST SET_SUCCESS
	@Test
	public void setRoot(){
		String res = rabbitSender.encryptSend("dtask.binding.setRoot","{\"nodeID\":1}");
		logger.info(res);
	}

	//dtask.test
	@Test
	public void test(){
		String res =rabbitSender.encryptSend("dtask.test","aaaa");
		logger.info("2:"+res);
	}

	@Test
	public void test2(){
		GetRemoteTaskListEntity getRemoteTaskListEntity = new GetRemoteTaskListEntity();

		RemoteTaskSearchEntity remoteTaskSearchEntity = new RemoteTaskSearchEntity();
		remoteTaskSearchEntity.setNodeID(0);
		remoteTaskSearchEntity.setTaskName("");
		remoteTaskSearchEntity.setUserID(0);

		getRemoteTaskListEntity.setRemoteTaskSearchEntity(remoteTaskSearchEntity);

		logger.info(JsonUtil.objectToJson(getRemoteTaskListEntity));

	}
}
