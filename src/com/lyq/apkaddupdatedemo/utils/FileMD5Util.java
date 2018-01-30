package com.lyq.apkaddupdatedemo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * 
    * @ClassName: FileMD5Util
	* Copyright (c) 2016 hongongda, Inc.
    * @Description: 计算字符串或者文件的MD5值
    * @author lyq
    * @date 2017-4-17
    *
 */
public class FileMD5Util {
	
	protected static char hexDigits[] = 
		{ '0', '1', '2', '3', '4', '5', '6', '7', 
		'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	protected static MessageDigest messagedigest;
	
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	    * @Title: getFileMD5String
	    * @Description: 计算文件的MD5值
	    * @param @param fileName
	    * @param @return
	    * @param @throws IOException    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String getFileMD5String(String fileName) throws IOException {
		return getFileMD5String(new File(fileName));
	}
	
	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		messagedigest.update(byteBuffer);
		in.close();
		return bufferToHex(messagedigest.digest());
	}

	/**
	 * 
	    * @Title: getMD5String
	    * @Description: 计算字符串的MD5值
	    * @param @param s
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	/**
	 * 
	    * @Title: checkMD5
	    * @Description: 比较MD5的值
	    * @param @param oldMd5
	    * @param @param newMd5
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	 */
	public static boolean checkMD5(String oldMd5, String newMd5) {
		return oldMd5.equals(newMd5);
	}

}