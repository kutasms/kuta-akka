package com.kuta.data.mysql.biz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.cache.JedisClient;
import com.kuta.base.database.KutaExpireMapBiz;
import com.kuta.base.database.KutaSQLUtil;
import com.kuta.base.entity.KutaConstants;
import com.kuta.base.util.KutaBeanUtil;
import com.kuta.base.util.KutaUtil;
import com.kuta.base.util.PageWrapper;
import com.kuta.base.util.Status;
import com.kuta.data.mysql.dao.OrganizationMapper;
import com.kuta.data.mysql.pojo.Organization;
import com.kuta.data.mysql.pojo.OrganizationExample;
import com.kuta.data.mysql.pojo.OrganizationExample.Criteria;

public class OrganizationBiz extends KutaExpireMapBiz<Organization, Integer> {

	public OrganizationBiz() {
		super("Organization_%s", 30 *60);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Integer key) {
		// TODO Auto-generated method stub
		return session.getMapper(OrganizationMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Integer getKey(Organization t) {
		// TODO Auto-generated method stub
		return t.getOid();
	}

	@Override
	public Organization get(SqlSession session, Integer key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(OrganizationMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, Organization entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(OrganizationMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, Organization entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(OrganizationMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<Organization> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public  Organization getMaster(JedisClient jedis) {
		String cacheKey = "organization_master";
		if(jedis.exists(cacheKey)) {
			Map<String, String> map = jedis.hgetAll(cacheKey);
			return KutaBeanUtil.map2Bean(map, Organization.class);
		} else {
			OrganizationExample example = new OrganizationExample();
			example.createCriteria().andParentOidEqualTo(0);
			try {
				return KutaSQLUtil.exec(session->{
					List<Organization> list = session.getMapper(OrganizationMapper.class).selectByExample(example);
					if(KutaUtil.isEmptyColl(list)) {
						return null;
					} else {
						Map<String, String> map = KutaBeanUtil.bean2Map(list.get(0));
						jedis.hset(cacheKey, map);
						return list.get(0);
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				logger.error("获取Master失败", e);
				return null;
			}
		}
	}
	
	public static Organization getMaster(SqlSession session, JedisClient jedis) {
		String cacheKey = "organization_master";
		if(jedis.exists(cacheKey)) {
			Map<String, String> map = jedis.hgetAll(cacheKey);
			return KutaBeanUtil.map2Bean(map, Organization.class);
		} else {
			OrganizationExample example = new OrganizationExample();
			example.createCriteria().andParentOidEqualTo(0);
			List<Organization> list = session.getMapper(OrganizationMapper.class).selectByExample(example);
			if(KutaUtil.isEmptyColl(list)) {
				return null;
			} else {
				Map<String, String> map = KutaBeanUtil.bean2Map(list.get(0));
				jedis.hset(cacheKey, map);
				return list.get(0);
			}
		}
	}
	
	public PageWrapper<Organization> search(SqlSession session, String key, int parentId, int pageNum, int pageSize){
		OrganizationExample example = new OrganizationExample();
		Criteria criteria = example.createCriteria();
		if(key != null) {
			criteria.andNameLike(String.format(KutaConstants.SQL_LIKE_TEMPLATE, key));
		}
		if(parentId > 0) {
			criteria.andParentOidEqualTo(parentId);
		}
		criteria.andStatusEqualTo(Status.NORMAL);
		PageHelper.startPage(pageNum, pageSize);
		List<Organization> list = session.getMapper(OrganizationMapper.class).selectByExample(example);
		PageWrapper<Organization> page = new PageWrapper<Organization>(list);
		return page;
	}
	
}
