package com.qlfsoft.wordman.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {

	/**
	 * 是否有wifi
	 * @param context
	 */
	public static boolean isWifi(Context context)
	{
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if(null == info)
		{
			return false;
		}else
		{
			return info.getTypeName().equals("WIFI");
		}
	}
	
	/**
	 * 是否有网络
	 * @param context
	 * @return
	 */
	public static boolean isNet(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = cm.getActiveNetworkInfo();
		if(null != net)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
	 * 获取网络数据
	 * @param url 网址
	 * @return
	 */
	public static String getHttpString(String url)
	{
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != HttpStatus.SC_OK)
				return null;
			else
			{
				HttpEntity entity = response.getEntity();
				if(null != entity)
				{
					InputStream in = entity.getContent();
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					for(String s = br.readLine(); null != s; s = br.readLine())
					{
						sb.append(s);
					}
					String result = sb.toString();
					return result;
				}
			}
		} catch (Exception e) {
			httpGet.abort();
			e.printStackTrace();
		} 
		return null;
	}
}
