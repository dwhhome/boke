package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Tools {
	private final static String[] codeArr={"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	/**
	 * 随机生成4位数
	 * @return
	 */
	public static String getRandom(){
		 String vcode = "";
	        for (int i = 0; i < 4; i++) {
	            vcode = vcode + (int)(Math.random() * 10);
	        }
	        return vcode;
	}
	
	/**
	 * 订单编码
	 * @return
	 */
	public static String getOrdeCode(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Date d = new Date();
		String orderCode = sdf.format(d)+String.valueOf(d.getTime())+getRandom();
		return orderCode;
	}
	
	
	public static String getOrdeCode(String code){
		return code+getOrdeCode();
	}
	
	
	/**
	 * 获取随机码
	 * @return
	 */
	public static String getRandomCode(int len){
		StringBuffer buf=new StringBuffer();
		for (int i = 0; i < len; i++) {
			buf.append(codeArr[(int)(Math.random() * 35)]);
		}
		return buf.toString();
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	
}
