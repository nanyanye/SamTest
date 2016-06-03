package utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.json.JSONObject;

public interface IOUtil {
	String yamlLoadToString(String filePath) throws FileNotFoundException;
	Object yamlLoad(String filePath) throws FileNotFoundException;
	JSONObject fileToJsonObject(String filePath) throws IOException;
}
