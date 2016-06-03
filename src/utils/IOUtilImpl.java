package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.yaml.snakeyaml.Yaml;

import com.google.common.io.Files;

import net.sf.json.JSONObject;

public class IOUtilImpl implements IOUtil {

	@Override
	public String yamlLoadToString(String filePath) throws FileNotFoundException {
		Object objYaml = new Yaml().load(new FileInputStream(new File(filePath)));
		return objYaml.toString();
	}

	@Override
	public JSONObject fileToJsonObject(String filePath) throws IOException {
		return JSONObject.fromObject(Files.readFirstLine(new File(filePath), StandardCharsets.UTF_8));
	}

	@Override
	public Object yamlLoad(String filePath) throws FileNotFoundException {
		return new Yaml().load(new FileInputStream(new File(filePath)));
	}
}
