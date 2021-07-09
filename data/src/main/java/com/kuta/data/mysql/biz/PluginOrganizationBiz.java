package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.base.util.PageWrapper;
import com.kuta.data.mysql.dao.PluginOrganizationMapper;
import com.kuta.data.mysql.dao.extend.PluginOrganizationMapperExt;
import com.kuta.data.mysql.pojo.PluginOrganization;
import com.kuta.data.mysql.pojo.PluginOrganizationExample;
import com.kuta.data.mysql.pojo.extend.PluginOrganizationExt;

public class PluginOrganizationBiz extends KutaMapBiz<PluginOrganization, Integer> {

	public PluginOrganizationBiz() {
		super("PluginOrganization_%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(PluginOrganization t) {
		// TODO Auto-generated method stub
		return t.getPoid();
	}

	@Override
	public PluginOrganization get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, PluginOrganization entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, PluginOrganization entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginOrganizationMapper.class).updateByPrimaryKeySelective(entity);
	}

	public PluginOrganizationExt getExt(SqlSession session, Integer poid) {
		return session.getMapper(PluginOrganizationMapperExt.class).selectExtByPrimaryKey(poid);
	}
	
	@Override
	public List<PluginOrganization> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public PageWrapper<PluginOrganization> search(
			SqlSession session,
			Integer oid,
			Boolean enable,
			String status,
			int pageNum,
			int pageSize){
		PluginOrganizationExample example = new PluginOrganizationExample();
		example.createCriteria()
		.andOidEqualTo(oid > 0, oid)
		.andEnableEqualTo(enable != null, enable)
		.andStatusEqualTo(status!=null, status);
		PageHelper.startPage(pageNum, pageSize);
		List<PluginOrganization> list = session.getMapper(PluginOrganizationMapper.class).selectByExample(example);
		return new PageWrapper<PluginOrganization>(list);
	}
}
