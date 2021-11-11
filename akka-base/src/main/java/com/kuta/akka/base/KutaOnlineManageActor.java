package com.kuta.akka.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuta.akka.base.entity.ActiveMessage;
import com.kuta.akka.base.entity.ForwardWebSocketResponse;
import com.kuta.akka.base.entity.RegistrationMessage;
import com.kuta.base.entity.KutaConstants;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * 在线用户管理Actor
 * */
public class KutaOnlineManageActor extends KutaActor {
	
	private class Item{
		private ActorRef channel;
		private Integer uid;
		
		public Item(Integer uid,ActorRef channel) {
			this.uid = uid;
			this.channel = channel;
		}
		
		public ActorRef getChannel() {
			return channel;
		}
		public Integer getUid() {
			return uid;
		}
	}
	
	/**
	 * 客户端连接Map
	 * */
	private Map<Integer, Item> channelMap = new HashMap<Integer, Item>();
	private final Class<? extends KutaActor> offlineMsgHandlerClazz;
	/**
	 * 构造函数
	 * @param offlineHandler 离线消息处理器
	 * */
	public KutaOnlineManageActor(Class<? extends KutaActor> offlineMsgHandlerClazz) {
		this.offlineMsgHandlerClazz = offlineMsgHandlerClazz;
	}
	
	public static Props props(Class<? extends KutaActor> offlineMsgHandlerClazz) {
		return Props.create(KutaOnlineManageActor.class, offlineMsgHandlerClazz);
	}
	
	@Override
	public void onReceive(ReceiveBuilder rb) {
		// TODO Auto-generated method stub
		rb.match(ActiveMessage.class, msg->{
			if(msg.getActive()) { //活跃中
				this.online(msg);
			}
			else {
				this.Offline(msg);//已下线
			}
		}).match(ForwardWebSocketResponse.class, msg->{//转发消息
			this.forward(msg);
		});
	}
	
	/**
	 * 用户上线
	 * @param message 用户在线离线消息包装
	 * */
	private void online(ActiveMessage message) {
		this.channelMap.put(message.getPid(), new Item(message.getUid(), message.getChannel()));
	}
	/**
	 * 用户离线
	 * @param message 用户在线离线消息包装
	 * */
	private void Offline(ActiveMessage message) {
		if(this.channelMap.containsKey(message.getPid())) {
			if(message.isForce() || this.channelMap.get(message.getPid()).getUid().equals(message.getUid())) {
				logger.info("从online-Map中清除用户:{}", message.getPid());
				this.channelMap.remove(message.getPid());
			} else {
				logger.info("UID不同不能移除玩家");
			}
		}
	}
	/**
	 * 转发用户消息
	 * @param message 用户在线离线消息包装
	 * */
	private void forward(ForwardWebSocketResponse message) {
		final List<Integer> needRemove = new ArrayList<Integer>();
		if(message.getPid().equals(KutaConstants.BROADCAST_ALL)) {
			this.channelMap.forEach((pid,item)->{
				if(!item.getChannel().isTerminated()) {
					message.setPid(pid);
					item.getChannel().tell(message.toJSONString(), self());
				} else {
					needRemove.add(pid);
				}
			});
			if(needRemove.size() > 0) {
				needRemove.forEach(pid->{
					this.channelMap.remove(pid);
				});
			}
			return;
		}
		
		if(this.channelMap.containsKey(message.getPid())) {
			Item item = this.channelMap.get(message.getPid());
			if(!item.getChannel().isTerminated()) {
				this.channelMap.get(message.getPid()).getChannel().tell(message.toJSONString(), self());
			} else {
				this.channelMap.remove(message.getPid());
			}
		} else {
			//该用户已下线，由下游Actor处理
			if(message.isSaveOffline() && this.offlineMsgHandlerClazz != null) {
				ActorRef handler = context().actorOf(Props.create(offlineMsgHandlerClazz));
				handler.tell(message, self());
			}
		}
	}

	/**
	 * 收到注册消息
	 * @param msg 注册消息包装
	 * */
	@Override
	public void onRegister(RegistrationMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
