package com.tzdr.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileProcessed {
	/**
	 * 上传
	 * @param fromUrl
	 * 			源地址
	 * @param toUrl
	 * 			最终地址
	 */
	public void uploadFile(String fromUrl,String toUrl){
		try {
			BufferedInputStream bufferInutStream = new BufferedInputStream(new FileInputStream(fromUrl));
			BufferedOutputStream bufferOutStream = new BufferedOutputStream(new FileOutputStream(toUrl));
			byte[] readSize = new byte[1024];
			while(bufferInutStream.read(readSize) != -1){
				bufferOutStream.write(readSize, 0, readSize.length);
			}
			bufferOutStream.flush();
			bufferInutStream.close();
			bufferOutStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param toUrl
	 * 				最终地址
	 * @param inputStream
	 * 				流
	 */
	public void uploadFile(String toUrl,InputStream inputStream){
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(toUrl));
			byte[] readSize = new byte[1024];
			while(bufferedInputStream.read(readSize) != -1){
				bufferedOutputStream.write(readSize, 0, readSize.length);
			}
			bufferedOutputStream.flush();
			bufferedInputStream.close();
			bufferedOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取流转String
	 * @param inputStream
	 * 				流
	 * @return 如果正常返回String 字符串,如果异常返回null
	 */
	public String readInputSteam(InputStream inputStream){
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder read = new StringBuilder();
		String line;
		try {
			while((line = bufferedReader.readLine()) != null){
				read.append(line);
			}
			bufferedReader.close();
			return read.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
