package com.thoughtripples.indigokernal;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;

import com.thoughtripples.indigokernal.adapter.ContactAdapter;
import com.thoughtripples.indigokernal.data.MySQLiteHelper;
import com.thoughtripples.indigokernal.model.ContactModel;

public class Contacts extends ListActivity {

	ContactAdapter adapter;
	ArrayList<ContactModel> contacts;
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_list);
		if(getIntent().hasExtra("id")){
			id=getIntent().getIntExtra("id", 0);
		}
		contacts=getData();
		adapter=new ContactAdapter(this, R.layout.members_listitem, contacts);
		setListAdapter(adapter);
	}
	private ArrayList<ContactModel> getData() {
		final ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
		if(id!=0){
			MySQLiteHelper db=new MySQLiteHelper(this);
			db.open();
			Cursor c=db.getEntityContents(id);
			if(c!=null){
				c.moveToFirst();
				String property1=c.getString(c.getColumnIndex("property"));
				String value1=c.getString(c.getColumnIndex("value"));
				c.moveToNext();
				String property2=c.getString(c.getColumnIndex("property"));
				String value2=c.getString(c.getColumnIndex("value"));
				c.moveToNext();
				String property3=c.getString(c.getColumnIndex("property"));
				String value3=c.getString(c.getColumnIndex("value"));
				c.moveToNext();
				String property4=c.getString(c.getColumnIndex("property"));
				String value4=c.getString(c.getColumnIndex("value"));
				String[] imgs=null,names=null,descs=null,phnos=null;
				
				if(property1.equalsIgnoreCase("images")){
					 imgs=value1.split(",");						
				}else if(property2.equalsIgnoreCase("images")){
					 imgs=value2.split(",");
				}else if(property3.equalsIgnoreCase("images")){
					 imgs=value3.split(",");
				}else if(property4.equalsIgnoreCase("images")){
					 imgs=value4.split(",");
				}

				if(property1.equalsIgnoreCase("names")){
					names=value1.split(",");						
				}else if(property2.equalsIgnoreCase("names")){
					names=value2.split(",");
				}else if(property3.equalsIgnoreCase("names")){
					names=value3.split(",");
				}else if(property4.equalsIgnoreCase("names")){
					names=value4.split(",");
				}

				if(property1.equalsIgnoreCase("relations")){
					descs=value1.split(",");						
				}else if(property2.equalsIgnoreCase("relations")){
					descs=value2.split(",");
				}else if(property3.equalsIgnoreCase("relations")){
					descs=value3.split(",");
				}else if(property4.equalsIgnoreCase("relations")){
					descs=value4.split(",");
				}

				if(property1.equalsIgnoreCase("phones")){
					phnos=value1.split(",");						
				}else if(property2.equalsIgnoreCase("phones")){
					phnos=value2.split(",");
				}else if(property3.equalsIgnoreCase("phones")){
					phnos=value3.split(",");
				}else if(property4.equalsIgnoreCase("phones")){
					phnos=value4.split(",");
				}
				
				for(int i=0;i<names.length;i++){
					ContactModel contact=new ContactModel(imgs[i], names[i], descs[i], phnos[i]);
					contacts.add(contact);
				}
			}
			db.close();
		}
		return contacts;
	}
}
