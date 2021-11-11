package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.database.KutaExpireMapBiz;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.util.PageWrapper;
import com.kuta.data.mysql.dao.RoleMapper;
import com.kuta.data.mysql.pojo.Role;
import com.kuta.data.mysql.pojo.RoleExample;
import com.kuta.data.mysql.pojo.RoleExample.Criteria;

public class RoleBiz extends KutaExpireMapBiz<Role, Integer> {

	public RoleBiz() {
		super("Role_%s", 30 * 60);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(RoleMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(Role t) {
		// TODO Auto-generated method stub
		return t.getRid();
	}

	@Override
	public Role get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(RoleMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, Role entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(RoleMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, Role entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(RoleMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<Role> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PageWrapper<Role> search(
			SqlSession session,
			String key,
			Integer did,
			int pageNum,
			int pageSize){
		RoleExample example = new RoleExample();
		Criteria criteria = example.createCriteria();
		if(key!=null) {
			criteria.andNameLike(String.format(
					KutaConstants.SQL_LIKE_TEMPLATE, key));
			
		}
		if(did>0) {
			criteria.andDidEqualTo(did);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list = session.getMapper(RoleMapper.class).selectByExample(example);
		return new PageWrapper<Role>(list);
	}
}
