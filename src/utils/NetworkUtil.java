package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public interface NetworkUtil {
	InputStream getInputStreamByUrl(String url) throws MalformedURLException, IOException;
}
