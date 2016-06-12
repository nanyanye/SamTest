package utils;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 解码json
 * 循环遍历json所有信息
 * */
public class DecodeJSONObImpl implements DecodeJSONOb{
		@SuppressWarnings({ "unchecked", "unused" })
		public void decodeJSONObject(JSONObject json) {
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
