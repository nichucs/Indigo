package com.thoughtripples.indigokernal.data;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gcm.GCMRegistrar;
import com.thoughtripples.indigokernal.app.AppController;
import com.thoughtripples.indigokernal.model.ActiveModels;

public class PrefetchData extends AsyncTask<Void, Void, Void> {
	Context context;
	
	public PrefetchData(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
//		makeJsonObjReq();
		MySQLiteHelper db = new MySQLiteHelper(context);
		db.open();
		db.cleartables();
		db.insert();
		ActiveModels.menu_items=db.getButtons(0);
		db.close();	
		return null;
	}
	/**
	 * Making json object request
	 * */
	private void makeJsonObjReq() {
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				"http://indigo.thoughtripples.com/api/update/", null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("Nzm", response.toString());
						 GCMRegistrar.setRegisteredOnServer(context, true);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("Nzm", "Error: " + error.getMessage());
						 GCMRegistrar.setRegisteredOnServer(context, false);
					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("appid", AppController.app_id);
				params.put("gcmid", AppController.gcm_key);
				params.put("phone1", AppController.phoneNumber1);
				params.put("phone2", AppController.phoneNumber2);
				params.put("time", ""+Calendar.getInstance().getTimeInMillis());

				return params;
			}

		};

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,"json_obj");

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);		
	}
}

