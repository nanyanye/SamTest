package mian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

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
		try {
			Object objYaml = new Yaml().load(new FileInputStream(new File("conf/testYaml.yaml")));
			List<String> list = new ArrayList<String>(Arrays.asList(objYaml.toString().split("url:")));
			list.stream().filter(url -> url.length() > 1).forEach((u) -> {
				try {
					Thread.sleep(10);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				new Thread(() -> {
					try {
						String fileName = System.currentTimeMillis() + "";
						// guava io
						ByteStreams.copy(new URL(u).openStream(),
								new FileOutputStream("E://test/" + fileName + ".txt"));
						JSONObject object = JSONObject.fromObject(Files.readFirstLine(new File("E://test/" + fileName + ".txt"), StandardCharsets.UTF_8));
						decodeJSONObject(object);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//循环遍历json所有信息
	@SuppressWarnings("unchecked")
	public static void decodeJSONObject(JSONObject json){  
        Iterator<String> keys=json.keys();  
        JSONObject jsonObj=null;  
        Object o;  
        String key;  
        while(keys.hasNext()){  
            key=keys.next();  
            //一个个key去重新拿对象
            o=json.get(key);  
            //判断是否为JSONObject
            if(o instanceof JSONObject){  
            	//是JSONObject 回调
                 decodeJSONObject((JSONObject)o);  
              //判断是否为JSONArray
            }else if(o instanceof JSONArray){ 
            	//o为JSONArray  
            	JSONArray os =(JSONArray)o; 
            	os.stream().forEach((object) ->{
            		decodeJSONObject((JSONObject)object);
            	});
            //以上都不是就是值了
            }  else{
            	System.out.println("key:"+key+"---->"+"value:"+o);
            }
        }  
    }  
}
