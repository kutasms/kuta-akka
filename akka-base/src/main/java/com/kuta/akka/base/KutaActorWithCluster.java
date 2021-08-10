package com.kuta.akka.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.kuta.akka.base.entity.RegistrationMessage;
import com.kuta.base.collection.KutaHashSet;
import com.kuta.data.akka.pojo.AkkaNodeInfo;

import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.japi.pf.ReceiveBuilder;


/**
 * 附带集群信息的Actor
 * */
public abstract class KutaActorWithCluster extends KutaActorWithTimers {

	/**
	 * 集群信息
	 * */
	protected final Cluster cluster = Cluster.get(getContext().system());

	/**
	 * 	节点名称
	 * */
	protected String nodeName;
	
	/**
	 * host和端口信息
	 * */
	protected String hostport;
	/**
	 * host名称
	 * */
	protected String hostname;
	/**
	 * 端口信息
	 * */
	protected Integer port;
	/**
	 * 当前节点配置的角色集合
	 */
	protected KutaHashSet<String> roles;

	/**
	 * 注册节点集合
	 * */
	protected Map<String, Set<String>> registerMap = new HashMap<String, Set<String>>();

	/**
	 * 节点注册消息
	 */
	private final static String REGISTRATION_MSG = "[{}]节点收到节点注册信息,发送方:{}";
	/**
	 * 消息未处理信息
	 * */
	private static final String UNHANDLED_MESSAGE = "未处理消息:{}";
	/**
	 * 成员状态已切换为Up
	 * */
	private static final String MSG_MEMBER_UP = "Member is Up: {}";
	/**
	 * 成员状态已切换为Removed
	 * */
	private static final String MSG_MEMBER_REMOVE = "Member is Removed: {}";
	/**
	 * 成员状态已切换为Unreachable
	 * */
	private static final String MSG_MEMBER_UNREACHABLE = "Member detected as Unreachable: {}";
	
	public AkkaNodeInfo getNodeInfo() {
		AkkaNodeInfo info = new AkkaNodeInfo();
		info.setHost(this.hostname);
		info.setHostport(this.hostport);
		info.setName(this.nodeName);
		info.setPort(this.port);
		info.setRoles(this.roles);
		return info;
	}
	
	protected void setName() {
		
	}
	
	
	@Override
	public void preStart() {
		//订阅集群信息
		cluster.subscribe(self(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
		if (roles == null) {
			roles = new KutaHashSet<>();
		}
		roles.addAll(cluster.getSelfRoles());
		
		hostname = cluster.selfAddress().getHost().get();
		port = cluster.selfAddress().getPort().get();
		hostport = cluster.selfAddress().hostPort();
		
		for (String role : this.roles) {
			if (!registerMap.containsKey(role)) {
				registerMap.put(role, new HashSet<String>());
			}
			Set<String> elem = registerMap.get(role);
			elem.add(this.hostport);
		}
		try {
			this.nodeName = getContext().getSystem().settings().config().getString("akka.cluster.name");
			if(nodeName != null) {
				logger.info("节点名称:{}",nodeName);
				System.out.println(nodeName);
			}
		}catch (Exception e) {
			// TODO: handle exception
			nodeName = "未命名";
		}
	}

	@Override
	public void postStop() {
		//取消订阅集群信息
		cluster.unsubscribe(self());
	}

	/**
	 * 当本节点状态更新为"Up"时触发
	 * */
	public abstract void onSelfUp();

	/**
	 * 消息处理函数
	 * <p>Member事件已经处理</p>
	 * @param rb 消息封装
	 */
	public abstract void onReceive(ReceiveBuilder rb);

	
	
	/**
	 * 当节点状态更新为"up"时触发
	 * @param mUp 成员up状态包装
	 * */
	protected void onMemberUp(MemberUp mUp) {
		logger.info(MSG_MEMBER_UP, mUp.member());
		Set<String> set = mUp.member().getRoles();
		for (String role : set) {
			if (!registerMap.containsKey(role)) {
				registerMap.put(role, new HashSet<String>());
			}
			Set<String> elem = registerMap.get(role);
			elem.add(mUp.member().address().hostPort());
		}
		if (mUp.member().address().hostPort().equals(this.hostport)) {
			onSelfUp();
		}
	}
	
	
	/**
	 * 当节点状态更新为"remove"时触发
	 * @param mRemoved 成员remove状态包装
	 * */
	protected void onMemberRemoved(MemberRemoved mRemoved) {
		logger.info(MSG_MEMBER_REMOVE, mRemoved.member());
		Set<String> set = mRemoved.member().getRoles();
		for (String role : set) {
			Set<String> elem = registerMap.get(role);
			elem.remove(mRemoved.member().address().hostPort());
		}
	}
	
	
	/**
	 * 当节点状态更新为"unreachable"时触发
	 * @param mUnreachable 成员unreachable状态包装
	 * */
	protected void onMemberUnreachable(UnreachableMember mUnreachable) {
		logger.info(MSG_MEMBER_UNREACHABLE, mUnreachable.member());
		Set<String> set = mUnreachable.member().getRoles();
		for (String role : set) {
			Set<String> elem = registerMap.get(role);
			elem.remove(mUnreachable.member().address().hostPort());
		}
	}
	
	/**
	 * 消息处理封装
	 * @return 消息封装
	 * */
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		
		ReceiveBuilder rBuilder = receiveBuilder().match(MemberUp.class, mUp -> {
			this.onMemberUp(mUp);
		}).match(MemberRemoved.class, mRemoved -> {
			this.onMemberRemoved(mRemoved);
		}).match(UnreachableMember.class, mUnreachable -> {
			this.onMemberUnreachable(mUnreachable);
		}).match(MemberEvent.class, message -> {
			// ignore
		});
		rBuilder.match(RegistrationMessage.class, msg->{
			//处理actor注册信息
			logger.info(REGISTRATION_MSG,self(),sender());
			onRegister(msg);
		});
		onReceive(rBuilder);
		rBuilder.matchAny(msg->{
			if(msg.equals("ack")) {
				return;
			}
			logger.info(UNHANDLED_MESSAGE,JSONObject.toJSONString(msg));
		});
		return rBuilder.build();
	}

}
