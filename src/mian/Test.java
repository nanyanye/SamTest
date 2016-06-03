package mian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import utils.IOUtil;
import utils.IOUtilImpl;
import utils.NetworkUtil;
import utils.NetworkUtilImpl;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	/**
	 * @param args
	 *            用 yml 配置三个 restful API 地址(自己网上找), 程序读取 yml, 每个 api 开启一个线程调用
	 *            restful API 取 JSON response, 存储到本地文件, 然后再读取此文件, 对每一级
	 *            array/hash 进行循环并打印内容, 对 array, 给定一个关键字要求查询 index, 对 hash, 给定一个
	 *            value 关键字查询出所有满足条件的 key
	 */
	public static void main(String[] args) {
		//System.out.println((Test.class.getResource("") + "").substring(6));
		getData();
	}

	public static void getData() {
		// 网络请求
		NetworkUtil networkUtil = new NetworkUtilImpl();
		// IO工具类
		IOUtil ioUtil = new IOUtilImpl();
		String yamlMsg = "";
		try {
			try {
				yamlMsg = ioUtil.yamlLoadToString("conf/testYaml.yaml");
			} catch (FileNotFoundException e) {
				System.err.println("File no Found Exception");
			}
		} catch (IOException e) {
			System.err.println("I/O Exception");
		}
		List<String> list = new ArrayList<String>(Arrays.asList(yamlMsg
				.split("url:")));
		list.stream()
				.filter(url -> url.length() > 1)
				.forEach(
						(u) -> {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								System.err
										.println("Thread Interrupted Exception!");
							}
							new Thread(
									() -> {
										String fileName = System
												.currentTimeMillis() + ".txt";
										String classPath = (Test.class
												.getResource("") + "")
												.substring(6);
										try {
											ByteStreams.copy(networkUtil
													.getInputStreamByUrl(u),
													new FileOutputStream(
															classPath
																	+ fileName));
										} catch (FileNotFoundException e) {
											System.err
													.println("File no Found Exception");
										} catch (IOException e) {
											System.err.println("I/O Exception");
										}
										JSONObject object = null;
										try {
											object = ioUtil
													.fileToJsonObject(classPath
															+ fileName);
										} catch (IOException e) {
											System.err.println("I/O Exception");
										}
										decodeJSONObject(object);
									}).start();
						});
	}

	// 循环遍历json所有信息
	@SuppressWarnings({ "unchecked", "unused" })
	public static void decodeJSONObject(JSONObject json) {
		Iterator<String> keys = json.keys();
		JSONObject jsonObj = null;
		Object o;
		String key;
		while (keys.hasNext()) {
			key = keys.next();
			// 一个个key去重新拿对象
			o = json.get(key);
			// 判断是否为JSONObject
			if (o instanceof JSONObject) {
				// 是JSONObject 回调
				decodeJSONObject((JSONObject) o);
				// 判断是否为JSONArray
			} else if (o instanceof JSONArray) {
				// o为JSONArray
				JSONArray os = (JSONArray) o;
				os.stream().forEach((object) -> {
					decodeJSONObject((JSONObject) object);
				});
				// 以上都不是就是value了
			} else {
				System.out.println("key:" + key + "---->" + "value:" + o);
			}
		}
	}
}
