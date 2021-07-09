package com.kuta.data.mysql.biz;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kuta.base.database.KutaMapBiz;
import com.kuta.base.util.PageWrapper;
import com.kuta.data.mysql.dao.TaskMapper;
import com.kuta.data.mysql.pojo.Task;
import com.kuta.data.mysql.pojo.TaskExample;

public class TaskBiz  extends KutaMapBiz<Task, Long>{

	public TaskBiz() {
		super("Task_%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int remove(SqlSession session, Long key) {
		// TODO Auto-generated method stub
		return session.getMapper(TaskMapper.class).deleteByPrimaryKey(key);
	}

	@Override
	public Long getKey(Task t) {
		// TODO Auto-generated method stub
		return t.getTid();
	}

	@Override
	public Task get(SqlSession session, Long key) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(TaskMapper.class).selectByPrimaryKey(key);
	}

	@Override
	public int insert(SqlSession session, Task entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(TaskMapper.class).insert(entity);
	}

	@Override
	public int update(SqlSession session, Task entity) throws Exception {
		// TODO Auto-generated method stub
		return session.getMapper(TaskMapper.class).updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<Task> select(SqlSession session, JSONObject param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public PageWrapper<Task> search(SqlSession session,
			Long oid, 
			Byte timeType,
			Date created,
			Boolean isCycle,
			String status,
			int pageNum,
			int pageSize){
		TaskExample example = new TaskExample();
		example.createCriteria()
		.andTimeTypeEqualTo(timeType!=null, timeType)
		.andCreatedGreaterThanOrEqualTo(created!=null, created)
		.andIsCycleEqualTo(isCycle != null, isCycle)
		.andStatusEqualTo(status!=null , status);
		PageHelper.startPage(pageNum,pageSize);
		List<Task> list = session.getMapper(TaskMapper.class).selectByExample(example);
		return new PageWrapper<Task>(list);
	}
	
	
}
