package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.util.PageWrapper;
import com.kuta.data.mysql.dao.PermissionMapper;
import com.kuta.data.mysql.pojo.Permission;
import com.kuta.data.mysql.pojo.PermissionExample;
import com.kuta.data.mysql.pojo.PermissionExample.Criteria;

public class PermissionBiz extends KutaMapBiz<Permission, Integer> {

	public PermissionBiz() {
		super("Permission_%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(PermissionMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(Permission t) {
		// TODO Auto-generated method stub
		return t.getPid();
	}

	@Override
	public Permission get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PermissionMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, Permission entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PermissionMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, Permission entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(PermissionMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<Permission> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PageWrapper<Permission> search(
			SqlSession session,
			String key,
			String level,
			int parentId,
			int pageNum,
			int pageSize,
			String orderByClassify,
			String orderByDeep,
			String orderByIdx){
		PermissionExample example = new PermissionExample();
		Criteria criteria = example.createCriteria();
		if(key!=null) {
			criteria.andNameLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
		}
		if(level!=null) {
			criteria.andLevelLike(String.format(KutaConstants.SQL_LIKE_TEMP_ONLY_RIGHT, level));
		}
		
		if(parentId > 0) {
			criteria.andParentRidEqualTo(parentId);
		}
		
		example.setOrderByClause(String.format("classify %s,deep %s,idx %s", 
				orderByClassify,orderByDeep,orderByIdx));
		
		PageHelper.startPage(pageNum, pageSize);
		List<Permission> list = session.getMapper(PermissionMapper.class).selectByExample(example);
		return new PageWrapper<Permission>(list);
	}
}
