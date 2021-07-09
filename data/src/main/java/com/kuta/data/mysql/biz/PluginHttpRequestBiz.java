package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.base.util.KutaUtil;
import com.kuta.data.mysql.dao.PluginHttpRequestMapper;
import com.kuta.data.mysql.dao.extend.PluginHttpRequestMapperExt;
import com.kuta.data.mysql.pojo.PluginHttpRequest;
import com.kuta.data.mysql.pojo.PluginHttpRequestExample;
import com.kuta.data.mysql.pojo.extend.PluginHttpRequestExt;

public class PluginHttpRequestBiz extends KutaMapBiz<PluginHttpRequest, Long>{

	public PluginHttpRequestBiz() {
		super("PluginHttpRequest_%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Long key) {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpRequestMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Long getKey(PluginHttpRequest t) {
		// TODO Auto-generated method stub
		return t.getPhrid();
	}

	@Override
	public PluginHttpRequest get(SqlSession session, Long key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpRequestMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, PluginHttpRequest entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpRequestMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, PluginHttpRequest entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpRequestMapper.class).updateByPrimaryKeySelective(entity);
	}

	public PluginHttpRequestExt getOneByPoid(SqlSession session, Integer poid, String key) {
		PluginHttpRequestExample example  = new PluginHttpRequestExample();
		example.createCriteria().andPoidEqualTo(poid).andKeyEqualTo(key);
		List<PluginHttpRequestExt> list = session.getMapper(PluginHttpRequestMapperExt.class).selectExtByExample(example);
		if(KutaUtil.isEmptyColl(list)) {
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public List<PluginHttpRequest> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
