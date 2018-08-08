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
@Transactional(readOnly=true)
public class SystemRepositoryQuery {
	@Autowired
	protected SessionFactory sessionFactory;

	/** 取得当前Session */
	protected Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	
	/**
	 * 立刻获取数据对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(Class clazz,Serializable id) {
		return (T)getSession().get(clazz, id);
	}
	
	/**
	 * 延时加载
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T load(Class clazz,Serializable id) {
		return (T)getSession().load(clazz, id);
	}
	
	/**
	 * 查询Class表所有数据
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findAll(Class clazz) {
		StringBuffer buf=new StringBuffer();
		buf.append("select t from ");
		buf.append(clazz.getSimpleName()+" t ");
		Query query = getSession().createQuery(buf.toString());
		return  query.list();
	}
	
	/**
	 * 通过hql查询数据（集合）
	 * @param hql
	 * @return
	 */
	public <T> List<T> queryByHql(String hql) {
		return queryByHql(hql,new HashMap());
	}
	
	public <T> List<T> queryByHql(String hql,Map<?,?> map) {
		Query query = getSession().createQuery(hql);
		query.setProperties(map);
		return  query.list();
	}
	
	/**
	 * 通过hql查询数据(单个)
	 * @param hql
	 * @return
	 */
	public <T> T queryUniqueByHql(String hql) {
		return  queryUniqueByHql(hql,new HashMap());
	}

	public <T> T queryUniqueByHql(String hql,Map<?,?> map) {
		Query query = getSession().createQuery(hql);
		query.setProperties(map);
		return  (T)query.uniqueResult();
	}

	/**
	 * 通过hql查询类似select id as id,name as name from A
	 * 返回Map
	 */
	public List<Map> queryMapWithHql(String hql) {
		return queryMapWithSql(hql,new HashMap());
	}
	
	public  List<Map> queryMapWithHql(String hql,Map<?,?> map) {
		Query query = getSession().createQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setProperties(map);
		return query.list();
	}
	
	/**
	 * 通过sql查询数据（集合）
	 * @param sql
	 * @return
	 */
	public <T> List<T> queryBySql(String sql) {
		return queryBySql(sql,new HashMap());
	}
	
	public <T> List<T> queryBySql(String sql,Map<?,?> map) {
		Query query = getSession().createSQLQuery(sql);
		query.setProperties(map);
		return  query.list();
	}
	
	/**
	 * 通过sql查询类似select id ,name  from t_a
	 * 返回Map
	 */
	public List<Map> queryMapWithSql(String sql) {
		return queryMapWithSql(sql,new HashMap());
	}
	
	public  List<Map> queryMapWithSql(String sql,Map<?,?> map) {
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setProperties(map);
		return query.list();
	}
	
	/**
	 * 通过sql查询数据(单个)
	 * @param sql
	 * @return
	 */
	public <T> T queryUniqueBySql(String sql) {
		return  queryUniqueBySql(sql,new HashMap());
	}

	public <T> T queryUniqueBySql(String sql,Map<?,?> map) {
		Query query = getSession().createSQLQuery(sql);
		query.setProperties(map);
		return  (T)query.uniqueResult();
	}
	
	
	
	//----------------------------分页查询---------------------------------
	public <T> PageModel<T> queryByPage(String hql,PageModel<T> pageModel){
		return queryByPage(hql,new HashMap(), pageModel);
	}
	
	public <T> PageModel<T> queryByPage(String hql,Map<?,?> map,PageModel<T> pageModel){
		return queryByPage(hql, map, pageModel,false);
	}
	
	/**
	 * 
	 * @param hql
	 * @param map
	 * @param pageModel
	 * @param autoFix 当页数大于最大页数时，自动修正为最后一页
	 * @return
	 */
	public <T> PageModel<T> queryByPage(String hql,Map<?,?> map,PageModel<T> pageModel,boolean autoFix){
		Query query;
		if(pageModel.getRecordCount()<0){
			StringBuffer buf=new StringBuffer();
			buf.append("select count(*)  ");
			buf.append(hql.substring(hql.indexOf("from")));
			query = getSession().createQuery(buf.toString());
			query.setProperties(map);
			pageModel.setRecordCount((Long)query.uniqueResult());
		}
		if(autoFix){//自动修正页数
			if(pageModel.getPageNo()>pageModel.getPageCount()){
				pageModel.setPageNo(pageModel.getPageCount());
			}
		}
		query=getSession().createQuery(hql);
		query.setProperties(map);
		query.setFirstResult((pageModel.getPageNo()-1)*pageModel.getPageSize());
		query.setMaxResults(pageModel.getPageSize());
		pageModel.setDatas(query.list());
		return pageModel;
	}
	//--------------------------------------------------------------------
	
	
	
}
