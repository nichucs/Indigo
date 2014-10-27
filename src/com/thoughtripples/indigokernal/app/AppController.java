package com.thoughtripples.indigokernal.app;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.thoughtripples.indigokernal.R;
import com.thoughtripples.indigokernal.utils.TelephonyInfo;

public class AppController extends Application {

	public static final String TAG = AppController.class
			.getSimpleName();
	public static final String SENDER_ID="483274956821";
	public static String app_id ;
	public static String gcm_key;
	public static String phoneNumber1;
	public static String phoneNumber2;
	public static boolean gcm_reg=false;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		app_id=getResources().getString(R.string.appid);
		SharedPreferences prefs=getSharedPreferences("indigo", MODE_PRIVATE);
		gcm_key=prefs.getString("gcmkey", null);
		TelephonyManager tMgr = (TelephonyManager)mInstance.getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber1 = tMgr.getLine1Number();
		phoneNumber2="0";
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	public static void displayMessage(Context context, String message) {
        Intent intent = new Intent("com.thoughtripples.indigokernal.DISPLAY_MESSAGE");
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }
}
