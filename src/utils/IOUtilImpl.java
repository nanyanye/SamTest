package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import com.google.common.io.Files;

import net.sf.json.JSONObject;

public class IOUtilImpl implements IOUtil {
	@Override
	public String yamlLoadToString(String filePath)	throws FileNotFoundException, IOException {
		FileInputStream fs = new FileInputStream(new File(filePath));
		Object objYaml = new Yaml().load(fs);
			fs.close();
		return objYaml.toString();
	}
	@Override
	public List<String> yamlLoadToList(String filePath) throws FileNotFoundException,IOException {
		List<String> list = new ArrayList<String>();
		FileInputStream fs = new FileInputStream(new File(filePath));
		Object objYaml = new Yaml().load(fs);
			fs.close();
		return Arrays.asList(objYaml.toString().split("url:"));
	}

	@Override
	public JSONObject fileToJsonObject(String filePath) throws IOException {
		return JSONObject.fromObject(Files.readFirstLine(new File(filePath), StandardCharsets.UTF_8));
	}

	@Override
	public Object yamlLoad(String filePath) throws FileNotFoundException ,IOException {
		FileInputStream fileInputStream =new FileInputStream(new File(filePath)) ;
		Object object = new Yaml().load(fileInputStream);
		fileInputStream.close();
		return object;
	}

	
}
