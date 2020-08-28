package com.kuta.akka.base;

import java.time.Duration;

import com.kuta.akka.base.entity.CalcFinishMessage;
import com.kuta.akka.base.entity.CalcInitMessage;
import com.kuta.akka.base.entity.CalcUnitCompleted;
import com.kuta.akka.base.entity.RedisScanResultMessage;
import com.kuta.akka.base.entity.DistributeMessage;
import com.kuta.akka.base.entity.ElectCompleted;
import com.kuta.akka.base.entity.KutaAkkaConstants;
import com.kuta.akka.base.entity.RegistrationMessage;
import com.kuta.akka.base.entity.ScanCompletedMessage;
import com.kuta.akka.base.entity.ScanStartedMessage;
import com.kuta.base.collection.KutaHashSet;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.ExecutionContext;


/**
 * 任务分发Actor
 * */
public abstract class TaskDistributeActor extends KutaActor {

	/**
	 * 目标Actor
	 * */
	private ActorRef targetActor;
	/**
	 * 可退出标识
	 * */
	private Cancellable cancellable;
	/**
	 * 扫描是否已开始
	 * */
	private boolean isScanStarted = false;
	/**
	 * 最大任务数
	 * */
	private Integer maxTaskSize = 10;
	/**
	 * 已添加任务数
	 * */
	private Integer addedTaskSize = 0;
	/**
	 * 名称
	 * */
	private final String name;
	/**
	 * 任务间隔
	 * */
	private final long interval;
	/**
	 * 等待分发的键集合
	 * */
	private KutaHashSet<String> waitDisKeys = new KutaHashSet<>();
	/**
	 * 使用自定义线程池的执行器上下文
	 * */
	private final ExecutionContext ec = getContext().getSystem().dispatchers().lookup(KutaAkkaConstants.BLOCKING_DISPATCHER);
	
	/**
	 * 消息过载提示
	 * */
	private static final String MSG_BACKPRESS = "计算器过载,截流..";
	/**
	 * 消息分割数量
	 * */
	private static final Integer MSG_SPLIT = 500;
	/**
	 * 构造函数
	 * @param maxTaskSize 最大任务数
	 * @param name 名称
	 * @param interval 时间间隔
	 * */
	public TaskDistributeActor(
			Integer maxTaskSize,
			String name,long interval) {
		// TODO Auto-generated constructor stub
		this.targetActor = setTargetActor();
		this.maxTaskSize = maxTaskSize;
		this.name = name;
		this.interval = interval;
	}
	
	/**
	 * 构建Actor属性
	 * @param clazz 类型,实现类
	 * @param maxTaskSize 最大任务数
	 * @param name 名称
	 * @param interval 时间间隔
	 * */
	public static <T> Props props(
			Class<T> clazz,
			Integer maxTaskSize,
			String name,
			long interval) {
		return Props.create(clazz,
				maxTaskSize,name, interval);
	}
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		CalcInitMessage calcInitMsg = new CalcInitMessage();
		targetActor.tell(calcInitMsg, self());
	}

	/**
	 * 投票完成事件
	 * @param msg 投票完成消息
	 * */
	public abstract void onElectCompleted(ElectCompleted msg);
	/**
	 * 设置处理消息的目标Actor
	 * */
	public abstract ActorRef setTargetActor();
	@Override
	public void onReceive(ReceiveBuilder rb) {
		// TODO Auto-generated method stub
		rb.match(String.class, msg->{
		}).match(DistributeMessage.class, msg->{
			if(this.waitDisKeys.size() > 0) {
				if(addedTaskSize >= maxTaskSize) {
					logger.info(MSG_BACKPRESS);
					return;
				}
				addedTaskSize++;
				RedisScanResultMessage message = new RedisScanResultMessage();
				message.setName(this.name);
				if (this.waitDisKeys.size() >= MSG_SPLIT) {
					message.setValues(this.waitDisKeys.pop(MSG_SPLIT));
				} else {
					message.setValues(this.waitDisKeys.pop(this.waitDisKeys.size()));
				}
				this.targetActor.tell(message, self());
			}
			
			if(this.waitDisKeys.size() == 0 && !isScanStarted) {
				cancellable.cancel();
				this.addedTaskSize = 0;
				
				CalcFinishMessage finishMessage = new CalcFinishMessage();
				finishMessage.setName(name);
				getContext().parent().tell(finishMessage, self());
			}
		}).match(ScanStartedMessage.class, msg->{
			isScanStarted = true;
			DistributeMessage distributeMessage = new DistributeMessage();
			cancellable = getContext().system().scheduler().
					scheduleWithFixedDelay(Duration.ZERO, 
					Duration.ofMillis(interval), self(), distributeMessage, 
					ec, 
					ActorRef.noSender());
		}).match(ScanCompletedMessage.class, msg->{
			isScanStarted = false;
		}).match(CalcUnitCompleted.class, msg->{
			if(addedTaskSize>0) {
				addedTaskSize--;
			}
		}).match(RedisScanResultMessage.class, msg -> {
			this.waitDisKeys.addAll(msg.getValues());
		}).match(ElectCompleted.class, msg->{
			this.onElectCompleted(msg);
		});
	}

	@Override
	public void onRegister(RegistrationMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
