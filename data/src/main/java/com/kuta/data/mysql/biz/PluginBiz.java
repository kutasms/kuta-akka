package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.database.KutaExpireMapBiz;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.util.PageWrapper;
import com.kuta.data.mysql.dao.PluginMapper;
import com.kuta.data.mysql.pojo.Plugin;
import com.kuta.data.mysql.pojo.PluginExample;

public class PluginBiz  extends KutaExpireMapBiz<Plugin, Integer>{

	public PluginBiz() {
		super("Plugin_%s", 30 * 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(PluginMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(Plugin t) {
		// TODO Auto-generated method stub
		return t.getPid();
	}

	@Override
	public Plugin get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, Plugin entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, Plugin entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<Plugin> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public PageWrapper<Plugin> search(
			SqlSession session, 
			int pageNum, 
			int pageSize,
			String key,
			String status,
			List<Integer> ownerIn){
		PluginMapper mapper = session.getMapper(PluginMapper.class);
		PluginExample example = new PluginExample();
		if(key!=null) {
			example.or().andNameLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key))
			.andOwnerIn(ownerIn!=null, ownerIn).andStatusEqualTo(status !=null, status);
			example.or().andDescLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key))
			.andOwnerIn(ownerIn!=null, ownerIn).andStatusEqualTo(status !=null, status);
		} else {
			example.createCriteria().andOwnerIn(ownerIn!=null, ownerIn).andStatusEqualTo(status !=null, status);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Plugin> list = mapper.selectByExample(example);
		return new PageWrapper<Plugin>(list);
	}
	
}
