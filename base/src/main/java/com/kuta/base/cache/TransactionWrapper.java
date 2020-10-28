package com.kuta.base.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TransactionWrapper {
	private Transaction transaction;
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	private Jedis jedis;
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public Jedis getJedis() {
		return jedis;
	}
}
