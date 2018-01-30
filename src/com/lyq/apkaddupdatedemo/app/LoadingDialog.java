package com.lyq.apkaddupdatedemo.app;

import com.lyq.apkaddupdatedemo.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @ClassName: LoadingDialog
 * Copyright (c) 2016 hongongda, Inc.
 * @Description: 自定义加载对话框
 * @author lyq
 * @date 2017-3-13
 */
public class LoadingDialog extends Dialog {

	private TextView tvMsg;
	private String strMsg; // 进度信息
	private TextView tvRate; // 进度百分比
	
	public LoadingDialog(Context context, String msg) {
		super(context, R.style.ProDialogStyle);
		setContentView(R.layout.dlg_loading);
		
		this.strMsg = msg;
		tvMsg = (TextView) findViewById(R.id.tv_dlg_loading_msg);
		tvMsg.setText(strMsg);
		tvRate = (TextView) findViewById(R.id.tv_loading_rate);
	}
	
	public void setMsgWithRate(String msg, String rate) {
		tvMsg.setText(msg);
		tvRate.setVisibility(View.VISIBLE);
		tvRate.setText(rate + "%");
	}
}
