package com.base.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.base.page.PageModel;

@Repository
@Transactional
public class SystemRepository extends SystemRepositoryQuery{
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public <T> T save(T entity){
		getSession().save(entity);
		return entity;
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public <T> T saveOrUpdate(T entity){
		getSession().saveOrUpdate(entity);
		getSession().flush();
		return entity;
	}
	
	/**
	 * 保存并熟刷新缓存
	 * @param entity
	 * @return
	 */
	public <T> T saveAndFlush(T entity){
		save(entity);
		getSession().flush();
		return entity;
	}
	
	
	/**
	 * 删除
	 * @param entity
	 * @return
	 */
	public <T> void delete(T entity) {
		getSession().delete(entity);
	}
	
	
	/**
	 * 删除根据Class和id删除
	 * @param id
	 * @return
	 */
	public <T> void delete(Class clazz,Serializable id) {
		getSession().delete(getSession().load(clazz, id));
	}
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public <T> T update(T entity) {
		getSession().update(entity);
		getSession().merge(entity);
		return entity;
	}
	
	public int updateByHql(String hql) {
		return updateByHql(hql,new HashMap());
	}
	
	public int updateByHql(String hql,Map<?,?> map) {
		Query query = getSession().createQuery(hql);
		query.setProperties(map);
		return query.executeUpdate();
	}
	
	
	public int updateBySql(String sql) {
		return updateBySql(sql,new HashMap());
	}
	
	public int updateBySql(String sql,Map<?,?> map) {
		Query query = getSession().createSQLQuery(sql);
		query.setProperties(map);
		return  query.executeUpdate();
	}
}
