package com.common.utils.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author SilverHu
 *
 */
public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	/** 连接池 */
	private static final PoolingHttpClientConnectionManager connectionManager;

	/** 请求链接超时配置 */
	private static final RequestConfig requestConfig;

	/** 报文读取间隔超时时间，单位：毫秒 */
	public static final int DEFAULT_READ_TIMEOUT = 5000;

	/** 连接超时时间，（三次握手完成时间），单位：毫秒 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5000;

	/** 连接池获取链接超时时间 ，单位：毫秒 */
	public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 5000;

	/** 最大连接数 */
	private static final int MAX_TOTAL = 64;

	/** 最大并发连接数 */
	private static final int MAX_PER_ROUTE = 32;

	public static final String DEFAULT_CHARSET = "utf-8";

	private static final HttpClientBuilder httpBuilder;

	private static final CloseableHttpClient httpClient;

	/**
	 * 连接池配置
	 */
	static {
		requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_READ_TIMEOUT)
				.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)
				.build();
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory()).build();
		connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connectionManager.setMaxTotal(MAX_TOTAL);
		connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		httpBuilder = HttpClientBuilder.create();
		httpBuilder.setDefaultRequestConfig(requestConfig);
		httpBuilder.setConnectionManager(connectionManager);
		httpClient = httpBuilder.build();
	}

	private HttpClientUtil() {

	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param headers
	 * @return
	 */
	public static String get(String url, Map<String, String> headers) {
		HttpGet request = new HttpGet(url);
		try {
			setHeader(request, headers);// 设置请求头
			return executeHttpRequest(httpClient, request);
		} finally {
			request.releaseConnection();
		}
	}

	/**
	 * post请求：raw
	 * 
	 * @param url
	 * @param body
	 *            json格式
	 * @param headers
	 * @return
	 */
	public static String postBody(String url, String body, Map<String, String> headers) {
		HttpPost request = new HttpPost(url);
		try {
			setHeader(request, headers);// 设置请求头
			setFormRaw(request, body);// 设置body
			return executeHttpRequest(httpClient, request);
		} finally {
			request.releaseConnection();
		}
	}

	/**
	 * post请求：form-data
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public static String postForm(String url, Map<String, String> params, Map<String, String> headers) {
		HttpPost request = new HttpPost(url);
		try {
			setHeader(request, headers);// 设置请求头
			setFormData(request, params);
			return executeHttpRequest(httpClient, request);
		} finally {
			request.releaseConnection();
		}
	}

	/**
	 * post binary请求
	 * 
	 * @param url
	 * @param bytes
	 * @return
	 */
	public static byte[] postBinary(String url, byte[] bytes, Map<String, String> headers) {
		HttpPost request = new HttpPost(url);
		try {
			setHeader(request, headers);
			setFormBinary(request, bytes);
			return executeHttpBinaryRequest(httpClient, request);
		} finally {
			request.releaseConnection();
		}
	}

	/**
	 * 设置表单请求头
	 * 
	 * @param request
	 * @param headers
	 */
	private static void setHeader(HttpRequestBase request, Map<String, String> headers) {
		if (null != headers) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * 设置表单raw
	 * 
	 * @param request
	 * @param body
	 */
	private static void setFormRaw(HttpPost request, String body) {
		// 设置body
		if (body != null) {
			StringEntity entity = new StringEntity(body, DEFAULT_CHARSET);// 解决中文乱码问题
			entity.setContentType(ContentType.APPLICATION_JSON.toString());
			request.setEntity(entity);
		}
	}

	/**
	 * 设置表单data
	 * 
	 * @param request
	 * @param params
	 */
	private static void setFormData(HttpPost request, Map<String, String> params) {
		if (params != null) {
			List<NameValuePair> formParams = new ArrayList<>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				request.setEntity(new UrlEncodedFormEntity(formParams, DEFAULT_CHARSET));
			} catch (UnsupportedEncodingException e) {
				logger.error("httpPost 设置Entity error:", e);
			}
		}
	}

	/**
	 * 设置表单binary
	 * 
	 * @param request
	 * @param params
	 */
	private static void setFormBinary(HttpPost request, byte[] bytes) {
		if (bytes != null) {
			InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(bytes));
			entity.setContentType(ContentType.APPLICATION_OCTET_STREAM.toString());
			request.setEntity(entity);
		}
	}

	/**
	 * 执行http请求
	 * 
	 * @param httpClient
	 *            http客户端
	 * @param http
	 *            http请求
	 * @return
	 */
	private static String executeHttpRequest(CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
		String responseContent = null;
		try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, DEFAULT_CHARSET);
				EntityUtils.consume(entity); // 关闭content stream
			}
		} catch (ClientProtocolException e) {
			logger.error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下" + e);
		} catch (IOException e) {
			logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下" + e);
		}

		logger.debug(httpRequest.getURI() + "：response=" + responseContent);
		return responseContent;
	}

	/**
	 * 执行http请求
	 * 
	 * @param httpClient
	 *            http客户端
	 * @param http
	 *            http请求
	 * @return
	 */
	private static byte[] executeHttpBinaryRequest(CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
		byte[] responseContent = null;
		try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toByteArray(entity);
				EntityUtils.consume(entity); // 关闭content stream
			}
			response.close();
		} catch (ClientProtocolException e) {
			logger.error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下" + e);
		} catch (IOException e) {
			logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下" + e);
		}

		logger.debug(httpRequest.getURI() + "：response=" + new String(responseContent));
		return responseContent;
	}
}
