package com.kuta.akka.base.entity;

/**
 * <pre>
 * KSF框架akka常量定义
 * </pre>
 * */
public class KutaAkkaConstants {
	
	/**
	 * <p>分布式集群-框架级定义</p>
	 * 系统名称
	 * */
	public static final String FRAMEWORK_NAME = "KSF";
	
	/**
	 * <p>分布式集群-框架级定义</p>
	 * <p>阻塞线程调度器，请在akka配置文件中定义akka.kuta-blocking-dispatcher</p>
	 * <p>请在akka节点中插入下面代码段</p>
	 * <pre>
	 * kuta-blocking-dispatcher {
	 *	  type = Dispatcher
	 *	  executor = "thread-pool-executor"
	 *	  thread-pool-executor {
	 *	    fixed-pool-size = 200
	 *	  }
	 *	  throughput = 1
	 * }
	 * </pre>
	 * */
	public static final String BLOCKING_DISPATCHER = "akka.kuta-blocking-dispatcher";
	
	/**
	 * 注册指令
	 * */
	public static final String CMD_SIGN_IN = "signin";
	/**
	 * 登陆指令
	 * */
	public static final String CMD_SIGN_UP = "signup";
	
	/**
	 * 网关模块
	 * */
	public static final String MODULE_GATEWAY = "gateway";
	
	/**
	 * cmd参数
	 * */
	public static final String PARAM_CMD  =  "cmd";
	/**
	 * <p>API系统级参数</p>
	 * uuid参数
	 * */
	public static final String PARAM_UUID =  "uuid";
	/**
	 * <p>API系统级参数</p>
	 * appid参数
	 * */
	public static final String PARAM_APPID=  "appid";
	/**
	 * <p>API系统级参数</p>
	 * version参数
	 * */
	public static final String PARAM_VERSION = "version";
	/**
	 * <p>API系统级参数</p>
	 * module参数
	 * */
	public static final String PARAM_MODULE = "module";
	/**
	 * <p>API系统级参数</p>
	 * <p>platform参数,调用接口的平台</p>
	 * */
	public static final String PARAM_PLATFORM = "platform";
	/**
	 * <p>API系统级参数</p>
	 * code参数,用于指定当前调用的接口
	 * */
	public static final String PARAM_CODE = "code";
	/**
	 * 类型参数
	 * */
	public static final String PARAM_TYPE = "type";
	/**
	 * 房间编号参数
	 * */
	public static final String PARAM_ROOM_ID = "room_id";
	/**
	 * 比赛编号参数
	 * */
	public static final String PARAM_RACE_ID = "race_id";
	
	
	/**
	 * 房间信息转发Actor
	 * */
	public static final String ACTOR_ROOM_FORWORD = "room-forward-actor";
}
