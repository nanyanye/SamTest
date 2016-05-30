package dataaccess;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

import entity.UrlInfo;

public class NetworkImpl implements Network {
	
	
	
	public void getDate(final UrlInfo urlInfo,final Consumer<String> consumer){
		 new Thread(new Runnable() {
			public void run() {
				BufferedReader reader = null;
			    String result = null;
			    StringBuffer sbf = new StringBuffer();
			    try {
			        URL url = new URL(urlInfo.getUrl());
			        HttpURLConnection connection = (HttpURLConnection) url
			                .openConnection();
			        connection.setRequestMethod("GET");
			        InputStream is = connection.getInputStream();
			        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			        String strRead = null;
			        while ((strRead = reader.readLine()) != null) {
			            sbf.append(strRead);
			            sbf.append("\r\n");
			        }
			        reader.close();
			        result = sbf.toString();
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			    consumer.accept(result);
			   // return result;
				
			}
		}).start();
	}


}
