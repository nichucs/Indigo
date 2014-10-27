package com.thoughtripples.indigokernal;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thoughtripples.indigokernal.data.MySQLiteHelper;
import com.thoughtripples.indigokernal.model.ActiveModels;
import com.thoughtripples.indigokernal.model.MenuModel;

public class Menus extends ListActivity {

	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_list);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, get_menus(ActiveModels.menu_items));
		setListAdapter(adapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		MenuModel b=ActiveModels.menu_items.get(position);
		String target=b.getTarget();
		if(target!=null && (target.equals("menu")||target.equals("link")||target.equals("html"))){
			MySQLiteHelper db=new MySQLiteHelper(getBaseContext());
			if(target.equals("menu")){
				db.open();
				ActiveModels.menu_items=db.getButtons(position+1);
				db.close();
				adapter.clear();
				adapter.addAll(get_menus(ActiveModels.menu_items));
				Log.d("Nzm", "adapteritem:"+adapter.getCount()+" \n menu_items:"+ActiveModels.menu_items.get(0).getText());
				adapter.notifyDataSetChanged();
			}else if(target.equals("link")){
				db.open();
				String link=db.getText(b.getTarget_id());
				db.close();
				Intent i=new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(link));
				startActivity(i);
			}else if(target.equals("html")){
				String target_class=b.getTarget();
				Log.d("nzm", "next activity:"+target_class);
				Intent i=new Intent(target_class);
				db.open();
				i.putExtra("url", db.getText(b.getTarget_id()));
				db.close();
				startActivity(i);
			}
		}else{
			String target_class=b.getTarget();
			Log.d("nzm", "next activity:"+target_class);
			Intent i=new Intent(target_class);
			i.putExtra("id", b.getTarget_id());
			startActivity(i);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		setListAdapter(adapter);
	}
	@Override
	public void onBackPressed() {
		MySQLiteHelper db=new MySQLiteHelper(this);
		db.open();
		Log.d("nzm", "current id:"+ActiveModels.menu_items.get(0).getId());
		ArrayList<MenuModel> items=db.goBack(ActiveModels.menu_items.get(0).getId());
		db.close();
		if(items!=null)
			{
			ActiveModels.menu_items=items;
			adapter.clear();
			adapter.addAll(get_menus(ActiveModels.menu_items));
			Log.d("Nzm", "adapteritem:"+adapter.getCount()+" \n menu_items:"+ActiveModels.menu_items.get(0).getText());
			adapter.notifyDataSetChanged();
			}
		else
			super.onBackPressed();
	}
	private ArrayList<String> get_menus(ArrayList<MenuModel> model){
		ArrayList<String> menu=new ArrayList<String>();
		if(model!=null)
		for(MenuModel m_model: model){
			menu.add(m_model.getText());
		}
		Log.d("Nzm", "List items:"+menu);
		return menu;
	}
}
