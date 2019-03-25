package com.yufz.bydweather.gson;

import com.google.gson.annotations.SerializedName;

public class Suggestion {
	
	@SerializedName("type")
	public String suggestionType;	//生活指数类型
	
	@SerializedName("brf")
	public String suggestionBrf;	//生活指数简介
	
	@SerializedName("txt")
	public String suggestionTxt;	//生活指数详细描述

}
