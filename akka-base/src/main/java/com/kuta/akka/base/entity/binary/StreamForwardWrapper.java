package com.kuta.akka.base.entity.binary;

import java.nio.ByteBuffer;

import akka.actor.ActorRef;

public class StreamForwardWrapper extends BinaryMessageHeader{
	private ActorRef channel = null;
	private boolean isForward = false;
	private ByteBuffer buffer;
	private byte[] tempHeader;
	
	public ActorRef getChannel() {
		return channel;
	}
	public void setChannel(ActorRef channel) {
		this.channel = channel;
	}
	public boolean isForward() {
		return isForward;
	}
	public void setForward(boolean isForward) {
		this.isForward = isForward;
	}
	public byte[] getTempHeader() {
		return tempHeader;
	}
	public void setTempHeader(byte[] tempHeader) {
		this.tempHeader = tempHeader;
	}
	public ByteBuffer getBuffer() {
		return buffer;
	}
	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}
}
