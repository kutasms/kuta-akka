package com.kuta.base.util;

import com.kuta.base.cache.JedisClient;
import com.kuta.base.cache.JedisPoolUtil;

import akka.actor.ActorRef;

/**
 * Player验证工具
 * */
public class PlayerVerifyUtil {
	
	/**
	 * 验证当前操作是否为玩家自己的操作
	 * @param jedis redis连接
	 * @param pid 玩家编号
	 * @param uid actor编号
	 * @return true:是玩家自己的操作 false:非法操作
	 * */
	public static boolean self(JedisClient jedis, int pid, int uid) {
		String md5 = KutaUtil.intToBase64(uid);
		String val = jedis.hget("map_player_channel", md5);
		if(val == null) {
			System.out.println("未查到相关链接的映射信息");
			return false;
		}
		if(val.equals(KutaUtil.intToBase64(pid))) {
			return true;
		}
		return false;
	}
	/**
	 * 验证当前操作是否为玩家自己的操作
	 * @param jedis redis连接
	 * @param actorRef actor对象
	 * @return true:是玩家自己的操作 false:非法操作
	 * @throws Exception 
	 * */
	public static boolean self(int pid, ActorRef actorRef) {
		try {
			return KutaRedisUtil.exec(jedis->{
				return self(jedis, pid, actorRef.path().uid());
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 验证当前操作是否为玩家自己的操作
	 * @param pid 玩家编号
	 * @param uid actor编号
	 * @return true:是玩家自己的操作 false:非法操作
	 * @throws Exception 
	 * */
	public static boolean self(int pid, int uid) throws Exception {
		return KutaRedisUtil.exec(jedis->{
			jedis = JedisPoolUtil.getJedis();
			return self(jedis, pid, uid);
		});
	}
	
	/**
	 * 将玩家添加到映射中
	 * @param pid 玩家编号
	 * @param uid actor编号
	 * */
	public static void mapping(Integer pid, int uid) {
		JedisClient jedis = null;
		try {
			jedis = JedisPoolUtil.getJedis();
			jedis.hset("map_player_channel",KutaUtil.intToBase64(uid),KutaUtil.intToBase64(pid));
		}
		finally {
			JedisPoolUtil.release(jedis.getJedis());
		}
	}
	
	public static void logged(Integer pid) {
		
	}
	/**
	 * 将玩家添加到映射中
	 * @param jedis redis连接
	 * @param pid 玩家编号
	 * @param uid actor编号
	 * */
	public static void mapping(JedisClient jedis,Integer pid, int uid) {
		jedis.hset("map_player_channel",KutaUtil.intToBase64(uid),KutaUtil.intToBase64(pid));
	}
	
	/**
	 * 将玩家从映射中移除
	 * @param jedis redis连接
	 * @param pid 玩家编号
	 * @param uid actor编号
	 * @throws Exception 
	 * */
	public static void unmapping(int uid) throws Exception {
		KutaRedisUtil.exec(jedis->{
			jedis.hdel("map_player_channel", KutaUtil.intToBase64(uid));
		});
	}
	public static void unmapping(JedisClient jedis,int uid) throws Exception {
		jedis.hdel("map_player_channel", KutaUtil.intToBase64(uid));
	}
}
