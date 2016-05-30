package mian;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.ho.yaml.YamlDecoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import dataaccess.Network;
import dataaccess.NetworkImpl;
import entity.UrlInfo;

public class Test {

	/**
	 * @param args
	 *            用 yml 配置三个 restful API 地址(自己网上找), 程序读取 yml, 每个 api 开启一个线程调用
	 *            restful API 取 JSON response, 存储到本地文件, 然后再读取此文件,
	 * 
	 * 
	 *            对每一级 array/hash 进行循环并打印内容, 对 array, 给定一个关键字要求查询 index, 对 hash,
	 *            给定一个 value 关键字查询出所有满足条件的 key
	 */

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		File dumpFile = new File("conf/testYaml.yaml");
		Network network = new NetworkImpl();

		try {
			YamlDecoder yDecoder = new YamlDecoder(
					new FileInputStream(dumpFile));
			UrlInfo urlInfo;
			for (int i = 0; i < 3; i++) {
				urlInfo = new UrlInfo();
				final HashMap<String, String> map = (HashMap<String, String>) yDecoder
						.readObject();
				urlInfo.setUrlName(map.get("urlName").toString());
				urlInfo.setUrl(map.get("url").toString());
				// 网络请求
				network.getDate(urlInfo, new Consumer<String>() {
					public void accept(String result) {
						String name = map.get("urlName").toString();
						// json保存文件
						saveFile(name, result);
						readFile(name);
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean saveFile(String fileName, String data) {
	//System.out.println(data);
		File file = new File("E://" + fileName + ".txt");
		FileWriter fw;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(data, 0, data.length() - 1);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	static Gson gson = new Gson();

	@SuppressWarnings("rawtypes")
	public static void readFile(String fileName) {
		FileReader fr;
		String data = "";
		try {
			String s = "";
			fr = new FileReader("E://" + fileName + ".txt");
			BufferedReader br = new BufferedReader(fr);
			while ((s = br.readLine()) != null) {
				// System.out.println(s);
				data = s;
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("小可乐充电桩".equals(fileName)) {
			System.out.println("---------充电桩-------------");
			// System.out.println(s);
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray(data);
				// 循环遍历
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = new JSONObject(jsonArray.getString(i));
					System.out.println("名字：" + obj.getString("name") + "---"
							+ "经纬度：" + obj.getDouble("latitude") + ":"
							+ obj.getDouble("longitude"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if("笑话".equals(fileName)) {
			System.out.println("---------笑话---------------");
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(data);

				Iterator iterator = jsonObject.keys();
				String key = null;
				String value = null;
				while (iterator.hasNext()) {
					key = (String) iterator.next();
					if("detail".equals(key)){
						value = jsonObject.getString(key);
						JSONArray jsonArray = new JSONArray(value);
						// 循环遍历
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject obj = new JSONObject(jsonArray.getString(i));
							System.out.println("作者：" + obj.getString("author"));
							System.out.println("内容：" + obj.getString("content"));
						}
					}
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			System.out.println("---------天气-------------");
			try {
				JSONObject jsonObject = new JSONObject(data);
				JSONObject obj = jsonObject.getJSONObject("weatherinfo");
				System.out.println(obj.getString("city"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
}
