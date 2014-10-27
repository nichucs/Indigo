package com.thoughtripples.indigokernal.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.android.volley.Cache.Entry;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.thoughtripples.indigokernal.R;
import com.thoughtripples.indigokernal.app.AppController;
import com.thoughtripples.indigokernal.model.MessageModel;

public class MessageAdapter extends ArrayAdapter<MessageModel> {

	Context context; int resource;
	ArrayList<MessageModel> messages;
	ViewHolder holder = null;
	
	public MessageAdapter(Context context, int resource,
			ArrayList<MessageModel> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		messages=objects;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}
	@Override
	public MessageModel getItem(int position) {
		// TODO Auto-generated method stub
		return messages.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row =convertView;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) row.findViewById(R.id.msghead);
			holder.desc=(TextView) row.findViewById(R.id.msgdesc);
			holder.time=(TextView) row.findViewById(R.id.msgtime);
			holder.image = (ImageView) row.findViewById(R.id.msgimg);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		MessageModel message=messages.get(position);
		holder.title.setText(message.getHead());
		holder.desc.setText(message.getDesc());
		holder.time.setText(message.getDate());
		
		if(message.getImage_url()!=null && !message.getImage_url().equalsIgnoreCase("null")){
			Cache cache = AppController.getInstance().getRequestQueue().getCache();
			Entry entry = cache.get(message.getImage_url());
			if(entry != null){
				
					// handle data, like converting it to xml, json, bitmap etc.,
//					Log.d("Nzm", "Catched entry:"+data);
					holder.image.setVisibility(View.VISIBLE);
					holder.image.setImageBitmap(BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length));
				
			}else{
				ImageLoader imageLoader = AppController.getInstance().getImageLoader();
				imageLoader.get(message.getImage_url(), new ImageListener() {
	
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("Nzm", "Image Load Error: " + error.getMessage());
				}
	
				@Override
				public void onResponse(ImageContainer response, boolean arg1) {
					if (response.getBitmap() != null) {
						// load image into imageview
						holder.image.setVisibility(View.VISIBLE);
						holder.image.setImageBitmap(response.getBitmap());
//						Log.d("Nzm", "image set:"+holder.title.getText().toString());
						notifyDataSetChanged();
					}
				}
			});
			}
		}else{
			holder.image.setVisibility(View.GONE);
		}
		
		return row;
	}
	static class ViewHolder {
		TextView title,desc,time;
		ImageView image;
	}
}
