package com.kuta.akka.base;

import com.kuta.akka.base.entity.KutaAkkaConstants;
import com.kuta.akka.util.DatabaseBatchExceUtil;
import com.kuta.base.exception.KutaRuntimeException;

import akka.actor.ActorRef;

/**
 * 附带集群信息和数据库批处理器的Actor
 * */
public abstract class KutaActorWithClusterDBExector extends KutaActorWithCluster {

	private final ActorRef dbServiceActor = getContext().
			actorOf(DBExecActor.props(KutaAkkaConstants.BLOCKING_DISPATCHER),"db-exec-actor");

	private final static String ERROR_INFO = "重复设置数据库批量处理器";
	
	@Override
	public void preStart() {
		// TODO Auto-generated method stub
		super.preStart();
		try {
			DatabaseBatchExceUtil.setDBExector(dbServiceActor);
		} catch (KutaRuntimeException e) {
			// TODO Auto-generated catch block
			logger.error(ERROR_INFO,e);
		}
	}

}
