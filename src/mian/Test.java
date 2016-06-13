package mian;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import utils.IOUtil;
import utils.IOUtilImpl;
import utils.Mythread;

public class Test {
	/**
	 * @param args
	 *            用 yml 配置三个 restful API 地址(自己网上找), 程序读取 yml, 每个 api 开启一个线程调用
	 *            restful API 取 JSON response, 存储到本地文件, 然后再读取此文件, 对每一级
	 *            array/hash 进行循环并打印内容, 对 array, 给定一个关键字要求查询 index, 对 hash, 给定一个
	 *            value 关键字查询出所有满足条件的 key
	 */
	public static void main(String[] args) {
		Test test = new Test();
		test.getData();
	}

	public void getData() {
		IOUtil ioUtil = new IOUtilImpl();
		List<String> list = null;
		try {
			list = ioUtil.yamlLoadToList("conf/testYaml.yaml");
		} catch (FileNotFoundException e) {
			System.err.println("File no Found Exception");
		} catch (IOException e) {
			System.err.println("I/O Exception");
		}
		list.stream().filter(url -> url.length() > 1).forEach((u) -> {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				System.err.println("Thread InterruptedException");
			}
			new Mythread(u).start();
		});
	}

}
