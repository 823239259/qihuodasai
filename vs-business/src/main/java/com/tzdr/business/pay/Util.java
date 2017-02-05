package com.tzdr.business.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Util {
	/**
	 * 字符串判空
	 * 
	 * @param arg
	 * @return 为空返回false,否则为true
	 */
	public static boolean isEmpty(String arg) {
		return arg == null || arg.length() <= 0;
	}

	/**
	 * 通过流来读取参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestToText(HttpServletRequest request) {
		StringBuilder read = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				read.append(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
		}
		return read.toString();
	}

	/**
	 * 字符串转Document
	 * 
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseDocument(String xmlStr) throws DocumentException {
		return DocumentHelper.parseText(xmlStr);
	}

	/**
	 * XML document转map对象
	 * 
	 * @param document
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String, Object> xmlToMap(Document document) throws DocumentException {
		if (document == null)
			throw new DocumentException();
		Map<String, Object> map = new HashMap<>();
		Element elements = document.getRootElement();
		Iterator<?> iterator = elements.elementIterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			map.put(element.getName(), element.getText());
		}
		return map;
	}
	/**
	 * md5加密
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String MD5Encode(String message) throws UnsupportedEncodingException{
		return DigestUtils.md5Hex(message.getBytes("UTF-8"));
	}
	public static String getAddresIP(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString();//获得本机IP
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}
