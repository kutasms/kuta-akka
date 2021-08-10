package com.kuta.data.mysql.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.cache.JedisClient;
import com.kuta.base.database.KutaExpireMapBiz;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.util.PageWrapper;
import com.kuta.data.mysql.dao.ScriptMapper;
import com.kuta.data.mysql.pojo.Organization;
import com.kuta.data.mysql.pojo.Script;
import com.kuta.data.mysql.pojo.ScriptExample;

public class ScriptBiz extends KutaExpireMapBiz<Script, Long> {

//	private OrganizationBiz organizationBiz = new OrganizationBiz();
	
	public ScriptBiz() {
		super("Script_%s", 30 * 60);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Long key) {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Long getKey(Script t) {
		// TODO Auto-generated method stub
		return t.getSid();
	}

	@Override
	public Script get(SqlSession session, Long key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, Script entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, Script entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(ScriptMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<Script> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public PageWrapper<Script> search(SqlSession session,JedisClient jedis,Integer category, 
			Integer uid, Integer oid, Boolean mergeMaster, String key, int pageNum, int pageSize){
		ScriptExample example = new ScriptExample();
		List<Integer> oids = new ArrayList<Integer>();
		if(mergeMaster != null && mergeMaster) {
			Organization master = OrganizationBiz.getMaster(session,jedis);
			oids.add(master.getOid());
		}
		if(oid!= null && !oids.contains(oid)) {
			oids.add(oid);
		}
		if(key == null) {
			ScriptExample.Criteria criteria = example.createCriteria();
			criteria.andUidEqualTo(uid).andCategoryEqualTo(category>0, category).andOidIn(oids);
		} else {
			example.or()
					.andUidEqualTo(oid == null , uid)
					.andCategoryEqualTo(category > 0, category)
					.andOidIn(uid == null , oids)
					.andNameLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
			example.or()
					.andUidEqualTo(oid == null, uid)
					.andCategoryEqualTo(category > 0, category)
					.andOidIn(uid == null, oids)
					.andDescLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
		}
		
		ScriptMapper mapper = session.getMapper(ScriptMapper.class);
		PageHelper.startPage(pageNum, pageSize);
		List<Script> list = mapper.selectByExample(example);
		return new PageWrapper<Script>(list);
	}
}
