package com.kuta.akka.base;

import java.util.HashMap;
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

	/**
	 * 客户端连接Map
	 * */
	private Map<Integer, ActorRef> channelMap = new HashMap<Integer, ActorRef>();
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
		this.channelMap.put(message.getPid(), message.getChannel());
	}
	/**
	 * 用户离线
	 * @param message 用户在线离线消息包装
	 * */
	private void Offline(ActiveMessage message) {
		logger.info("从online-Map中清除用户:{}", message.getPid());
		this.channelMap.remove(message.getPid());
	}
	/**
	 * 转发用户消息
	 * @param message 用户在线离线消息包装
	 * */
	private void forward(ForwardWebSocketResponse message) {
		if(message.getPid().equals(KutaConstants.BROADCAST_ALL)) {
			this.channelMap.forEach((pid,actor)->{
				message.setPid(pid);
				actor.tell(message.toJSONString(), self());
			});
			return;
		}
		if(this.channelMap.containsKey(message.getPid())) {
			this.channelMap.get(message.getPid()).tell(message.toJSONString(), self());
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
