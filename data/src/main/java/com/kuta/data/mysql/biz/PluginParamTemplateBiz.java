package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.data.mysql.dao.PluginParamTemplateMapper;
import com.kuta.data.mysql.pojo.PluginParamTemplate;
import com.kuta.data.mysql.pojo.PluginParamTemplateExample;

public class PluginParamTemplateBiz extends KutaMapBiz<PluginParamTemplate, Integer> {

	public PluginParamTemplateBiz() {
		super("PluginParamTemplate_%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(PluginParamTemplateMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(PluginParamTemplate t) {
		// TODO Auto-generated method stub
		return t.getPptid();
	}

	@Override
	public PluginParamTemplate get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginParamTemplateMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, PluginParamTemplate entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginParamTemplateMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, PluginParamTemplate entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginParamTemplateMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<PluginParamTemplate> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public List<PluginParamTemplate> getList(SqlSession session,Integer pid){
		PluginParamTemplateExample example = new PluginParamTemplateExample();
		example.createCriteria().andPidEqualTo(pid);
		return session.getMapper(PluginParamTemplateMapper.class).selectByExample(example);
	}
}
