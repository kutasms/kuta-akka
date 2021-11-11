package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.database.KutaExpireMapBiz;
import com.kuta.data.mysql.dao.ScriptCategoryMapper;
import com.kuta.data.mysql.pojo.ScriptCategory;
import com.kuta.data.mysql.pojo.ScriptCategoryExample;

public class ScriptCategoryBiz extends KutaExpireMapBiz<ScriptCategory, Integer>{

	public ScriptCategoryBiz() {
		super("ScriptCategory_%s", 30 * 60);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptCategoryMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(ScriptCategory t) {
		// TODO Auto-generated method stub
		return t.getCid();
	}

	@Override
	public ScriptCategory get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptCategoryMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, ScriptCategory entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptCategoryMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, ScriptCategory entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptCategoryMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<ScriptCategory> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ScriptCategory> getList(SqlSession session,Integer oid, Integer parentId){
		ScriptCategoryExample example = new ScriptCategoryExample();
		example.createCriteria().andOidEqualTo(oid).andParentCidEqualTo(parentId);
		return session.getMapper(ScriptCategoryMapper.class).selectByExample(example);
	}
}
