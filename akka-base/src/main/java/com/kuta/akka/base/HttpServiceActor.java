package com.kuta.akka.base;

import com.alibaba.fastjson.JSONObject;
import com.kuta.akka.base.entity.GatewayMessage;
import com.kuta.akka.base.entity.HttpResponseMessage;
import com.kuta.akka.base.entity.ResponseStatus;
import com.kuta.akka.base.entity.WebSocketResponse;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.ThrowingConsumer;

import akka.actor.ActorRef;

public abstract class HttpServiceActor extends KutaActor {

	protected void inspectRESTful(GatewayMessage msg, 
			ThrowingConsumer<HttpResponseMessage, Exception> consumer,
			String... args) {
		HttpResponseMessage response = new HttpResponseMessage();
		response.setCode(msg.getCode());
		response.setElapsedTime(System.currentTimeMillis());
		try {
			if (args != null && args.length > 0) {
				for (String arg : args) {
					if(!msg.getParams().containsKey(arg)) {
						throw new KutaIllegalArgumentException(String.format("缺失参数%s", arg));
					}
				}
			}
			
			consumer.accept(response);
		} 
		catch(KutaIllegalArgumentException ex) {
			logger.error(ex,ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatus(ex.getErrorCode());
			response.setElapsedTime(System.currentTimeMillis() - response.getElapsedTime());
			msg.getChannel().tell(response, self());
		}
		catch (Exception ex) {
			logger.error(ex,ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
			response.setElapsedTime(System.currentTimeMillis() - response.getElapsedTime());
			msg.getChannel().tell(response, self());
		}
	}
	
	protected void successRESTful(ActorRef channel, HttpResponseMessage rsp, JSONObject data) {
		if(!channel.isTerminated()) {
			rsp.setStatus(ResponseStatus.OK);
			rsp.setMessage("OK");
			
			if(data != null) {
				rsp.setData(data);
			}
			channel.tell(rsp, self());
		} else {
			throw new KutaRuntimeException("Http通道已关闭");
		}
	}
	
	protected void failureRESTful(ActorRef channel, 
			HttpResponseMessage rsp, 
			String errorMsg, 
			int errorCode) {
		if(!channel.isTerminated()) {
			rsp.setMessage(errorMsg);
			rsp.setStatus(errorCode);
			channel.tell(rsp, self());
		} else {
			throw new KutaRuntimeException("Http通道已关闭");
		}
	}

	protected void inspectWebsocket(GatewayMessage msg, 
			ThrowingConsumer<WebSocketResponse, Exception> consumer,
			String... args) {
		WebSocketResponse response = new WebSocketResponse();
		response.setCode(msg.getCode());
		try {
			if (args != null && args.length > 0) {
				for (String arg : args){
					if(!msg.getParams().containsKey(arg)) {
						throw new KutaIllegalArgumentException(String.format("缺失参数%s", arg));
					}
				}
			}
			consumer.accept(response);
		} catch (Exception ex) {
			logger.error(ex,ex.getMessage());
			response.setMessage(ex.getMessage());
			response.setStatus(ResponseStatus.UNKNOWN_ERROR);
			msg.getChannel().tell(response.toJSONString(), self());
		}
	}
	
	protected void successWebsocket(ActorRef channel, WebSocketResponse rsp, JSONObject data) {
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
	
	protected void failureWebsocket(ActorRef channel, 
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
