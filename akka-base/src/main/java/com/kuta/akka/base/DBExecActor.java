package com.kuta.akka.base;

import java.time.Duration;
import java.util.Set;

import com.kuta.akka.base.entity.DBExecRequestMessage;
import com.kuta.akka.base.entity.RegistrationMessage;
import com.kuta.base.collection.KutaHashSet;
import com.kuta.base.database.KutaSQLUtil;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.ExecutionContext;

public class DBExecActor extends KutaActor {
	KutaHashSet<DBExecRequestMessage> set = new KutaHashSet<>();
	
	public DBExecActor(String dispatcherName) {
		 ec = getContext().getSystem().dispatchers()
					.lookup(dispatcherName);
	}
	
	public static Props props(String dispatcherName) {
		return Props.create(DBExecActor.class,dispatcherName);
	}
	
	private final ExecutionContext ec;

	@Override
	public void onReceive(ReceiveBuilder rb) {
		// TODO Auto-generated method stub
		rb.match(DBExecRequestMessage.class, msg -> {
			this.insert(msg);
		}).match(String.class, msg -> {
			switch (msg) {
			case "exec":
				this.exec();
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		getContext().getSystem().scheduler().scheduleAtFixedRate(Duration.ZERO, Duration.ofMillis(1000), self(), "exec",
				ec, ActorRef.noSender());
	}

	private void insert(DBExecRequestMessage msg) {
		set.add(msg);
	}

	private void exec() {
		if(set.size()>0) {
			Set<DBExecRequestMessage> msgs = set.pop(set.size() > 500 ? 500 : set.size());
			try {
				KutaSQLUtil.execBatch(session -> {
					msgs.forEach(msg -> {
						try {
							msg.getConsumer().accept(session);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onRegister(RegistrationMessage msg) {
		// TODO Auto-generated method stub

	}

}
