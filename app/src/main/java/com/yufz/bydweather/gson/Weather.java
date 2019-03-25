package com.yufz.bydweather.gson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Weather {
	
	public String status;
	
	public Basic basic;
	
	public Update update;
	
	public Now now;
	
	@SerializedName("lifestyle")
	public List<Suggestion> suggestionList;
	
	@SerializedName("daily_forecast")
	public List<Forecast> forecastList;

}
