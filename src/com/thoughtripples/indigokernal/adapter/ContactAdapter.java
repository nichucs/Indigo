package com.thoughtripples.indigokernal.adapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.thoughtripples.indigokernal.R;
import com.thoughtripples.indigokernal.app.AppController;
import com.thoughtripples.indigokernal.model.ContactModel;

public class ContactAdapter extends ArrayAdapter<ContactModel> {

	Context context;
	int resource;
	ArrayList<ContactModel> contacts;
	ViewHolder holder=null;
	
	public ContactAdapter(Context context, int resource,
			ArrayList<ContactModel> objects) {
		super(context, resource, objects);
		this.context=context;
		this.contacts=objects;
		this.resource=resource;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contacts.size();
	}
	@Override
	public ContactModel getItem(int position) {
		// TODO Auto-generated method stub
		return contacts.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row =convertView;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) row.findViewById(R.id.contact_name);
			holder.desc=(TextView) row.findViewById(R.id.contact_relation);
			holder.image = (ImageView) row.findViewById(R.id.contact_image);
			holder.call=(ImageButton) row.findViewById(R.id.call_phone);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		ContactModel contact=contacts.get(position);
		holder.title.setText(contact.getName());
		holder.desc.setText(contact.getDesc());
		if(contact.getPhno()!=null && !contact.getPhno().equals("null"))
			holder.call.setVisibility(View.VISIBLE);
		else
			holder.call.setVisibility(View.GONE);
		if(contact.getImage_url()!=null && !contact.getImage_url().equalsIgnoreCase("null")){
			Cache cache = AppController.getInstance().getRequestQueue().getCache();
			Entry entry = cache.get(contact.getImage_url());
			if(entry != null){
				
					// handle data, like converting it to xml, json, bitmap etc.,
//					Log.d("Nzm", "Catched entry:"+data);
					holder.image.setImageBitmap(BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length));
				
			}else{
				ImageLoader imageLoader = AppController.getInstance().getImageLoader();
				imageLoader.get(contact.getImage_url(), new ImageListener() {
	
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("Nzm", "Image Load Error: " + error.getMessage());
				}
	
				@Override
				public void onResponse(ImageContainer response, boolean arg1) {
					if (response.getBitmap() != null) {
						// load image into imageview
//						holder.image.setVisibility(View.VISIBLE);
						holder.image.setImageBitmap(response.getBitmap());
//						Log.d("Nzm", "image set:"+holder.title.getText().toString());
						notifyDataSetChanged();
					}
				}
			});
			}
		}
		
		return row;
	}
	
	static class ViewHolder {
		TextView title,desc;
		ImageView image;
		ImageButton call;
	}

}
