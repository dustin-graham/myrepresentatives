package com.uvad.demo.myrepresentatives.service;

import android.app.IntentService;
import android.content.Context;

public abstract class BaseService extends IntentService {

	private Context mContext;

	public BaseService(String name) {
		super(name);
		mContext = this;
	}

	public BaseService(String name, Context c) {
		super(name);
		if (c != null) {
			mContext = c;
		} else {
			mContext = this;
		}
	}

	/**
	 * it's necessary to use this method to get the appropriate context since
	 * the context may be explicitly set (such as during a unit test)
	 * 
	 * @return
	 */
	protected Context getContext() {
		return mContext;
	}

	protected boolean tokenExists(String token) {
		if (token == null || token.length() == 0) {
			return false;
		}
		return true;
	}
}
