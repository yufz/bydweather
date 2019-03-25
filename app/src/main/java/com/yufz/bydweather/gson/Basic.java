package com.yufz.bydweather.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
	
	@SerializedName("location")
	public String cityName;	//城市名
	
	@SerializedName("cid")
	public String weatherId;	//天气id

}
