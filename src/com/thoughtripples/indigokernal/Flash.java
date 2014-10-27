package com.thoughtripples.indigokernal;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.thoughtripples.indigokernal.app.AppController;
import com.thoughtripples.indigokernal.data.MySQLiteHelper;
import com.thoughtripples.indigokernal.data.PrefetchData;
import com.thoughtripples.indigokernal.model.ActiveModels;
import com.thoughtripples.indigokernal.utils.WakeLocker;

public class Flash extends Activity {

	Activity activity=this;
    AsyncTask<Void, Void, Void> mRegisterTask;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
     // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
//        GCMRegistrar.checkManifest(this);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
				"com.thoughtripples.indigokernal.DISPLAY_MESSAGE"));
        final String regId = GCMRegistrar.getRegistrationId(this);
        
        // Check if regid already presents
//        if (regId.equals("")) {
//            // Registration is not present, register now with GCM           
//            GCMRegistrar.register(this, AppController.SENDER_ID);
//        } else {
            // Skips registration.              
        	prefs=getSharedPreferences("indigo", MODE_PRIVATE);
            editor=prefs.edit();
            editor.putString("gcmkey", regId);
            editor.commit();
            AppController.gcm_key=regId;
            AppController.gcm_reg=true;
//        }
       loadData();
    }
	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString("message");
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			// Showing received message			
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	public void loadData() {
		 if(AppController.gcm_reg==true)
	        {    new PrefetchData(this){
		        	protected void onPostExecute(Void result) {
		        		Intent i= new Intent(activity, DashBoard.class);
		        		startActivity(i);
		        		finish();
		        	};
		        }.execute();
	        }
		
	}
    @Override
    protected void onDestroy() {
    	try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
    	super.onDestroy();
    }

}
