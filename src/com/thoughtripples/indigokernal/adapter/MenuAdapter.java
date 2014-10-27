package com.thoughtripples.indigokernal.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thoughtripples.indigokernal.model.MenuModel;

public class MenuAdapter extends BaseAdapter {

	Context context;
	ArrayList<MenuModel> menu_items;
	
	public MenuAdapter(Context context, ArrayList<MenuModel> menu_items) {
		super();
		this.context = context;
		this.menu_items = menu_items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menu_items.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) {
		if(view==null){
			LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 view=inflater.inflate(android.R.layout.simple_list_item_1, null);
		}
		return view;
	}

}
