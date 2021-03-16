# D-Task/Tasking分布式任务管理系统
## 1. 项目简介
普通的团队任务管理软件，通常部署在一台服务器上，整个团队使用一个系统。但这样一旦系统需要更新，迁移，甚至发生崩溃，就会导致整个团队无法使用。D-Task（后改名为Tasking）将让每个部门拥有自己独立的任务管理服务，每个服务称之为节点，或者系统。节点与节点之间可以互相连接，（例如一个公司有北京总部，上海分部，广州分部，则上海分部和广州分部可以连接至北京总部），组建一个强大的任务管理网络。本项目支持插件化开发，提高了系统的开放性与可扩展性。
## 2. 功能说明
账号管理：账号登录/改密，上级用户登录。

用户管理：添加/删除用户，储存用户基本信息（例如生日，入职日期等）

角色管理：用户角色，角色权限设置

菜单控制：根据用户权限显示相应的操作菜单。

部门管理：为用户按部门分类，方便查找用户。

本地任务：当前节点的任务管理，例如人员，进度安排等。

联合任务：与其他节点联合举行的任务管理。

系统绑定：将本系统绑定到其他一个中心调配服务中，与其他节点连接。

网站设置：网站基础参数设置。

视频会议：使用插件SDK开发的视频会议插件。包含会议室管理，视频会议，会议纪要等功能。

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

## 7.插件开发
为了提升平台的可扩展性，本项目支持插件化开发。节点与中心服务都可以开发插件。具体开发步骤如下：

 1. 下载插件模板： `https://github.com/kitman0000/D-Task/tree/master/pluginTemplate`并导入到IDE中
 2. 如果你需要显示页面
    
    (1)请在controller层注册。[查看示例][1]
    
    (2)请在resources文件下编写页面。[查看示例][2]
    
    (3)如果需要获取用户token，请从url参数中获取。
    
 3. 如果你需要监听用户操作事件
    
    (1)请导入插件SDK：`https://github.com/kitman0000/D-Task/blob/master/jar/plugin-sdk.jar`.
    
    (2)我们提供了14个模块的事件监听接口，在你的代码中实现该接口，并使用`@Component`即可响应事件。
    
 4. 如果你需要添加新的菜单，请先完成第\[3\]步后，监听菜单事件。[查看示例][3]
 5. 将插件打包为Jar包，并和其他打包结果放在同一文件夹下。
 6. 修改Dtask.jar中的`META-INF/MANIFEST.MF`文件，在文件最后加上你插件的jar文件名。
 7. 启动Dtask.jar，插件即可生效。
 
 更多细节可以查看我们的插件示例：[Agora视频会议插件][4] [Agora视频会议中心服务插件][5]


## 8. 其他文档链接
设计文档：https://docs.qq.com/doc/DSUNMaWZKZGdUb1ZF

API文档：https://docs.qq.com/sheet/DSUdGbnBRTUhqTVll?tab=BB08J2

用户手册:成功运行项目后，用户手册地址在http://{主机地址}:{端口号}/#/webUserManual


**注意：以上文档都在更新中，都不是最终版本**


## 9. 界面截图
![任务菜单][6]


![子任务菜单][7]


![节点拓扑图][8]


  [1]: https://github.com/kitman0000/D-Task/blob/master/pluginTemplate/src/main/java/com/dtask/template/templateModule/controller/PageController.java
  [2]: https://github.com/kitman0000/D-Task/tree/master/LiveMeeting/src/main/resources/templates/LiveMeeting
  [3]: https://github.com/kitman0000/D-Task/blob/master/LiveMeeting/src/main/java/com/dtask/liveMeeting/liveMeetingModule/service/MenuEventImpl.java
  [4]: https://github.com/kitman0000/D-Task/tree/master/LiveMeeting
  [5]: https://github.com/kitman0000/D-Task/tree/master/LiveMeetingCenter
  [6]: https://raw.githubusercontent.com/kitman0000/D-Task/master/screenshot/%E6%88%AA%E5%9B%BE1.png
  [7]: https://raw.githubusercontent.com/kitman0000/D-Task/master/screenshot/%E6%88%AA%E5%9B%BE3.png
  [8]: https://raw.githubusercontent.com/kitman0000/D-Task/master/screenshot/%E6%88%AA%E5%9B%BE2.png
