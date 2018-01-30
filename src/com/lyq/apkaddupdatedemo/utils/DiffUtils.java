package com.lyq.apkaddupdatedemo.utils;

public class DiffUtils {

	/**
	 * 
	    * @Title: genDiff
	    * @Description: 比较新旧apk文件的差异，生成patch文件，存放于patchPath
	    * @param oldApkPath 示例:/sdcard/old.apk
	    * @param newApkPath 示例:/sdcard/new.apk
	    * @param patchPath  示例:/sdcard/xx.patch
	    * @return 0
	    * @throws
	 */
	public static native int genDiff(String oldApkPath, String newApkPath, String patchPath);
}