package com.lyq.apkaddupdatedemo.app;

/**
 * @ClassName: Result
 * Copyright (c) 2016 hongongda, Inc.
 * @Description: 任务执行结果
 * @author lyq
 * @date 2017-3-13
 */
public class Result {
	
	public static final int SUCCESS = 0; // 成功
	public static final int FAILURE = 1; // 失败
	
	private int responCode; // 响应码
	private Object responExtra; // 响应参数
	
	public Result(int responCode, Object responExtra) {
		this.responCode = responCode;
		this.responExtra = responExtra;
	}

	public int getResponCode() {
		return responCode;
	}

	public void setResponCode(int responCode) {
		this.responCode = responCode;
	}

	public Object getResponExtra() {
		return responExtra;
	}

	public void setResponExtra(Object responExtra) {
		this.responExtra = responExtra;
	}
}
