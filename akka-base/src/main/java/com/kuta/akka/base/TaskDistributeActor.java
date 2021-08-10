package com.kuta.akka.base;

import java.time.Duration;

import com.kuta.akka.base.entity.CalcFinishMessage;
import com.kuta.akka.base.entity.DataScanStartNotice;
import com.kuta.akka.base.entity.DistributeMessage;
import com.kuta.akka.base.entity.ElectCompleted;
import com.kuta.akka.base.entity.FragmentCalculateCompleted;
import com.kuta.akka.base.entity.KutaAkkaConstants;
import com.kuta.akka.base.entity.RedisScanResultMessage;
import com.kuta.akka.base.entity.RegistrationMessage;
import com.kuta.akka.base.entity.ScanCompletedMessage;
import com.kuta.akka.base.entity.ScanStartedMessage;
import com.kuta.base.collection.KutaHashSet;
import com.kuta.base.exception.KutaIllegalArgumentException;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.ExecutionContext;


/**
 * 任务分发Actor
 * */
public abstract class TaskDistributeActor extends KutaActorWithTimers {

	/**
	 * 	目标Actor
	 * */
	protected ActorRef targetActor;
	
	/**
	 * 	计算器类型
	 * */
	protected Class<? extends KutaActor> targetClazz;
	/**
	 * 	可退出标识
	 * */
	protected Cancellable cancellable;
	/**
	 *	 扫描是否已开始
	 * */
	protected boolean isScanStarted = false;
	/**
	 * 	最大任务数
	 * */
	protected final Integer maxTaskSize;
	/**
	 * 	已添加任务数
	 * */
	protected Integer addedTaskSize = 0;
	/**
	 * 	名称
	 * */
	protected final String name;
	
	/**
	 * 	是否限流
	 * */
	protected final boolean isLimited;
	
	/**
	 * 	任务间隔, 默认1000毫秒
	 * */
	protected final long interval;
	/**
	 * 	等待分发的键集合
	 * */
	protected KutaHashSet<String> waitDisKeys = new KutaHashSet<>();
	/**
	 * 	使用自定义线程池的执行器上下文
	 * */
	protected final ExecutionContext ec = getContext().getSystem().dispatchers().lookup(KutaAkkaConstants.BLOCKING_DISPATCHER);
	
	/**
	 * 	消息过载提示
	 * */
	protected static final String MSG_BACKPRESS = "计算器过载,截流..";
	/**
	 * 	消息分割数量
	 * */
	protected static final Integer MSG_SPLIT = 500;
	/**
	 * 构造函数
	 * @param maxTaskSize 最大任务数
	 * @param name 名称
	 * @param interval 时间间隔
	 * */
	public TaskDistributeActor(
			Integer maxTaskSize,
			String name,Long interval,boolean isLimited) {
		// TODO Auto-generated constructor stub
		this.targetActor = setTargetActor();
		this.targetClazz = setTargetClazz();
		if(this.targetClazz == null) {
			throw new KutaIllegalArgumentException("请调用setTargetClazz设置目标计算器类型");
		}
		this.maxTaskSize = maxTaskSize == null ? 10 : maxTaskSize;
		this.name = name == null ? "未设置名称任务" : name;
		this.interval = interval == null ? 1000L : interval;
		this.isLimited = isLimited;
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
	}

	/**
	 * 投票完成事件,请使用onStart替换
	 * @param msg 投票完成消息
	 * */
	@Deprecated
	public abstract void onElectCompleted(ElectCompleted msg);
	
	/**
	 * 	标识数据扫描任务已经开始
	 * */
	public abstract void onStart(DataScanStartNotice notice);
	/**
	 * 	设置处理消息的目标Actor
	 * 	已废弃，请使用setTargetClz方法
	 * */
	@Deprecated
	public abstract ActorRef setTargetActor();
	
	public abstract Class<? extends KutaActor> setTargetClazz();
	
	public abstract Props calcActorProps(DistributeMessage msg);
	
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
				ActorRef buildCalcActor = getContext().actorOf(
					calcActorProps(msg)
					.withDispatcher(KutaAkkaConstants.BLOCKING_DISPATCHER), 
						String.format("bld_calc_%s", 
								System.currentTimeMillis()));
				buildCalcActor.tell(message, self());
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
			if(isLimited) {
				//开启限流
				DistributeMessage distributeMessage = new DistributeMessage();
				cancellable = getContext().system().scheduler().
						scheduleWithFixedDelay(Duration.ZERO, 
						Duration.ofMillis(interval), self(), distributeMessage, 
						ec, 
						ActorRef.noSender());
			}
		}).match(ScanCompletedMessage.class, msg->{
			isScanStarted = false;
		}).match(FragmentCalculateCompleted.class, msg->{
			sender().tell(PoisonPill.getInstance(), ActorRef.noSender());
			if(addedTaskSize>0) {
				addedTaskSize--;
			}
		}).match(RedisScanResultMessage.class, msg -> {
			if(isLimited) {
				this.waitDisKeys.addAll(msg.getValues());
			} else {
				ActorRef buildCalcActor = getContext().actorOf(
						Props.create(targetClazz)
						.withDispatcher(
								KutaAkkaConstants.BLOCKING_DISPATCHER), 
								String.format("bld_calc_%s", System.currentTimeMillis()));
				buildCalcActor.tell(msg, self());
			}
		}).match(ElectCompleted.class, msg->{
			this.onElectCompleted(msg);
		}).match(DataScanStartNotice.class, notice->{
			this.onStart(notice);
		});
	}

	@Override
	public void onRegister(RegistrationMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
