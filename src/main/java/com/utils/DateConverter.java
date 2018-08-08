package com.utils;

/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class DateConverter implements org.springframework.core.convert.converter.Converter<String, Date>
/*    */ {
/*    */   public DateConverter() {}
/*    */   
/*    */   public Date convert(String source)
/*    */   {
/* 13 */     SimpleDateFormat df = null;
/*    */     try {
/* 15 */       if (source.length() == 10) {
/* 16 */         df = new SimpleDateFormat("yyyy-MM-dd");
/* 17 */         return df.parse(source); }
/* 18 */       if (source.length() == 19) {
/* 19 */         df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 20 */         return df.parse(source); }
/* 21 */       if (source.length() == 21) {
/* 22 */         df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 23 */         return df.parse(source.substring(0, 19));
/*    */       }
/*    */     } catch (ParseException e) {
/* 26 */       e.printStackTrace();
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\eclipse_workspace\workspace\filemanage\WebContent\WEB-INF\lib\platform-bin.jar
 * Qualified Name:     com.guotai.platform.util.DateConverter
 * Java Class Version: 6 (50.0)
 * JD-Core Version:    0.7.0.1
 */