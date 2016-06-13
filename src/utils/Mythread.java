package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import mian.Test;
import net.sf.json.JSONObject;

import com.google.common.io.ByteStreams;

public class Mythread extends Thread {
	private String path;
	// 网络请求
	private NetworkUtil networkUtil = new NetworkUtilImpl();
	// IO工具类
	private IOUtil ioUtil = new IOUtilImpl();
	//Json解析工具
	private DecodeJSONOb decodeJSONOb =new DecodeJSONObImpl();
	public Mythread(String path) {
		this.path = path;
	}

	public void run() {
		String fileName = System.currentTimeMillis() + ".txt";
		String classPath = (Test.class.getResource("") + "").substring(6);
		
		try {
			ByteStreams.copy(networkUtil.getInputStreamByUrl(path),
					new FileOutputStream(classPath + fileName));
		} catch (FileNotFoundException e) {
			System.err.println("File no Found Exception");
		} catch (IOException e) {
			System.err.println("I/O Exception");
		}
		JSONObject object = null;
		try {
			object = ioUtil.fileToJsonObject(classPath + fileName);
		} catch (IOException e) {
			System.err.println("I/O Exception");
		}
		decodeJSONOb.decodeJSONObject(object);
	}
}
