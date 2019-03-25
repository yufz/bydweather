package com.yufz.bydweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
	
	public static void sendOkHttpRequest(String urlAddress, okhttp3.Callback callback) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.url(urlAddress)
			.build();
		client.newCall(request).enqueue(callback);
	}

}
