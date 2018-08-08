package com.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;  

import org.apache.commons.codec.binary.Base64;  

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	public static void main(String[] args){  
        String str = "543";  
        try{  
            byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));  
            //System.out.println("RESULT: " + new String(encodeBase64));
            //System.out.println(getBase64(str));
            System.out.println(getFromBase64("8J+QjiDmlrnnvr0="));
        } catch(UnsupportedEncodingException e){  
            e.printStackTrace();  
        }  
    }  
	
	// 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
	
    
    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static boolean generateImage(String imgStr, String path) {
    	String[] ss=imgStr.split(",");
    	path=path+"."+ss[0].split(";")[0].split("/")[1];
        if(imgStr == null){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try{
            //解密
            byte[] b = decoder.decodeBuffer(ss[1]);
            //处理数据
            for (int i = 0;i<b.length;++i){
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
