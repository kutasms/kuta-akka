package com.kuta.database.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.kuta.database.mysql.dao.PluginScriptRelMapper;
import com.kuta.database.mysql.pojo.PluginScriptRel;
import com.kuta.database.mysql.pojo.PluginScriptRelExample;
import com.kuta.database.mysql.pojo.PluginScriptRelKey;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.base.exception.KutaRuntimeException;
import com.kuta.base.util.KutaUtil;

public class PluginScriptBiz  extends KutaMapBiz<PluginScriptRel, PluginScriptRelKey>{

	public PluginScriptBiz() {
		super("PluginScript_%s_%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, PluginScriptRelKey key) {
		// TODO Auto-generated method stub
		return session.getMapper(PluginScriptRelMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public PluginScriptRelKey getKey(PluginScriptRel t) {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public PluginScriptRel get(SqlSession session, PluginScriptRelKey key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginScriptRelMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, PluginScriptRel entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginScriptRelMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, PluginScriptRel entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PluginScriptRelMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<PluginScriptRel> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String formatCacheKeyByTKey(PluginScriptRelKey key) throws KutaRuntimeException {
		// TODO Auto-generated method stub
		return formatCacheKey(key.getPid(), key.getOid());
	}
	
	public Short maxIndex(SqlSession session,Integer pid) {
		return session.getMapper(PluginScriptRelMapper.class).maxIndex(pid);
	}
	
	public PluginScriptRel search(SqlSession session,Integer pid, short index) {
		PluginScriptRelExample example = new PluginScriptRelExample();
		example.createCriteria().andPidEqualTo(pid).andIndexEqualTo(index);
		List<PluginScriptRel> list = session.getMapper(PluginScriptRelMapper.class).selectByExample(example);
		if(KutaUtil.isEmptyColl(list)) {
			return null;
		}
		return list.get(0);
	}
}
