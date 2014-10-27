package com.thoughtripples.indigokernal.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.android.volley.Cache.Entry;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.thoughtripples.indigokernal.R;
import com.thoughtripples.indigokernal.app.AppController;
import com.thoughtripples.indigokernal.model.ImageItem;

/**
 * 
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class GridViewAdapter extends ArrayAdapter<ImageItem> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<ImageItem> data = new ArrayList<ImageItem>();
	ViewHolder holder = null;

	public GridViewAdapter(Context context, int layoutResourceId,
			ArrayList<ImageItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.imageTitle = (TextView) row.findViewById(R.id.text);
			holder.image = (ImageView) row.findViewById(R.id.image);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		final ImageItem item = data.get(position);
		holder.imageTitle.setText(item.getTitle());
//		holder.image.setImageBitmap(item.getImage());
		
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(item.getImage());
		if(entry != null){
			
				// handle data, like converting it to xml, json, bitmap etc.,
//				Log.d("Nzm", "Catched entry:"+data);
				holder.image.setImageBitmap(BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length));
			
		}else{
			ImageLoader imageLoader = AppController.getInstance().getImageLoader();
			imageLoader.get(item.getImage(), new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Nzm", "Image Load Error: " + error.getMessage());
			}

			@Override
			public void onResponse(ImageContainer response, boolean arg1) {
				if (response.getBitmap() != null) {
					// load image into imageview
//					holder.image.setVisibility(View.VISIBLE);
					holder.image.setImageBitmap(response.getBitmap());
//					Log.d("Nzm", "image set:"+holder.title.getText().toString());
					notifyDataSetChanged();
				}
			}
		});
		}
		
		row.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, item.getTitle() + "#Selected",
						Toast.LENGTH_LONG).show();
			}
		});
		return row;
	}

	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
}