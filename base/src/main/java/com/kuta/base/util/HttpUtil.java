package com.kuta.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.kuta.base.entity.KutaConstants;

/**
 * HTTP tool class
 * */
public class HttpUtil {
	
	/**
	 * Execute a get request
	 *
	 * @param url Website url
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String get(String url) throws IOException {
		return get(url, null);
	}

	/**
	 * Execute a get request
	 *
	 * @param url Website url
	 * @param headers Header definition
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String get(String url, Map<String, String> headers) throws IOException {
		return fetch("GET", url, null, headers);
	}

	/**
	 * Execute a post request
	 *
	 * @param url Website url
	 * @param body Requested parameters
	 * @param headers Header definition
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String post(String url, String body, Map<String, String> headers) 
			throws IOException {
		return fetch("POST", url, body, headers);
	}

	/**
	 * Execute a post request
	 *
	 * @param url Website url
	 * @param body Requested parameters
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String post(String url, String body) throws IOException {
		return post(url, body, null);
	}

	/**
	 * Execute a post request,Content-Type is application/x-www-form-urlencoded
	 *
	 * @param url Website url
	 * @param params Requested parameters
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String postForm(String url, Map<String, String> params) throws IOException {
		return postForm(url, params, null);
	}

	/**
	 * Execute a post request,Content-Type is application/x-www-form-urlencoded
	 *
	 * @param url Website url
	 * @param params Requested parameters
	 * @param headers Header definition
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String postForm(String url, Map<String, String> params, Map<String, String> headers)
			throws IOException {
		// set content type
		if (headers == null) {
			headers = new HashMap<>();
		}
		headers.put("Content-Type", "application/x-www-form-urlencoded");

		// parse parameters
		String body = "";
		if (params != null) {
			boolean first = true;
			for (String param : params.keySet()) {
				if (first) {
					first = false;
				} else {
					body += "&";
				}
				String value = params.get(param);
				body += URLEncoder.encode(param, "UTF-8") + "=";
				body += URLEncoder.encode(value, "UTF-8");
			}
		}

		return post(url, body, headers);
	}

	/**
	 * Execute a put request
	 *
	 * @param url Website url
	 * @param body Requested parameters
	 * @param headers Header definition
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String put(String url, String body, Map<String, String> headers) throws IOException {
		return fetch("PUT", url, body, headers);
	}

	/**
	 * Execute a put request
	 *
	 * @param url Website url
	 * @param body Requested parameters
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String put(String url, String body) throws IOException {
		return put(url, body, null);
	}

	/**
	 * Execute a delete request
	 *
	 * @param url Website url
	 * @param headers Header definition
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String delete(String url, Map<String, String> headers) throws IOException {
		return fetch("DELETE", url, null, headers);
	}

	/**
	 * Execute a delete request
	 *
	 * @param Website url
	 * @return HTTP returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String delete(String url) throws IOException {
		return delete(url, null);
	}

	/**
	 * Add parameters on URL
	 *
	 * @param url Website url
	 * @param params Parameter set
	 * @return URL with parameters
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String appendQueryParams(String url, Map<String, String> params) throws IOException {
		String fullUrl = url;
		if (params != null) {
			boolean first = (fullUrl.indexOf('?') == -1);
			for (String param : params.keySet()) {
				if (first) {
					fullUrl += '?';
					first = false;
				} else {
					fullUrl += '&';
				}
				String value = params.get(param);
				fullUrl += URLEncoder.encode(param, "UTF-8") + '=' + URLEncoder.encode(value, "UTF-8");
			}
		}
		return fullUrl;
	}

	/**
	 * Gets all parameters on the URL
	 *
	 * @param url Website url
	 * @return Parameter set
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static Map<String, String> getQueryParams(String url) throws IOException {
		Map<String, String> params = new HashMap<>();

		int start = url.indexOf('?');
		while (start != -1) {
			// read parameter name
			int equals = url.indexOf('=', start);
			String param = "";
			if (equals != -1) {
				param = url.substring(start + 1, equals);
			} else {
				param = url.substring(start + 1);
			}

			// read parameter value
			String value = "";
			if (equals != -1) {
				start = url.indexOf('&', equals);
				if (start != -1) {
					value = url.substring(equals + 1, start);
				} else {
					value = url.substring(equals + 1);
				}
			}

			params.put(URLDecoder.decode(param, "UTF-8"), URLDecoder.decode(value, "UTF-8"));
		}

		return params;
	}

	/**
	 * Delete all parameters on URL
	 *
	 * @param url Website url
	 * @return URL of deleted parameter
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String removeQueryParams(String url) throws IOException {
		int q = url.indexOf('?');
		if (q != -1) {
			return url.substring(0, q);
		} else {
			return url;
		}
	}

	/**
	 * Execute a request
	 *
	 * @param method HTTP methods, such as "get" or "post"
	 * @param url Website url
	 * @param body parameter
	 * @param headers Header information
	 * @return Returned results
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String fetch(String method, String url, String body, Map<String, String> headers) throws IOException {
		// connection
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setConnectTimeout(100000);
		conn.setReadTimeout(300000);

		// method
		if (method != null) {
			conn.setRequestMethod(method);
		}

		// headers
		if (headers != null) {
			for (String key : headers.keySet()) {
				conn.addRequestProperty(key, headers.get(key));
			}
		}

		// body
		if (body != null) {
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();
			os.close();
		}
		
		InputStream ips = null;
		byte[] header = new byte[2];
		try {
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			bis.mark(2);
			int result = bis.read(header);
			// reset输入流到开始位置
			bis.reset();
			// 判断是否是GZIP格式
			int ss = (header[0] & 0xff) | ((header[1] & 0xff) << 8);
			if(result!=-1 && ss == GZIPInputStream.GZIP_MAGIC) {
				//System.out.println("为数据压缩格式...");
				ips= new GZIPInputStream(bis);
			} else {
		        // 取前两个字节
				ips= bis;
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
			ips = conn.getInputStream();
		}
		
		String response = streamToString(ips);
		ips.close();

		// handle redirects
		if (conn.getResponseCode() == 301) {
			String location = conn.getHeaderField("Location");
			return fetch(method, location, body, headers);
		}
		conn.disconnect();
		return response;
	}

	public static ResponseWithCode getWithCode(String url) throws IOException{
		return fetchWithCode("GET", url, null, null);
	}
	public static ResponseWithCode fetchWithCode(String method, String url, String body, Map<String, String> headers) throws IOException {
		// connection
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setConnectTimeout(100000);
		conn.setReadTimeout(300000);

		// method
		if (method != null) {
			conn.setRequestMethod(method);
		}

		// headers
		if (headers != null) {
			for (String key : headers.keySet()) {
				conn.addRequestProperty(key, headers.get(key));
			}
		}

		// body
		if (body != null) {
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();
			os.close();
		}
		
		InputStream ips = null;
		byte[] header = new byte[2];
		try {
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			bis.mark(2);
			int result = bis.read(header);
			// reset输入流到开始位置
			bis.reset();
			// 判断是否是GZIP格式
			int ss = (header[0] & 0xff) | ((header[1] & 0xff) << 8);
			if(result!=-1 && ss == GZIPInputStream.GZIP_MAGIC) {
				//System.out.println("为数据压缩格式...");
				ips= new GZIPInputStream(bis);
			} else {
		        // 取前两个字节
				ips= bis;
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
			ips = conn.getInputStream();
		}
		
		String response = streamToString(ips);
		ips.close();
		
		// handle redirects
		if (conn.getResponseCode() == 301) {
			String location = conn.getHeaderField("Location");
			return fetchWithCode(method, location, body, headers);
		}
		conn.disconnect();
		return ResponseWithCode.create(response, conn.getResponseCode());
	}

	/**
	 * Read content from input stream
	 *
	 * @param in Input stream
	 * @return Read string
	 * @throws IOException Thrown when an IO exception occurs
	 */
	public static String streamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();

		BufferedReader br = new BufferedReader(new InputStreamReader(in, KutaConstants.ENCODE_UTF_8));
		String line;
		while ((line = br.readLine()) != null) {
			out.append(line);
		}
		br.close();
		return out.toString();
	}
}
