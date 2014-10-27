package com.thoughtripples.indigokernal;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import com.thoughtripples.indigokernal.adapter.GridViewAdapter;
import com.thoughtripples.indigokernal.data.MySQLiteHelper;
import com.thoughtripples.indigokernal.model.ImageItem;

public class Gallery extends Activity {
	private GridView gridView;
	private GridViewAdapter customGridAdapter;
	int id;
	ArrayList<ImageItem> imageItems;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		if(getIntent().hasExtra("id")){
			id=getIntent().getIntExtra("id", 0);
		}
		
		gridView = (GridView) findViewById(R.id.gridView);
		this.imageItems=getData();
		customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, imageItems);
		gridView.setAdapter(customGridAdapter);
	}
	private ArrayList<ImageItem> getData() {
		final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
		if(id!=0){
			MySQLiteHelper db=new MySQLiteHelper(this);
			db.open();
			Cursor c=db.getEntityContents(id);
			if(c!=null){
				c.moveToFirst();
				String property1=c.getString(c.getColumnIndex("property"));
				String value1=c.getString(c.getColumnIndex("value"));
				c.moveToNext();
//				String property2=c.getString(c.getColumnIndex("property"));
				String value2=c.getString(c.getColumnIndex("value"));
				if(property1.equalsIgnoreCase("images")){
					String[] imgs=value1.split(",");
					String[] titles=value2.split(",");
					for(int i=0;i<imgs.length;i++){
						ImageItem img=new ImageItem(imgs[i], titles[i]);
						imageItems.add(img);
					}						
				}
				else if(property1.equalsIgnoreCase("names")){
					String[] imgs=value2.split(",");
					String[] titles=value1.split(",");
					for(int i=0;i<imgs.length;i++){
						ImageItem img=new ImageItem(imgs[i], titles[i]);
						imageItems.add(img);
					}
				}
			}
			db.close();
		}
		return imageItems;
	}
}
