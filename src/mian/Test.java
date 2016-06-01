package mian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

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
	public static void main(String[] args) {
		try {
			Object obj = new Yaml().load(new FileInputStream(new File("conf/testYaml.yaml")));
			List<String> list = new ArrayList<String>(Arrays.asList(obj.toString().split("url:")));
			List<String> fileName = Arrays.asList("1", "2", "3");
			list.stream().filter(url ->url.length()>1).forEach((u) -> {
						InputStream in = null;
						try {
							in = new URL(u).openStream();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
							try {
							FileOutputStream fos = new FileOutputStream("E://" +System.currentTimeMillis()+ ".txt");
							byte[] b = new byte[1024];
							while ((in.read(b)) != -1) {
								fos.write(b);
								System.out.println(b);
							}
							in.close();
							fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
