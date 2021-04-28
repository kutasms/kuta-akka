package com.kuta.database.mysql.biz;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuta.database.mysql.dao.UserMapper;
import com.kuta.database.mysql.dao.extend.UserMapperExt;
import com.kuta.database.mysql.pojo.User;
import com.kuta.database.mysql.pojo.UserExample;
import com.kuta.database.mysql.pojo.UserExample.Criteria;
import com.kuta.database.mysql.pojo.extend.UserExt;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.util.KutaUtil;
import com.kuta.base.util.PageWrapper;

public class UserBiz extends KutaMapBiz<UserExt, Integer>{

	public UserBiz(String cacheName) {
		super(cacheName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(UserMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(UserExt t) {
		// TODO Auto-generated method stub
		return t.getUid();
	}

	@Override
	public UserExt get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(UserMapperExt.class).selectExtByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, UserExt entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(UserMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, UserExt entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(UserMapper.class).updateByPrimaryKeySelective(entity);
	}
	
	public PageInfo<User> getListWithPageInfo(SqlSession session,String key,int pageNum, int pageSize){
		UserExample example = null;
		if(!KutaUtil.isEmptyString(key)) {
			example = new UserExample();
			Criteria phoneCriteria = example.createCriteria();
			phoneCriteria.andPhoneEqualTo(key);
			Criteria nameCriteria = example.createCriteria();
			nameCriteria.andNameEqualTo(key);
			Criteria accCriteria = example.createCriteria();
			accCriteria.andAccountEqualTo(key);
			
			
			example.or(accCriteria);
			example.or(phoneCriteria);
			example.or(nameCriteria);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<User> list = session.getMapper(UserMapper.class).selectByExample(example);
		PageInfo<User> page = new PageInfo<>(list);
		return page;
	}
	
	public User getByAccount(SqlSession session,String account) {
		UserExample example = new UserExample();
		example.createCriteria().andAccountEqualTo(account);
		List<User> list = session.getMapper(UserMapper.class).selectByExample(example);
		if(KutaUtil.isEmptyColl(list)) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<UserExt> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	public PageWrapper<User> search(
			SqlSession session,
			String key,
			String status,
			Integer did,
			int pageNum,
			int pageSize){
		UserExample example = new UserExample();
		if(key!=null) {
			Criteria criteria1 = example.or().andStatusEqualTo(status).andAccountLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
			if(did > 0) {
				criteria1.andDidEqualTo(did);
			}
			Criteria criteria2 = example.or().andStatusEqualTo(status).andNameLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
			if(did > 0) {
				criteria2.andDidEqualTo(did);
			}
			Criteria criteria3 = example.or().andStatusEqualTo(status).andPhoneLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
			if(did > 0) {
				criteria3.andDidEqualTo(did);
			}
		} else {
			Criteria criteria = example.createCriteria().andStatusEqualTo(status);
			if(did > 0) {
				criteria.andDidEqualTo(did);
			}
			PageHelper.startPage(pageNum, pageSize);
			List<User> list = session.getMapper(UserMapper.class).selectByExample(example);
			return new PageWrapper<User>(list);
		}
		
		
		return null;
	}
}
