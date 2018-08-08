package com.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class GsonUtils {
	private  static Gson gson=new Gson();
	private  static JsonParser parser=new JsonParser();
	public static Gson getGson() {
		return gson;
	}
	public static JsonParser getParser() {
		return parser;
	}
	
	
}
