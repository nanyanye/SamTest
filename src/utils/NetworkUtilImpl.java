package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtilImpl implements NetworkUtil {

	@Override
	public InputStream getInputStreamByUrl(String url) throws MalformedURLException, IOException {
		return new URL(url).openStream();
	}

}
