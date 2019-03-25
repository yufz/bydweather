package com.yufz.bydweather.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
	
	@SerializedName("tmp")
	public String temperature;	//温度
	
	@SerializedName("cond_txt")
	public String info;	//实况天气状况描述
	
	@SerializedName("wind_spd")
	public String windSpeed;	//风速
	
	@SerializedName("hum")
	public String humidity;	//相对湿度

}
