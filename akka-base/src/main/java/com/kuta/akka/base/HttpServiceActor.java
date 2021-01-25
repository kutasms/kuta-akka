package com.kuta.akka.base;

import com.kuta.akka.base.entity.GatewayMessage;
import com.kuta.akka.base.entity.KutaHttpResponse;
import com.kuta.akka.base.entity.KutaResponse;
import com.kuta.akka.base.entity.KutaWebSocketResponse;
import com.kuta.base.communication.ResponseStatus;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.exception.KutaIllegalArgumentException;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.Status;
import com.kuta.base.util.ThrowingConsumer;

import akka.actor.ActorRef;

public abstract class HttpServiceActor extends KutaActor {

	
	protected <T extends KutaResponse> void inspect(GatewayMessage msg, 
			ThrowingConsumer<T, Exception> consumer,
			String... args) {
		KutaHttpResponse httpRsp = null;
		KutaWebSocketResponse websocketRsp = null;
		boolean isWebsocket = msg.getProtocol().equals(KutaConstants.PROTOCAL_WEBSOCKET);
		if(isWebsocket) {
			websocketRsp = new KutaWebSocketResponse();
			websocketRsp.setCode(msg.getCode());
			websocketRsp.setElapsedTime(System.currentTimeMillis());
		} else {
			httpRsp = new KutaHttpResponse();
			httpRsp.setCode(msg.getCode());
			httpRsp.setElapsedTime(System.currentTimeMillis());
		}
		try {
			if (args != null && args.length > 0) {
				for (String arg : args) {
					if(!msg.getParams().containsKey(arg)) {
						throw new KutaIllegalArgumentException(
								String.format("缺失参数%s", arg));
					}
				}
			}
			if(isWebsocket) {
				consumer.accept((T)websocketRsp);
			} else {
				consumer.accept((T)httpRsp);
			}
			
		} 
		catch(KutaIllegalArgumentException ex) {
			logger.error(ex,ex.getMessage());
			if(isWebsocket) {
				websocketRsp.setMessage(ex.getMessage());
				websocketRsp.setStatus(ex.getErrorCode());
				websocketRsp.setElapsedTime(System.currentTimeMillis() - websocketRsp.getElapsedTime());
				msg.getChannel().tell(websocketRsp.toJSONString(), self());
			} else {
				httpRsp.setMessage(ex.getMessage());
				httpRsp.setStatus(ex.getErrorCode());
				httpRsp.setElapsedTime(System.currentTimeMillis() - httpRsp.getElapsedTime());
				msg.getChannel().tell(httpRsp, self());
			}
		}
		catch (Exception ex) {
			logger.error(ex,ex.getMessage());
			if(isWebsocket) {
				websocketRsp.setMessage(ex.getMessage());
				websocketRsp.setStatus(ResponseStatus.UNKNOWN_ERROR);
				websocketRsp.setElapsedTime(System.currentTimeMillis() - 
						websocketRsp.getElapsedTime());
				msg.getChannel().tell(websocketRsp, self());
			} else {
				httpRsp.setMessage(ex.getMessage());
				httpRsp.setStatus(ResponseStatus.UNKNOWN_ERROR);
				httpRsp.setElapsedTime(System.currentTimeMillis() - 
						httpRsp.getElapsedTime());
				msg.getChannel().tell(httpRsp, self());
			}
		}
	}
	
	
	protected void success(GatewayMessage msg, KutaResponse rsp) {
		success(msg.getChannel(),msg.getProtocol(), rsp, null);
	}
	
	protected void success(GatewayMessage msg, KutaResponse rsp, Object data) {
		success(msg.getChannel(),msg.getProtocol(), rsp, data);
	}
	
	protected void success(ActorRef channel, String protocal, KutaResponse rsp, Object data) {
		rsp.setStatus(ResponseStatus.OK);
		rsp.setMessage(Status.OK);
		
		if(data != null) {
			rsp.setData(data);
		}
		if(channel == null || channel.isTerminated()) {
			logger.info("通道已关闭,取消发送");
		}
		if(rsp.getElapsedTime() == null) {
			rsp.setElapsedTime(System.currentTimeMillis());
		}
		rsp.setElapsedTime(System.currentTimeMillis() - rsp.getElapsedTime());
		if(KutaConstants.PROTOCAL_WEBSOCKET.equals(protocal)) {
			channel.tell(rsp.toJSONString(), self());
		} else {
			channel.tell(rsp, self());
		}
	}
	
	
	protected void failure(GatewayMessage msg, 
			KutaResponse rsp, 
			String errorMsg, 
			int errorCode) {
		failure(msg.getChannel(), rsp, msg.getProtocol(), errorMsg, errorCode);
	}
	
	
	protected void failure(ActorRef channel, 
			KutaResponse rsp, 
			String protocal,
			String errorMsg, 
			int errorCode) {
		if(!channel.isTerminated()) {
			rsp.setMessage(errorMsg);
			rsp.setStatus(errorCode);
			if(KutaConstants.PROTOCAL_WEBSOCKET.equals(protocal)) {
				channel.tell(rsp.toJSONString(), self());
			} else {
				channel.tell(rsp, self());
			}
			
		} else {
			throw new KutaRuntimeException("Http通道已关闭");
		}
	}
	
	
}
