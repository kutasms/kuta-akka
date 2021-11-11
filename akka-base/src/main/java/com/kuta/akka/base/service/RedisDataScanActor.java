package com.kuta.akka.base.service;

import com.kuta.akka.base.KutaActor;
import com.kuta.akka.base.entity.KutaAkkaConstants;
import com.kuta.akka.base.entity.RedisScanRequestMessage;
import com.kuta.akka.base.entity.RedisScanResultMessage;
import com.kuta.akka.base.entity.RegistrationMessage;
import com.kuta.akka.base.entity.ScanCompletedMessage;
import com.kuta.akka.base.entity.ScanStartedMessage;
import com.kuta.base.cache.JedisPoolUtil;
import com.kuta.base.cache.JedisUtil;
import com.kuta.base.collection.KutaHashSet;
import com.kuta.base.util.KutaRedisUtil;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.ConsistentHashingRouter.ConsistentHashableEnvelope;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

/**
 * 数据扫描Actor
 * */
public class RedisDataScanActor extends KutaActor {

	/**
	 * hash路由
	 * */
	private final ActorRef hashRouter;
	/**
	 * 广播路由
	 * */
	private final ActorRef broadcastRouter;
	/**
	 * hash一致性路由种子数
	 * */
	private int keySeed = 0;
	/**
	 * 分割线
	 * */
	private final int split = 2000;
	/**
	 * 扫描结果集
	 * */
	private final KutaHashSet<String> result = new KutaHashSet<>();
	/**
	 * 可结束调度标识
	 * */
	private Cancellable cancellable;
	/**
	 * 使用自定义线程池创建的执行器上下文
	 * */
	private final ExecutionContext ec = getContext().getSystem().dispatchers().lookup(KutaAkkaConstants.BLOCKING_DISPATCHER);
	/**
	 * 任务名称
	 * */
	private String name = null;
	/**
	 * 扫描模板信息
	 * */
	private String pattern = null;
	
	/**
	 * 扫描模板信息
	 * */
	public RedisDataScanActor(ActorRef hashRouter, ActorRef broadcastRouter) {
		// TODO Auto-generated constructor stub
		this.hashRouter = hashRouter;
		this.broadcastRouter = broadcastRouter;
	}
	/**
	 * 获取属性集
	 * @param hashRouter 哈希一致性路由
	 * @param broadcastRouter 广播路由
	 * */
	public static Props Props(ActorRef hashRouter,ActorRef broadcastRouter) {
		return Props.create(RedisDataScanActor.class, hashRouter,broadcastRouter);
	}

	
	
	/**
	 * 消息处理函数
	 * */
	@Override
	public void onReceive(ReceiveBuilder rb) {
		// TODO Auto-generated method stub
		rb.match(RedisScanRequestMessage.class, msg -> {
			logger.info("收到{}Key扫描请求...", msg.getName());
			this.name = msg.getName();
			this.pattern = msg.getPattern();
			logger.info("通知数据扫描已经开始...");
			ScanStartedMessage scanStartedMessage = new ScanStartedMessage();
			scanStartedMessage.setName(msg.getName());
			broadcastRouter.tell(scanStartedMessage, self());
			Future<Boolean> future = Futures.future(()->{
				return KutaRedisUtil.exec(jedis->{
					try {
						jedis = JedisPoolUtil.getJedis();
						long start = System.currentTimeMillis();
						logger.info("开始扫描关键字");
						JedisUtil.scanKeys(jedis, msg.getPattern(), msg.getScanSize(), msg.getConsumeSize(), msg.getSleep(), x->{
							RedisScanResultMessage result = new RedisScanResultMessage();
							result.setName(msg.getName());
							result.setPattern(msg.getPattern());
							result.setSource("REDIS");
							result.setValues(x);
							
							keySeed++;
							hashRouter.tell(new ConsistentHashableEnvelope(result, String.format("K%s", keySeed)), self());
						});
						logger.info("关键字扫描完成,耗时:{}", System.currentTimeMillis() - start);
						
						ScanCompletedMessage completedMsg = new ScanCompletedMessage();
						completedMsg.setName(msg.getName());
						this.broadcastRouter.tell(completedMsg, self());
						return true;
					}
					catch (Exception e) {
						// TODO: handle exception
						logger.error(e, "REDIS扫描过程发生故障");
						return false;
					}
				});
			}, ec);
			future.onComplete(new OnComplete<Boolean>() {
				@Override
				public void onComplete(Throwable failure, Boolean success) throws Throwable {
					// TODO Auto-generated method stub
					logger.info("{}数据扫描完成,扫描{}", name, success ? "成功":"失败");
					self().tell(PoisonPill.getInstance(), ActorRef.noSender());
				}
			}, ec);
			
		}).match(String.class, msg -> {
			switch (msg) {
			case "dispatch":
				if (this.result.size() > 0) {
					RedisScanResultMessage result = new RedisScanResultMessage();
					result.setName(this.name);
					result.setPattern(this.pattern);
					result.setSource("REDIS");
					if (this.result.size() > split) {
						result.setValues(this.result.pop(split));
					} else {
						result.setValues(this.result.pop(this.result.size()));
					}
					keySeed++;
					hashRouter.tell(new ConsistentHashableEnvelope(result, String.format("K%s", keySeed)), self());
				}
				else {
					cancellable.cancel();
					self().tell(PoisonPill.getInstance(), ActorRef.noSender());
				}
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void onRegister(RegistrationMessage msg) {
		// TODO Auto-generated method stub

	}

}
