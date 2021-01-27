# D-Task分布式任务管理系统
## 1. 项目简介
普通的团队任务管理软件，通常部署在一台服务器上，整个团队使用一个系统。但这样一旦系统需要更新，迁移，甚至发生崩溃，就会导致整个团队无法使用。D-Task将让每个部门拥有自己独立的任务管理服务，每个服务称之为节点，或者系统。节点与节点之间可以互相连接，（例如一个公司有北京总部，上海分部，广州分部，则上海分部和广州分部可以连接至北京总部），组建一个强大的任务管理网络。D-Task的D为Distributed 的简写，表示采用分布式部署。
## 2. 功能说明
账号管理：账号登录/改密
用户管理：添加/删除用户，储存用户基本信息（例如生日，入职日期等）
角色管理：用户角色，角色权限设置
菜单控制：根据用户权限显示相应的操作菜单。
部门管理：为用户按部门分类，方便查找用户。
本地任务：当前节点的任务管理，例如人员，进度安排等。
联合任务：与其他节点联合举行的任务管理。
系统绑定：将本系统绑定到其他一个中心调配服务中，与其他节点连接。
网站设置：网站基础参数设置。
## 3. 技术栈
前端:VueJS + ElementUI + axios
后端:Springboot + Shiro + Maven
缓存技术: Ehcache/Redis
数据库:MySql
消息队列:RabbitMQ

## 4.部署与安装
前端部分请使用`npm run build`编译，并放入Nginx中

后端部分请使用IDEA打包为jar包单独运行，或打包为war包放入Tomcat中

节点服务数据库请使用：https://github.com/kitman0000/D-Task/blob/master/Data_DTask.sql

中心服务器数据库请使用：https://github.com/kitman0000/D-Task/blob/master/Data_DTask_Center.sql

**注意：以上数据文件均只包含表结构，请自行添加数据，系统需要添加用户后才能登录**

## 5. 节点绑定方式（重要）
节点绑定步骤如下：
1. 服务节点1(Service Node ,以下简称SN)通知中心调配服务(Center Distribution Service,以下简称CDS),将自身加入节点列表.(此步骤连接RabbitMQ后自动完成)
2. SN1向CDS获取节点列表内的所有SN.
3. SN1选择另一个SN2并向CDS递交绑定请求,申请将被选择的SN2作为父节点,或者当无根节点时,可以向CDS申请成为根节点
4. SN2向CDS获取请求列表.
5. SN2向CDS提交申请处理结果,若同意,则绑定完成.

绑定完成后,父节点的用户可以直接登录子节点,反之不行.
 
通过中心调配服务器,服务节点既可以独立管理自身的任务,也可以和其他节点在中心调配服务器处理远程任务(和其他节点共享的任务).

如果当前没用设置过根节点，则会在后端控制台打印`Can't connect to D-task Center Service,running in single mode.`
此时如何节点都可以申请成为根节点

## 6. MQClouder
在Dtask中，消息队列的操作基于我们自己开发的MQClouder，任何开发者可以引用MQClouder到自己的项目中使用。
使用MQClouder，将会帮助你
1. 简化消息队列开发流程
2. 使用AES加密消息队列信息

**你只需要**
1. Springboot配置文件下，加入secretKey
    secretKey = "你的密钥"
2、在消息队列接收层加入`@MessageReceiver`注解
```java
	@MessageReceiver
	@RabbitListener(bindings = @QueueBinding(
		value = @Queue("dtask.account.login." + "#{${nodeName}}"),
		exchange = @Exchange(value = "topicExchange",type = "topic"),
		key = "dtask.account.login." + "#{${nodeName}}"
	))
	public String mqRemoteLogin(String msg){
		try {
			RemoteLoginEntity remoteLoginEntity = (RemoteLoginEntity) 
			JsonUtil.jsonToObject(msg,RemoteLoginEntity.class);
			return account.mqRemoteLogin(remoteLoginEntity.getUsername(),remoteLoginEntity.getPwd());
		}catch (Exception ex){
			ex.printStackTrace();
			return "SYS_FAILED";
		}
	}
```
**注意：这只是目前初代版本的方式，后续版本更新后可能有很多变化**

## 7. 其他文档链接
设计文档：https://docs.qq.com/doc/DSUNMaWZKZGdUb1ZF

API文档：https://docs.qq.com/sheet/DSUdGbnBRTUhqTVll?tab=BB08J2

用户手册:成功运行项目后，用户手册地址在http://{主机地址}:{端口号}/#/webUserManual


**注意：以上文档都在更新中，都不是最终版本**

## 8. 未来开发计划
1.任务内部的子任务数据可视化，整体任务可视化
2.使用Agora平台的API，添加远程视频会议功能
