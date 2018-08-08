package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;

public class HttpClientUtil {
	/**
	 * @Description TODO(发送post请求)
	 */
	public static String sendByPost(List<NameValuePair> formParams,String url){
		HttpUriRequest request=request_body(formParams,url);
		HttpResponse response=senRequest(request);
		return getResponseStr(response,request);
	}
	
	public static String sendByPost(String param,String url){
		HttpUriRequest request=request_by_content(param,url);
		HttpResponse response=senRequest(request);
		return getResponseStr(response,request);
	}
	
	/**
	 * @Description TODO(发送get请求)
	 */
	public static String sendByGet(List<NameValuePair> formParams,String url){
		HttpUriRequest request=request_line(formParams,url);
		HttpResponse response=senRequest(request);
		return getResponseStr(response,request);
	}
	
	public static String sendByGet(String url){
		HttpUriRequest request=request_line(null,url);
		HttpResponse response=senRequest(request);
		return getResponseStr(response,request);
	}
	
	/**
	 * @Description TODO(上传文件)
	 */
	 public static HttpResponse uploadFile(String url,List<File> files){
		 HttpUriRequest request=uploadFileRequst(url,files);
		 HttpResponse response=senRequest(request);
		 request.abort();
		 return response;
	 }
	
	 public static HttpUriRequest uploadFileRequst(String url,List<File> files){
		try {
			//以浏览器兼容模式运行，防止文件名乱码。  
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
/*			entityBuilder.addPart("param1", new StringBody("中国", Charset.forName("UTF-8")));
			entityBuilder.addPart("param2", new StringBody("value2", Charset.forName("UTF-8")));*/
			int i=0;
			for (File file : files) {
				entityBuilder.addPart("param"+(i++), new FileBody(file));
			}
			HttpEntity entity=entityBuilder.setCharset(CharsetUtils.get("UTF-8")).build();
			HttpPost request = new HttpPost(url);
			request.setEntity(entity);
			return request;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	 
	 /**
	  * 文件流直接上传
	  * @param url
	  * @param
	  * @return
	  */
	 public static HttpResponse uploadFileInputStrem(String url,Map<String,InputStreamBody> inputStreamMap){
		 HttpUriRequest request=uploadInputStremRequst(url,inputStreamMap);
		 HttpResponse response=senRequest(request);
		 request.abort();
		 return response;
	 }
	 
	 
	 public static HttpUriRequest uploadInputStremRequst(String url,Map<String,InputStreamBody> inputStreamMap){
		try {
			//以浏览器兼容模式运行，防止文件名乱码。  
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			for (String key:inputStreamMap.keySet()) {
				entityBuilder.addPart(key,inputStreamMap.get(key));
			}
			HttpEntity entity=entityBuilder.setCharset(CharsetUtils.get("UTF-8")).build();
			HttpPost request = new HttpPost(url);
			request.setEntity(entity);
			return request;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//发送请求，并返回响应
	 public static HttpResponse senRequest(HttpUriRequest httpUriRequest){
		try {
			// 核心应用类
		    HttpClient httpClient = HttpClients.createDefault();
			// 发送请求，返回响应
	        HttpResponse response = httpClient.execute(httpUriRequest);
			 // 打印响应信息
	        System.out.println(response.getStatusLine());
	        return response;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	
	//request_body方法发送请求(只能用于POST请求)
	public static HttpUriRequest request_body(List<NameValuePair> formParams,String url){
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
			
			HttpPost  request = new HttpPost(url);
			request.setEntity(entity);
			
	        // 打印请求信息
	        System.out.println(request.getRequestLine());
			return request;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static HttpUriRequest request_by_content(String param,String url){
		try {
			StringEntity entity = new StringEntity(param,"utf-8");
			HttpPost  request = new HttpPost(url);
			request.setEntity(entity);
			request.addHeader("content-type", "application/x-www-form-urlencoded");
	        // 打印请求信息
	        System.out.println(request.getRequestLine());
			return request;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//request-line方法发送请求(支持get和post)--目前主要用于get
	public static HttpUriRequest request_line(List<NameValuePair> params,String url){
		HttpUriRequest request;
		if(params!=null){
			String param = URLEncodedUtils.format(params, "UTF-8");
			request= new HttpGet(url+"?"+param);
		}else{
			request= new HttpGet(url);
		}
        // 打印请求信息
        System.out.println(request.getRequestLine());
        return request;
		
	}

	//获取请求响应的字符串
	public static String getResponseStr(HttpResponse response,HttpUriRequest request){
		 String returnstr=null;
		 try {
	        	if(response!=null){
	        		/*InputStream in=response.getEntity().getContent();
	    			StringBuffer sb=new StringBuffer();
	    			int len=0;
	    			byte[] b=new byte[1024];
	    			while ((len = in.read(b)) != -1)  
	                {  
	                   sb.append(new String(b, 0, len, "utf-8"));  
	                }  
	    			System.out.println(sb.toString());*/
	    			
	        		//获取响应对象
			        HttpEntity httpentity=response.getEntity();
			        /*System.out.println(ContentType.getOrDefault(httpentity).getCharset());*/
			        //解决HttpClient获取中文乱码 ，用String对象进行转码  
		        	// 对返回的html代码
					returnstr= EntityUtils.toString(httpentity,"utf-8");
					 //释放资源
					request.abort();
	        	}
			} catch (UnsupportedCharsetException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 return returnstr;
	}
	
	
	
	//测试方法
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException {
		//文件下载
	/*	DownLoadEntity downLoadEntity=new DownLoadEntity();
		downLoadEntity.setFileName("test.docx");
		downLoadEntity.setHadoopPath("/hadoopPathca407c9746944f31a9ca0bb33d1334bc.docx");
		HttpClientService.downLoadFile(downLoadEntity);*/
		
		
	/*	//文件上传
		List<File> files=new ArrayList<File>();
		files.add(new File("C:\\测试.txt"));
		files.add(new File("C:\\1.txt"));
		HttpClientService.uploadFile(files);*/
		
		//发送请求
	/*	List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("param1", "中国"));
		formParams.add(new BasicNameValuePair("param2", "request_body"));
		String rt=HttpClientUtil.sendByPost(formParams,"http://1t6y344017.iask.in/webprint/test/a.do");
		System.out.println(rt);
		rt=HttpClientUtil.sendByGet(formParams,"http://1t6y344017.iask.in/webprint/test/a.do");
		System.out.println(rt);*/
		

	}
	
	
}
