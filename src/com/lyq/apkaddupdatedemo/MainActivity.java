package com.lyq.apkaddupdatedemo;

import java.io.File;
import java.io.IOException;

import com.lyq.apkaddupdatedemo.app.Constants;
import com.lyq.apkaddupdatedemo.app.Result;
import com.lyq.apkaddupdatedemo.async.CustomAsyncTask;
import com.lyq.apkaddupdatedemo.utils.DiffUtils;
import com.lyq.apkaddupdatedemo.utils.FileMD5Util;
import com.lyq.apkaddupdatedemo.utils.PatchUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView tvPatchPath;
	private Button btnMakePatch;
	private Button btnMakeApk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
	}
	
	private void initView() {
		tvPatchPath = (TextView) findViewById(R.id.tv_patch_path);
		btnMakePatch = (Button) findViewById(R.id.btn_make_patch);
		btnMakeApk = (Button) findViewById(R.id.btn_make_apk);
	}
	
	private void initEvent() {
		btnMakePatch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					new MakePatchAsyncTask(MainActivity.this, "开始生成差分包，请等待...").execute(0);
				} else {
					Toast.makeText(MainActivity.this, "请插入SD卡！", Toast.LENGTH_LONG).show();
                }
			}
		});
		
		btnMakeApk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File patchFile = new File(Constants.PATCH_PATH);
				if (patchFile.exists()) {
					new MakeNewApkAsyncTask(MainActivity.this, "开始合成新版apk，请等待...").execute(0);
				} else {
					Toast.makeText(MainActivity.this, "请先生成差分包！", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	/**
	 * 
	    * @ClassName: MakePatchAsyncTask
		* Copyright (c) 2016 hongongda, Inc.
	    * @Description: 生成差分包
	    * @author lyq
	    * @date 2017-4-17
	    *
	 */
	private class MakePatchAsyncTask extends CustomAsyncTask {
		
		private long start; // 开始时间
		private long end; // 结束时间
		
		public MakePatchAsyncTask(Context context, String strMsg) {
			super(context, strMsg);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			start = System.currentTimeMillis();
			tvPatchPath.setText("开始生成差分包，请等待...\n");
		}

		@Override
		protected Result doInBackground(Object... params) {
			int genDiff = DiffUtils.genDiff(
					Constants.OLD_APK_PATH, Constants.NEW_APK_PATH, Constants.PATCH_PATH);

			if (genDiff == 0) {
				return new Result(Result.SUCCESS, "生成差分包成功！");
			} else {
				return new Result(Result.FAILURE, "生成差分包失败！");
			}
		}
		    
		@Override
		protected void doFail(int code, Object extra) {
			Toast.makeText(MainActivity.this, (String) extra, Toast.LENGTH_LONG).show();
		}
		    
		@Override
		protected void doSuccess(int code, Object extra) {
			end = System.currentTimeMillis();
			tvPatchPath.setText(tvPatchPath.getText().toString() 
					+ "\n生成差分包成功：\n" + Constants.PATCH_PATH + "\n耗时："
					+ (end - start) / 1000 + "秒\n");
			Toast.makeText(MainActivity.this, (String) extra, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * 
	    * @ClassName: MakeNewApkAsyncTask
		* Copyright (c) 2016 hongongda, Inc.
	    * @Description: 合成新版apk并进行MD5校验
	    * @author lyq
	    * @date 2017-4-17
	    *
	 */
	private class MakeNewApkAsyncTask extends CustomAsyncTask {
		
		private long start; // 开始时间
		private long end; // 结束时间
		
		public MakeNewApkAsyncTask(Context context, String strMsg) {
			super(context, strMsg);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			start = System.currentTimeMillis();
			tvPatchPath.setText(tvPatchPath.getText().toString() + "\n开始合成新版apk，请等待...\n");
		}

		@Override
		protected Result doInBackground(Object... params) {
			int patchResult = PatchUtils.patch(
					Constants.OLD_APK_PATH, Constants.OLD2NEW_APK_PATH, Constants.PATCH_PATH);

			if (patchResult == 0) {
				return new Result(Result.SUCCESS, "合成新版apk成功！");
			} else {
				return new Result(Result.FAILURE, "合成新版apk失败！");
			}
		}
		    
		@Override
		protected void doFail(int code, Object extra) {
			Toast.makeText(MainActivity.this, (String) extra, Toast.LENGTH_LONG).show();
		}
		    
		@Override
		protected void doSuccess(int code, Object extra) {
			end = System.currentTimeMillis();
			tvPatchPath.setText(tvPatchPath.getText().toString() 
					+ "\n合成新版apk成功：\n" + Constants.OLD2NEW_APK_PATH + "\n耗时："
					+ (end - start) / 1000 + "秒\n");
			Toast.makeText(MainActivity.this, (String) extra, Toast.LENGTH_LONG).show();
			
			String newApkMd5 = "";
			String old2newApkMd5 = "";
			try {
				newApkMd5 = FileMD5Util.getFileMD5String(Constants.NEW_APK_PATH);
				old2newApkMd5 = FileMD5Util.getFileMD5String(Constants.OLD2NEW_APK_PATH);
				
				tvPatchPath.setText(tvPatchPath.getText().toString() + "\n开始进行文件MD5校验...\n"
						+ "新版apk的MD5值：\n" + newApkMd5
						+ "\n合成的新版apk的MD5值：\n" + old2newApkMd5
						+ "\nMD5校验结果：" + FileMD5Util.checkMD5(newApkMd5, old2newApkMd5));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static {
		System.loadLibrary("ApkAddUpdateServer");
	}
}
