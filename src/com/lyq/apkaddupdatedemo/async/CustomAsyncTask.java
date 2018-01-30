package com.lyq.apkaddupdatedemo.async;

import com.lyq.apkaddupdatedemo.R;
import com.lyq.apkaddupdatedemo.app.LoadingDialog;
import com.lyq.apkaddupdatedemo.app.Result;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

/**
 * @ClassName: CustomAsyncTask
 * Copyright (c) 2016 hongongda, Inc.
 * @Description: 自定义异步任务类
 * @author lyq
 * @date 2017-3-13
 */
public abstract class CustomAsyncTask extends AsyncTask<Object, Object, Result> {

	private Context context;
	private LoadingDialog dialog;
	private Dialog otherDialog;
	private String strMsg;
	
	private boolean isShow; // 是否显示对话框
	private boolean canInterrupt = true; // 任务是否能中断
	
	public CustomAsyncTask(Context context, Dialog dialog, boolean isShow) {
		super();
		this.context = context;
		this.otherDialog = dialog;
		this.isShow = isShow;
	}

	public CustomAsyncTask(Context context, String strMsg) {
		super();
		this.context = context;
		this.strMsg = strMsg;
		this.isShow = true;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (isShow) {
			// 之前的对话框先显示
			if (otherDialog != null) {
				otherDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (getStatus() == Status.RUNNING && canInterrupt) {
							CustomAsyncTask.this.cancel(true);
							Log.v("CustomAsyncTask", "任务中断");
						}
					}
				});
				otherDialog.show();
			} else {
				if (dialog == null) {
					if (TextUtils.isEmpty(strMsg)) {
						strMsg = context.getResources().getString(R.string.pub_loading);
					}
					dialog = new LoadingDialog(context, strMsg);
					dialog.setCanceledOnTouchOutside(false);
					dialog.setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							if (getStatus() == Status.RUNNING && canInterrupt) {
								CustomAsyncTask.this.cancel(true);
							}
						}
					});
				}
				dialog.show();
			}
		}
	}
	
	@Override
	protected abstract Result doInBackground(Object... params);

	@Override
	protected void onPostExecute(Result results) {
		super.onPostExecute(results);
		final Result result = results;
		canInterrupt = false;
		cancleDialog();
		
		switch (result.getResponCode()) {
		case Result.SUCCESS:
			doSuccess(result.getResponCode(), result.getResponExtra());
			break;
		case Result.FAILURE:
			doFail(result.getResponCode(), result.getResponExtra());
			break;
		}
	}
	
	/**
	 * 
	    * @Title: cancleDialog
	    * @Description: 关闭对话框
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	public void cancleDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.cancel();
		}
		
		if (otherDialog != null && otherDialog.isShowing()) {
			otherDialog.cancel();
		}
	}

	@Override
	protected void onCancelled() {
		cancleDialog();
		super.onCancelled();
	}

	/**
	 * 
	    * @Title: setprocess
	    * @Description: 设置对话框进度
	    * @param @param msg
	    * @param @param rate    参数
	    * @return void    返回类型
	    * @throws
	 */
	public void setprocess(String msg, String rate) {
		publishProgress(msg, rate);
	}
	
	/**
	 * 每次调用publishProgress方法都会触发onProgressUpdate方法
	 * onProgressUpdate在UI线程中执行，因此可以操作控件
	 */
	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		dialog.setMsgWithRate((String) values[0], (String) values[1]);
	}

	/**
	 * 
	    * @Title: doFail
	    * @Description: 失败处理
	    * @param @param code
	    * @param @param extra    参数
	    * @return void    返回类型
	    * @throws
	 */
	protected abstract void doFail(int code, Object extra);

	/**
	 * 
	    * @Title: doSuccess
	    * @Description: 成功处理
	    * @param @param code
	    * @param @param extra    参数
	    * @return void    返回类型
	    * @throws
	 */
	protected abstract void doSuccess(int code, Object extra);
}
