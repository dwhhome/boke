package com.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @ClassName CommonUtil
 * @author liaozt
 * @date 2013-8-2 下午3:38:40
 * @Description TODO(封装了以前的DictionaryService.typeCodeNameMap方法,增加了排序功能)
 */
public class CommonUtil {
	private static Map<String,String> propMap=new ConcurrentHashMap<String, String>();
	
	/**
	 * @param url
	 * @return
	 * @throws IOException 
	 * @Description TODO(获取properties)
	 */
	public static String getValueByLocalProperties(String url,String key){
		String rt=null;
		InputStream in=null;
		try {
			if(propMap.get(key)!=null){
				return propMap.get(key);
			}
			in=CommonUtil.class.getClassLoader().getResourceAsStream(url);
			Properties pro=new Properties();
			pro.load(in);
			rt=(String) pro.get(key);
			propMap.put(key,rt);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(url+"中"+key+"获取配置失败!");
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rt;
	}
	
	
	/**
	 * 中文转码
	 * @param obj
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static Object changeEncode(Object obj) {
		if (obj.getClass().getSimpleName().equals("String")) {
			try {
				obj = new String(((String) obj).getBytes("iso-8859-1"), "utf-8");
				return (String) obj;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().getSimpleName().equals("String")) {
				try {
					String methodName = field.getName().substring(0, 1)
							.toUpperCase()
							+ field.getName().substring(1);
					String value = (String) obj.getClass()
							.getMethod("get" + methodName).invoke(obj, null);
					if (value != null && !value.equals("")) {
						obj.getClass()
								.getMethod("set" + methodName, String.class)
								.invoke(obj,
										new String(
												value.getBytes("iso-8859-1"),
												"utf-8"));
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		return obj;
	}
	
	
	/**
	 * 将list转换为set
	 * @param list
	 * @return Set
	 */
	public  static <T> Set<T> changeList2Set(List<T> list){		
		Set<T> set = new HashSet<T>();
		if(list==null)  return set;
		for(T t : list){
			set.add(t);
		}
		return set;
	}
	
	/**
	 * @return
	 * @Description TODO(将set转换为list)
	 */
	public  static <T> List<T> changeSet2List(Set<T> set){	
		List<T> list = new ArrayList<T>();
		if(null==set){
			return list;
		}
		Iterator<T> itor=set.iterator();
		while (itor.hasNext()) {
			list.add(itor.next());
		}
		return list;
	}
	
	
	/**
	 * @param path
	 * @Description TODO(创建目录)
	 */
	public static void dirIsCreate(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}
	
	
	/**
	 * 对象的深度复制
	 * 复制的对象必须是实现了Serializable接口的
	 */
	public static <T extends Serializable> T copyObj(T t) throws IOException, ClassNotFoundException{
		//将对象序列化
		ByteArrayOutputStream bo=new ByteArrayOutputStream();
		ObjectOutputStream oo=new ObjectOutputStream(bo);
		oo.writeObject(t);
		//对象反序列化
		ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi=new ObjectInputStream(bi);
		return (T)oi.readObject();
	}

	
	public static boolean isEmpty(String value){
		if(null == value || "".equals(value) || "(NULL)".equals(value)) return true ;
		return false ;
	}
	
	public static boolean isEmpty(Object obj){
		if(obj == null) return true ;
		return false ;
	}
	public static boolean isEmpty(File obj){
		if(obj == null) return true ;
		return false ;
	}
	
	public static boolean isEmpty(Collection<?> o){
		if(o==null || o.isEmpty()) return true ;
		return false ;
	}
	
	public static boolean isEmpty(Map<?,?> o){
		if(o==null || o.isEmpty()) return true ;
		return false ;
	}
	
	public static String upperFirstWord(String word){
		return word.substring(0,1).toUpperCase()+word.substring(1);
	}
	
	public static List<Field> collect(Class<?> type) {
    	List<Field> rst = new ArrayList();
    	Class<?> t = type;
    	do {
    		rst.addAll(java.util.Arrays.asList(t.getDeclaredFields()));
    		t = t.getSuperclass();
    	} while (t != Object.class);
    		return rst;
    }
	
	
	public static String getWebBasePath(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName();
		if(request.getServerPort()!=80){
			basePath+=":"+request.getServerPort();
		}
		basePath+=path+"/";
		return basePath;
	}
	
	
	public static String getCode(int length,int num){
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i <length-(num+"").length(); i++) {
			buffer.append("0");
		}
		buffer.append(num);
		return buffer.toString();
	}
	
	
}