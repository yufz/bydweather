package com.yufz.bydweather.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
	
	public String date;	//预报日期
	
	@SerializedName("cond_txt_d")
	public String info;	//天气信息
	
	@SerializedName("tmp_max")
	public String tmpMax;	//最高温度
	
	@SerializedName("tmp_min")
	public String tmpMin;	//最低温度

}
