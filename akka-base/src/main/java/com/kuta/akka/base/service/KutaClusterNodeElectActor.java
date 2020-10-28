package com.kuta.akka.base.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.kuta.akka.base.KutaActorWithClusterQuartz;
import com.kuta.akka.base.entity.ElectCompleted;
import com.kuta.akka.base.entity.ElectMessage;

import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

/**
 * kuta集群节点选民<br/>
 * 从多个相同角色的节点中推选出其中一个节点来执行上层工作<br/>
 * 比如需要先从redis海量数据中心中模糊查找某个key,再将数据以哈希一致性路由分发到其他节点<br/>
 * <b style=
 * "color:red;">注意：需要将akka路由配置中allow-local-routees设置为on，将本节点也添加到路由表中</b>
 */
public abstract class KutaClusterNodeElectActor extends KutaActorWithClusterQuartz {

	protected Map<String, Map<String, ElectMessage>> electMap = new HashMap<>();

	/**
	 * 业务流程:<br/>
	 * 选民Aoctor生成调度，按规定的调度执行推举<br/>
	 * 1. 各节点分别发起广播，告知当前节点的随机数<br/>
	 * 2. 各节点在收到推举消息的时候，将此节点的随机数保存<br/>
	 * 3. 判断所有节点的随机数是否收集完整<br/>
	 * 4. 根据所有节点的随机数取最大值，最大值的节点作为领导节点<br/>
	 * 5. 领导节点开始进行上层工作<br/>
	 * 
	 * @param router
	 *            集群节点路由
	 */
	protected void startElect(ActorRef router,String role, String electName, int min, int max) {
		
		if (!electMap.containsKey(electName)) {
			logger.info("创建推举MapKey:{}", electName);
			electMap.put(electName, new HashMap<String, ElectMessage>());
		}
		Random random = new Random();
		// min:5,max:50 0-45
//		int seed = random.nextInt((max - min)) + min;
		ElectMessage electMessage = new ElectMessage();
		electMessage.setActorRef(self());
		electMessage.setRole(role);
		electMessage.setSeed(Math.abs(UUID.randomUUID().hashCode()));
		electMessage.setHostport(this.cluster.selfAddress().hostPort());
		electMessage.setName(electName);
		logger.info("将本节点加入投票Map中.");
//		map.put(this.cluster.selfAddress().hostPort(), electMessage);
		self().tell(electMessage, self());
		router.tell(electMessage, self());
		logger.info("向worker角色的集群节点广播发送推选投票信息");
	}

	@Override
	public void preStart() {
		// TODO Auto-generated method stub
		super.preStart();
	}

	public abstract void onRecv(ReceiveBuilder rb);
	
	@Override
	public void onReceive(ReceiveBuilder rb) {
		// TODO Auto-generated method stub
		rb.match(ElectMessage.class, msg -> {
			logger.info("收到推举消息:{}",JSONObject.toJSONString(msg));
			if (!electMap.containsKey(msg.getName())) {
				electMap.put(msg.getName(), new HashMap<String, ElectMessage>());
			}
			Map<String, ElectMessage> val = electMap.get(msg.getName());
			val.put(msg.getActorRef().path().address().hostPort(), msg);
			Set<String> reachableSet = this.registerMap.get(msg.getRole());
			logger.info("已将消息放入Map,当前Map.Size:{}",val.size());
			if(val.size() >= this.registerMap.get(msg.getRole()).size()) {
				//已经收集的数量大于当前可达同角色集群节点数量
				logger.info("已收集到足够的推举投票信息.");
				Integer max = 0;
				String hostport = "";
				for(Map.Entry<String, ElectMessage> entry : val.entrySet()) {
					//判断当前可达节点中是否包含了选举消息送达节点
					if(reachableSet.contains(entry.getValue().getHostport())) {
						if(entry.getValue().getSeed() > max) {
							max = entry.getValue().getSeed();
							hostport = entry.getValue().getHostport();
						}
						logger.info("可达节点中包含当前节点，赋值max:{},hostport:{}",max,hostport);
					}
					else {
						logger.info("可达节点中不包含当前节点,{}", entry.getValue().getHostport());
					}
				}
				if(hostport.equals(this.hostport)) {
					logger.info("选举结果为本节点执行");
					//选举结果为本节点执行
					ElectCompleted result = new ElectCompleted();
					result.setName(msg.getName());
					self().tell(result, ActorRef.noSender());
				}
				else {
					logger.info("选举结果为其他节点执行,本地:{},预期:{}",this.hostport,hostport);
				}
				this.electMap.remove(msg.getName());
			} else {
				logger.info("还未收集到足够的推举投票信息,need:{},current:{}", this.registerMap.get(msg.getRole()).size(),val.size());
			}
		});
		onRecv(rb);
	}

}
