package com.kuta.akka.base;

import com.alibaba.fastjson.JSONObject;
import com.kuta.akka.base.entity.GatewayMessage;
import com.kuta.akka.base.entity.ResponseStatus;
import com.kuta.akka.base.entity.WebSocketResponse;
import com.kuta.base.exception.KutaAsserts;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.ThrowingConsumer;

import akka.actor.ActorRef;

public abstract class WebsocketServiceActor extends KutaActor {

	protected void inspect(GatewayMessage msg, 
			ThrowingConsumer<WebSocketResponse, Exception> consumer,
			String... args) {
		WebSocketResponse response = new WebSocketResponse();
		response.setCode(msg.getCode());
		try {
			if (args != null && args.length > 0) {
				for (String arg : args)
					KutaAsserts.notNull(arg, arg);
			}
			consumer.accept(response);
		} catch (Exception ex) {
			logger.error(ex,ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
			msg.getChannel().tell(response.toJSONString(), self());
		}
	}
	
	protected void success(ActorRef channel, WebSocketResponse rsp, JSONObject data) {
		if(!channel.isTerminated()) {
			rsp.setStatus(ResponseStatus.OK);
			rsp.setMessage("OK");
			if(data != null) {
				rsp.setData(data);
			}
			channel.tell(rsp.toJSONString(), self());
		} else {
			throw new KutaRuntimeException("websocket通道已关闭");
		}
	}
	
	protected void failure(ActorRef channel, 
			WebSocketResponse rsp, 
			String errorMsg, 
			int errorCode) {
		if(!channel.isTerminated()) {
			rsp.setMessage(errorMsg);
			rsp.setStatus(errorCode);
			channel.tell(rsp.toJSONString(), self());
		} else {
			throw new KutaRuntimeException("websocket通道已关闭");
		}
	}

}
