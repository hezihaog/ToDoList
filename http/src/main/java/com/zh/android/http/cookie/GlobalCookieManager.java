package com.zh.android.http.cookie;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zh.android.core.io.IORuntimeException;
import com.zh.android.core.util.URLUtil;
import com.zh.android.http.HttpConnection;

/**
 * 全局Cooki管理器，只针对Hutool请求有效
 * 
 * @author Looly
 * @since 4.5.15
 */
public class GlobalCookieManager {

	/** Cookie管理 */
	private static CookieManager cookieManager;
	static {
		cookieManager = new CookieManager(new ThreadLocalCookieStore(), CookiePolicy.ACCEPT_ALL);
	}
	
	/**
	 * 自定义{@link CookieManager}
	 * 
	 * @param customCookieManager 自定义的{@link CookieManager}
	 */
	public static void setCookieManager(CookieManager customCookieManager) {
		cookieManager = customCookieManager;
	}
	
	/**
	 * 获取全局{@link CookieManager}
	 * 
	 * @return {@link CookieManager}
	 */
	public static CookieManager getCookieManager() {
		return cookieManager;
	}

	/**
	 * 将本地存储的Cookie信息附带到Http请求中，不覆盖用户定义好的Cookie
	 * 
	 * @param conn {@link HttpConnection}
	 */
	public static void add(HttpConnection conn) {
		if(null == cookieManager) {
			// 全局Cookie管理器关闭
			return;
		}
		
		Map<String, List<String>> cookieHeader;
		try {
			cookieHeader = cookieManager.get(URLUtil.toURI(conn.getUrl()), new HashMap<String, List<String>>(0));
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		
		// 不覆盖模式回填Cookie头，这样用户定义的Cookie将优先
		conn.header(cookieHeader, false);
	}

	/**
	 * 存储响应的Cookie信息到本地
	 * 
	 * @param conn {@link HttpConnection}
	 */
	public static void store(HttpConnection conn) {
		if(null == cookieManager) {
			// 全局Cookie管理器关闭
			return;
		}
		
		try {
			cookieManager.put(URLUtil.toURI(conn.getUrl()), conn.headers());
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
