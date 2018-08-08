package com.base.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utils.CommonUtil;
import com.base.annotation.FillEntityById;
import com.base.annotation.FillFieldById;
import com.base.repository.SystemRepository;

@Service
public class EntityUtilsService {
	@Autowired
	private SystemRepository systemRepository;
	
	public <T> T fillEntity(T t){
		Class clazz=t.getClass();
		List<Field> fields=CommonUtil.collect(clazz);
		for (Field field : fields) {
			if(field.isAnnotationPresent(FillEntityById.class)){
				try {
					FillEntityById ann=field.getAnnotation(FillEntityById.class);
					String entityId=(String) clazz.getMethod("get"+CommonUtil.upperFirstWord(ann.entityId())).invoke(t);
					Object obj=systemRepository.get(ann.entityClass(),entityId);
					clazz.getMethod("set"+CommonUtil.upperFirstWord(field.getName()),ann.entityClass()).invoke(t,obj);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}
	
	
	public <T> T fillEntityField(T t){
		Class clazz=t.getClass();
		List<Field> fields=CommonUtil.collect(clazz);
		for (Field field : fields) {
			if(field.isAnnotationPresent(FillFieldById.class)){
				FillFieldById ann=field.getAnnotation(FillFieldById.class);
				try {
					String entityId=(String) clazz.getMethod("get"+CommonUtil.upperFirstWord(ann.entityId())).invoke(t);
					Object obj=systemRepository.queryUniqueByHql("select "+ann.field()+" from "+ann.entityClass().getSimpleName()+" where "+getPrimaryKey(ann.entityClass()).getName()+"='"+entityId+"'");
					clazz.getMethod("set"+CommonUtil.upperFirstWord(field.getName()),field.getType()).invoke(t,obj);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	public <T> T fillAll(T t){
		fillEntity(t);
		fillEntityField(t);
		return t;
	}
	
	public <T> List<T> fillAll(List<T> ts){
		List<T> nts = new ArrayList<T>();
		for (T t : ts) {
			fillEntity(t);
			fillEntityField(t);
			nts.add(t);
		}
		return ts;
	}
	
	public Field getPrimaryKey(Class clazz){
		List<Field> fields=CommonUtil.collect(clazz);
		for (Field field : fields) {
			if(field.isAnnotationPresent(Id.class)){
				return field;
			}
		}
		return null;
	}
	
}
