package com.yufz.bydweather;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.yufz.bydweather.gson.Forecast;
import com.yufz.bydweather.gson.Weather;
import com.yufz.bydweather.util.HttpUtil;
import com.yufz.bydweather.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {

	private ScrollView weatherLayout;

	private TextView titleCity;

	private TextView titleUpdateTime;

	private TextView degreeText;

	private TextView weatherInfoText;

	private LinearLayout forecastLayout;

	private TextView windSpeedText;

	private TextView humidityText;

	private TextView comfortText;

	private TextView carWashText;

	private TextView sportText;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		// 初始化各控件
		weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
		titleCity = (TextView) findViewById(R.id.title_city);
		titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
		degreeText = (TextView) findViewById(R.id.degree_text);
		weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
		forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
		windSpeedText = (TextView) findViewById(R.id.windspeed_text);
		humidityText = (TextView) findViewById(R.id.humidity_text);
		comfortText = (TextView) findViewById(R.id.comfort_text);
		carWashText = (TextView) findViewById(R.id.car_wash_text);
		sportText = (TextView) findViewById(R.id.sport_text);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherString = prefs.getString("weather", null);
		if (weatherString != null) {
			// 有缓存时直接解析天气数据
			Weather weather = Utility.handleWeatherResponse(weatherString);
			showWeatherInfo(weather);
		} else {
			// 无缓存时去服务器查询天气
			String mWeatherId = getIntent().getStringExtra("weather_id");
			weatherLayout.setVisibility(View.INVISIBLE);
			requestWeather(mWeatherId);
		}
	}

	/**
	 * 根据天气id请求城市天气信息
	 */
	public void requestWeather(final String weatherId) {
		String weatherUrl = "https://free-api.heweather.net/s6/weather?location=" + weatherId
				+ "&key=be0ea5a5559141bbaf7a005ae4ce88d9";
		HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				final String responseText = response.body().string();
				final Weather weather = Utility.handleWeatherResponse(responseText);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (weather != null && "ok".equals(weather.status)) {
							SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
									WeatherActivity.this).edit();
							editor.putString("weather", responseText);
							editor.apply();
							showWeatherInfo(weather);
						} else {
							Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}

			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	/**
	 * 处理并展示Weather实体类中的数据。
	 */
	private void showWeatherInfo(Weather weather) {
		String cityName = weather.basic.cityName;
		String updateTime = weather.update.updateTime.split(" ")[1];
		String degree = weather.now.temperature + "℃";
		String weatherInfo = weather.now.info;
		String windSpeedInfo = weather.now.windSpeed;
		String humidityInfo = weather.now.humidity;
		titleCity.setText(cityName);
		titleUpdateTime.setText(updateTime);
		degreeText.setText(degree);
		weatherInfoText.setText(weatherInfo);
		windSpeedText.setText(windSpeedInfo);
		humidityText.setText(humidityInfo);
		forecastLayout.removeAllViews();
		for (Forecast forecast : weather.forecastList) {
			View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
			TextView dateText = (TextView) view.findViewById(R.id.date_text);
			TextView infoText = (TextView) view.findViewById(R.id.info_text);
			TextView maxText = (TextView) view.findViewById(R.id.max_text);
			TextView minText = (TextView) view.findViewById(R.id.min_text);
			dateText.setText(forecast.date);
			infoText.setText(forecast.info);
			maxText.setText(forecast.tmpMax);
			minText.setText(forecast.tmpMin);
			forecastLayout.addView(view);
		}
//		if (weather.aqi != null) {
//			aqiText.setText(weather.aqi.city.aqi);
//			pm25Text.setText(weather.aqi.city.pm25);
//		}
		String comfort = "舒适度：" + weather.suggestionList.get(0).suggestionTxt;
		String carWash = "洗车指数：" + weather.suggestionList.get(6).suggestionTxt;
		String sport = "运行建议：" + weather.suggestionList.get(3).suggestionTxt;
		comfortText.setText(comfort);
		carWashText.setText(carWash);
		sportText.setText(sport);
		weatherLayout.setVisibility(View.VISIBLE);
//		Intent intent = new Intent(this, AutoUpdateService.class);
//		startService(intent);
	}

}
