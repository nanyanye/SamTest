package dataaccess;

import java.util.function.Consumer;

import entity.UrlInfo;

public interface Network {
	public void getDate(UrlInfo urlInfo,Consumer<String> consumer);
}
