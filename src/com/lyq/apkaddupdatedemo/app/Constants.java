package com.lyq.apkaddupdatedemo.app;

import android.os.Environment;

public class Constants {
	
	// SD卡根目录
	public static final String SD_CARD_PATH 
			= Environment.getExternalStorageDirectory().toString();
	
	// 旧版apk路径
	public static final String OLD_APK_PATH = SD_CARD_PATH 
			+ "/ApkAddUpdateServer/App_V4.21.apk";
	
	// 新版apk路径
	public static final String NEW_APK_PATH = SD_CARD_PATH 
			+ "/ApkAddUpdateServer/App_V4.22.apk";
	
	// 差分包路径
	public static final String PATCH_PATH = SD_CARD_PATH 
			+ "/ApkAddUpdateServer/App_apk.patch";
	
	// 旧版apk与差分包合成新版apk的路径
	public static final String OLD2NEW_APK_PATH = SD_CARD_PATH
			+ "/ApkAddUpdateServer/App_V4.21_new.apk";
	
}
