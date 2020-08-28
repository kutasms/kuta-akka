package com.kuta.akka.base;

import com.kuta.akka.base.entity.KutaAkkaConstants;
import com.kuta.akka.util.DatabaseBatchExceUtil;

import akka.actor.ActorRef;

/**
 * <pre>
 * 附带数据库批量执行器的Actor
 * 同一个akka节点单例存在此Actor
 * 请使用KutaAkkaDBUtil.tell方法将需要执行的数据库请求传递至批量处理器
 * 
 * DBExecRequestMessage msg = new DBExecRequestMessage();
 * msg.setConsumer(session->{
 *	   PlayerMapper mapper = session.getMapper(PlayerMapper.class);
 *	   ...
 * });
 * KutaAkkaDBUtil.tell(msg);
 * </pre>
 * */
public abstract class KutaActorWithDBExector extends KutaActor{
	
	private static final String DB_SERVICE_ACTOR_NAME = "db-exec-actor";
	
	/**
	 * 初始化数据库批量处理Actor
	 * */
	private final ActorRef dbServiceActor = getContext().
			actorOf(DBExecActor.props(KutaAkkaConstants.BLOCKING_DISPATCHER),
					DB_SERVICE_ACTOR_NAME);
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		DatabaseBatchExceUtil.setDBExector(dbServiceActor);
	}

}
