package com.lyq.apkaddupdatedemo.utils;

public class PatchUtils {

	/**
	 * 
	    * @Title: patch
	    * @Description: 将旧版apk与patch文件合成新版apk，存放于newApkPath
	    * @param oldApkPath 示例:/sdcard/old.apk
	    * @param newApkPath 示例:/sdcard/new.apk
	    * @param patchPath  示例:/sdcard/xx.patch
	    * @return 0
	    * @throws
	 */
	public static native int patch(String oldApkPath, String newApkPath, String patchPath);
}