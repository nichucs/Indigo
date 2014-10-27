package com.thoughtripples.indigokernal;



import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.thoughtripples.indigokernal.app.AppController;
import com.thoughtripples.indigokernal.data.MySQLiteHelper;

public class DashBoard extends Activity implements OnClickListener {

	Button b1,b2,b3,b4;
	ArrayList<String> actions,ids;
	public enum Entity{
		MENU,
		LINK,
		GENERAL_PROFILE,
		SPECIAL_PROFILE,
		GALLERY,
		MAP,
		CONTACTS,
		MESSAGE
	}
	Entity target;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
		setContentView(R.layout.activity_dashboard);
		b1=(Button) findViewById(R.id.button1);
		b2=(Button) findViewById(R.id.button2);
		b3=(Button) findViewById(R.id.button3);
		b4=(Button) findViewById(R.id.button4);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		
		MySQLiteHelper db=new MySQLiteHelper(this);
		db.open();
		Cursor c=db.getDashboard();
		if(c!=null){
			c.moveToFirst();
			actions=new ArrayList<String>();
			ids=new ArrayList<String>();
			for(int i=0;i<4;i++){
				actions.add(c.getString(c.getColumnIndex("target")));
				ids.add(c.getString(c.getColumnIndex("targetid")));
				switch(i){
				case 0:
					b1.setText(c.getString(c.getColumnIndex("name")));
					makeImageRequest(1,c.getString(c.getColumnIndex("icon")));
					c.moveToNext();
					break;
				case 1:
					b2.setText(c.getString(c.getColumnIndex("name")));
					makeImageRequest(2,c.getString(c.getColumnIndex("icon")));
					c.moveToNext();
					break;
				case 2:
					b3.setText(c.getString(c.getColumnIndex("name")));
					makeImageRequest(3,c.getString(c.getColumnIndex("icon")));
					c.moveToNext();
					break;
				case 3:
					b4.setText(c.getString(c.getColumnIndex("name")));
					makeImageRequest(4,c.getString(c.getColumnIndex("icon")));
					c.moveToNext();
					break;
				}
			}
		}
		db.close();
	}
	
	public Drawable makeImageRequest(final int no,String url){
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	Drawable d = null;
	// If you are using NetworkImageView
//	imgNetWorkView.setImageUrl(url, imageLoader);

	


	// Loading image with placeholder and error image
//	imageLoader.get(url, ImageLoader.getImageListener(
//			imageView, R.drawable.ico_loading, R.drawable.ico_error));
	
	Cache cache = AppController.getInstance().getRequestQueue().getCache();
	Entry entry = cache.get(url);
	if(entry != null){
		try {
			String data = new String(entry.data, "UTF-8");
			// handle data, like converting it to xml, json, bitmap etc.,
//			Log.d("Nzm", "Catched entry:"+data);
			d= new BitmapDrawable(BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length));
			d.setBounds(0, 0, 72, 72);
			switch(no){
			case 1:
				b1.setCompoundDrawables(null, d, null, null);
				break;
			case 2:
				b2.setCompoundDrawables(null, d, null, null);
				break;
			case 3:
				b3.setCompoundDrawables(null, d, null, null);
				break;
			case 4:
				b4.setCompoundDrawables(null, d, null, null);
			}
		} catch (UnsupportedEncodingException e) {		
			e.printStackTrace();
		}
	}else{
		// cached response doesn't exists. Make a network call here
		
		imageLoader.get(url, new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Nzm", "Image Load Error: " + error.getMessage());
			}

			@Override
			public void onResponse(ImageContainer response, boolean arg1) {
				if (response.getBitmap() != null) {
					Log.d("Nzm", "non catched img width:"+response.getBitmap().getWidth());
//					imageView.setImageBitmap(response.getBitmap());
					Drawable d=new BitmapDrawable(response.getBitmap());;
					d.setBounds(0, 0, 72, 72);
					switch(no){
					case 1:
						b1.setCompoundDrawables(null, d, null, null);
						break;
					case 2:
						b2.setCompoundDrawables(null, d, null, null);
						break;
					case 3:
						b3.setCompoundDrawables(null, d, null, null);
						break;
					case 4:
						b4.setCompoundDrawables(null, d, null, null);
					}
				}
			}
		});

	}
	return d;
}
	
	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
	}
	@Override
	public void onClick(View v) {
		final View view=v;
		Animation anm=AnimationUtils.loadAnimation(getBaseContext(),R.anim.button_click);
		anm.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				switch(view.getId()){
				case R.id.button1:
					switchEntity(actions.get(0), ids.get(0));
					break;
				case R.id.button2:
					switchEntity(actions.get(1), ids.get(1));
					break;
				case R.id.button3:
					switchEntity(actions.get(2), ids.get(2));
					break;
				case R.id.button4:
					switchEntity(actions.get(3), ids.get(3));
					break;
				} 
			}
		});
		v.startAnimation(anm);	
	}
	
	private void switchEntity(String action, String id){
		int targetid=Integer.parseInt(id);
		target=findTarget(action);
		Intent i = null;
		switch(target){
		case MENU:
			i=new Intent(this, Menus.class);
			i.putExtra("id", targetid);
			break;
		case LINK:
			i=new Intent(this, Menus.class);
			i.putExtra("id", targetid);
			break;
		case GENERAL_PROFILE:
			i=new Intent(this, GeneralProfile.class);
			i.putExtra("id", targetid);
			break;
		case SPECIAL_PROFILE:
			i=new Intent(this, Menus.class);
			i.putExtra("id", targetid);
			break;
		case GALLERY:
			i=new Intent(this, Gallery.class);
			i.putExtra("id", targetid);
			break;
		case CONTACTS:
			i=new Intent(this, Contacts.class);
			i.putExtra("id", targetid);
			break;
		case MAP:
			i=new Intent(this, Menus.class);
			i.putExtra("id", targetid);
			break;
		case MESSAGE:
			i=new Intent(this, Messages.class);
			i.putExtra("id", targetid);
			break;
		}
		startActivity(i);
	}

	private Entity findTarget(String action) {
		if(action.equalsIgnoreCase("MESSAGE"))
			return Entity.MESSAGE;
		else if(action.equalsIgnoreCase("LINK"))
			return Entity.LINK;
		else if(action.equalsIgnoreCase("GENERAL_PROFILE"))
			return Entity.GENERAL_PROFILE;
		else if(action.equalsIgnoreCase("SPECIAL_PROFILE"))
			return Entity.SPECIAL_PROFILE;
		else if(action.equalsIgnoreCase("GALLERY"))
			return Entity.GALLERY;
		else if(action.equalsIgnoreCase("CONTACTS"))
			return Entity.CONTACTS;
		else if(action.equalsIgnoreCase("map"))
			return Entity.MAP;
		else
			return Entity.MENU;
	}
}
