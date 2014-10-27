package com.thoughtripples.indigokernal;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.thoughtripples.indigokernal.adapter.MessageAdapter;
import com.thoughtripples.indigokernal.data.MySQLiteHelper;
import com.thoughtripples.indigokernal.model.MessageModel;

public class Messages extends Activity implements OnClickListener {

	ListView msg_list;
	EditText head,desc;
	ImageButton attach,send;
	MessageAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_list);
		
		msg_list=(ListView) findViewById(R.id.msgs);
		head=(EditText) findViewById(R.id.head);
		desc=(EditText) findViewById(R.id.desc);
		attach=(ImageButton) findViewById(R.id.attach);
		send=(ImageButton) findViewById(R.id.send);
		attach.setOnClickListener(this);
		send.setOnClickListener(this);
		registerForContextMenu(attach);
		
		MySQLiteHelper db=new MySQLiteHelper(this);
		db.open();
		ArrayList<MessageModel> messages=db.getMessages();
		db.close();
		adapter=new MessageAdapter(this, R.layout.message_row, messages);
		msg_list.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.attach:
			openContextMenu(attach);
			break;
		case R.id.send:
			break;
		}
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if(v.getId()==R.id.attach){
			menu.add(0,1,1,"Take photo");
			menu.add(0,2,2,"Gallery");
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case 1:
			Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, 0);
			break;
		case 2:
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK && data!=null)
		switch(requestCode){
		case 0:
			Bitmap bmp=(Bitmap) data.getExtras().get("data");
			break;
		case 1:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
