package com.kuta.data.mysql.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.util.PageWrapper;
import com.kuta.data.mysql.dao.DepartmentMapper;
import com.kuta.data.mysql.pojo.Department;
import com.kuta.data.mysql.pojo.DepartmentExample;
import com.kuta.data.mysql.pojo.DepartmentExample.Criteria;

public class DepartmentBiz extends KutaMapBiz<Department, Integer> {

	public DepartmentBiz() {
		super("Department_%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(DepartmentMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(Department t) {
		// TODO Auto-generated method stub
		return t.getDid();
	}

	@Override
	public Department get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(DepartmentMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, Department entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(DepartmentMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, Department entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(DepartmentMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<Department> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public PageWrapper<Department> search(SqlSession session, String key, int oid, int parentId, int pageNum, int pageSize){
		DepartmentExample example = new DepartmentExample();
		Criteria criteria = example.createCriteria();
		if(oid>0) {
			criteria.andOidEqualTo(oid);
		}
		if(parentId > 0) {
			criteria.andParentDidEqualTo(parentId);
		}
		if(key!=null) {
			criteria.andNameLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
			Criteria criteria2 = example.createCriteria();
			if(oid>0) {
				criteria2.andOidEqualTo(oid);
			}
			if(parentId > 0) {
				criteria2.andParentDidEqualTo(parentId);
			}
			criteria2.andAliasLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
			example.or(criteria2);
		}
		PageHelper.startPage(pageNum,pageSize);
		List<Department> list = session.getMapper(DepartmentMapper.class).selectByExample(example);
		return new PageWrapper<Department>(list);
	}
}
