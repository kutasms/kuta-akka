package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.database.KutaExpireMapBiz;
import com.kuta.base.util.KutaUtil;
import com.kuta.data.mysql.dao.PluginHttpReportMapper;
import com.kuta.data.mysql.dao.extend.PluginHttpReportMapperExt;
import com.kuta.data.mysql.pojo.PluginHttpReport;
import com.kuta.data.mysql.pojo.PluginHttpReportExample;
import com.kuta.data.mysql.pojo.extend.PluginHttpReportExt;

public class PluginHttpReportBiz extends KutaExpireMapBiz<PluginHttpReport, Long> {

	public PluginHttpReportBiz() {
		super("PluginHttpReport_%s", 30 * 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Long key) {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpReportMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Long getKey(PluginHttpReport t) {
		// TODO Auto-generated method stub
		return t.getPhrid();
	}

	@Override
	public PluginHttpReport get(SqlSession session, Long key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpReportMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, PluginHttpReport entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpReportMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, PluginHttpReport entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginHttpReportMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<PluginHttpReport> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PluginHttpReportExt getOneByPoid(SqlSession session, Integer poid, String key) {
		PluginHttpReportExample example  = new PluginHttpReportExample();
		example.createCriteria().andPoidEqualTo(poid).andKeyEqualTo(key);
		List<PluginHttpReportExt> list = session.getMapper(PluginHttpReportMapperExt.class).selectExtByExample(example);
		if(KutaUtil.isEmptyColl(list)) {
			return null;
		}
		return list.get(0);
	}

}
