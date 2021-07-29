package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.database.KutaExpireMapBiz;
import com.kuta.data.mysql.dao.PluginOrganizationParamMapper;
import com.kuta.data.mysql.pojo.PluginOrganizationParam;
import com.kuta.data.mysql.pojo.PluginOrganizationParamExample;
import com.kuta.data.mysql.pojo.PluginOrganizationParamKey;

public class PluginOrganizationParamBiz extends KutaExpireMapBiz<PluginOrganizationParam, PluginOrganizationParamKey> {

	public PluginOrganizationParamBiz() {
		super("PluginOrganizationParam_%s", 30 * 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, PluginOrganizationParamKey key) {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationParamMapper.class).deleteByPrimaryKey(key);
	}

	public int remove(SqlSession session, Integer poid) {
		PluginOrganizationParamExample example = new PluginOrganizationParamExample();
		example.createCriteria().andPoidEqualTo(poid);
		return session.getMapper(PluginOrganizationParamMapper.class).deleteByExample(example);
	}
	
	@Override
	public PluginOrganizationParamKey getKey(PluginOrganizationParam t) {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public PluginOrganizationParam get(SqlSession session, PluginOrganizationParamKey key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationParamMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, PluginOrganizationParam entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationParamMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, PluginOrganizationParam entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationParamMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<PluginOrganizationParam> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
