package com.thoughtripples.indigokernal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thoughtripples.indigokernal.model.MenuModel;
import com.thoughtripples.indigokernal.model.MessageModel;

public class MySQLiteHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "DBindigolib";

	private static final String KEY_ID = "_id";

	final String Appgeneral_TABLE= "CREATE TABLE IF NOT EXISTS Appgeneral( " + KEY_ID
			+ " INTEGER PRIMARY KEY , " + "icon TEXT, "
			+  "name VARCHAR(20), "
			+ "last_update TEXT)";
	final String Dashboard_TABLE = "CREATE TABLE IF NOT EXISTS Dashboard( " + KEY_ID
			+ " INTEGER PRIMARY KEY , " + "icon TEXT, "
			+ "name VARCHAR(20), " + "target VARCHAR(20), "
			+ "targetid INTEGER)";
	final String Menu_TABLE = "CREATE TABLE IF NOT EXISTS Menu( " + KEY_ID
			+ " INTEGER PRIMARY KEY , " + "text TEXT, "
			+ "parentid INTEGER, " + "sortorder INTEGER, " + "target VARCHAR(20), "
			+ "targetid INTEGER)";
	final String Menu_props_TABLE = "CREATE TABLE IF NOT EXISTS Menu_props( " + KEY_ID
			+ " INTEGER PRIMARY KEY , " + "itemid INTEGER, "
			+ "property TEXT, " + "value TEXT, FOREIGN KEY(itemid) REFERENCES Menu(_id))";
	final String Entities_TABLE= "CREATE TABLE IF NOT EXISTS Entities( " + KEY_ID
			+ " INTEGER PRIMARY KEY , " + "menu_id INTEGER, "
			+ "property TEXT, " + "value TEXT, FOREIGN KEY(menu_id) REFERENCES Menu(_id))";
	final String Message_TABLE="CREATE TABLE IF NOT EXISTS Message( " + KEY_ID
			+ " INTEGER PRIMARY KEY , " + "heading TEXT, "
			+"desc text,"+"date text,"+"image text)";
	SQLiteDatabase db;

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// "('About','Contact','Website',);");

	@Override
	public void onCreate(SQLiteDatabase db) {

		// SQL statement to create book table

		// create books table
		db.execSQL(Appgeneral_TABLE);
		db.execSQL(Dashboard_TABLE);
		db.execSQL(Menu_TABLE);
		db.execSQL(Menu_props_TABLE);
		db.execSQL(Entities_TABLE);
		db.execSQL(Message_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed

		// db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_DUP_VEHICLESTOCK);
		// create fresh books table
		this.onCreate(db);
	}

	// ---------------------------------------------------------------------
	public void cleartables() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM Dashboard");
		db.execSQL("DELETE FROM Menu");
		db.execSQL("DELETE FROM Menu_props");
		db.execSQL("DELETE FROM Entities");
		db.execSQL("DELETE FROM Message");
//		onCreate(db);
	}

	public void clearDB() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DROP DATABASE " + DATABASE_NAME);

	}

	public MySQLiteHelper open() {
		db = this.getWritableDatabase();
		return this;

	}

	public boolean isOpen() {
		if (db == null)
			return false;
		else
			return true;
	}

	public void close() {
		db.close();
	}
public void insert()
{
	db.execSQL("INSERT INTO Dashboard VALUES"
			+ "(1,'http://lorempixel.com/72/72/abstract/1','Menu','menu','1');");
	db.execSQL("INSERT INTO Dashboard VALUES"
			+ "(2,'http://lorempixel.com/72/72/abstract/2','Messages','message','1');");
	db.execSQL("INSERT INTO Dashboard VALUES"
			+ "(3,'http://lorempixel.com/72/72/abstract/3','Gallery','gallery','1');");
	db.execSQL("INSERT INTO Dashboard VALUES"
			+ "(4,'http://lorempixel.com/72/72/abstract/4','Contacts','contacts','3');");
	
	
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(1,'About','0','1','menu','4');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(2,'Contact','0','2','menu','6');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(3,'Website','0','3','link','8');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(4,'Organisation','1','1','general_profile','1');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(5,'Team','1','2','contacts','3');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(6,'Sales','2','1','general_profile','3');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(7,'Marketing','2','2','general_profile','4');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(8,'http://www.google.co.in','3','0','Browser','5');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(9,'Custom HTML','0','4','html','10');");
	db.execSQL("INSERT INTO Menu VALUES"
			+ "(10,'http://indigo.thoughtripples.com/api_ref.html','9','1','html','1');");
	
	
//	db.execSQL("INSERT INTO Menu_props VALUES"
//			+ "(null,'1','width','200');");
//	db.execSQL("INSERT INTO Menu_props VALUES"
//			+ "(null,'1','height','wrap_content');");
	db.execSQL("INSERT INTO Menu_props VALUES"
			+ "(1,'1','background','ff00ffff');");
	
	
	db.execSQL("INSERT INTO Entities VALUES"
			+ "(1,'9','images','http://lorempixel.com/160/160/nature/1,http://lorempixel.com/160/160/nature/2,http://lorempixel.com/160/160/nature/3,http://lorempixel.com/160/160/nature/4,http://lorempixel.com/160/160/nature/5,http://lorempixel.com/160/160/nature/6,http://lorempixel.com/160/160/nature/7,http://lorempixel.com/160/160/nature/8,http://lorempixel.com/160/160/nature/9,http://lorempixel.com/160/160/nature/10');");
	db.execSQL("INSERT INTO Entities VALUES"
			+ "(2,'9','names','First,Second,Third,Fourth,Fifth,Sixth,Seventh,Eighth,Nineth,Tenth');");
	db.execSQL("INSERT INTO Entities VALUES"
			+ "(3,'5','images','http://lorempixel.com/68/68/fashion/1,http://lorempixel.com/68/68/fashion/2,http://lorempixel.com/68/68/fashion/3,http://lorempixel.com/68/68/fashion/4,http://lorempixel.com/68/68/fashion/5,http://lorempixel.com/68/68/fashion/6,http://lorempixel.com/68/68/fashion/7,http://lorempixel.com/68/68/fashion/8,http://lorempixel.com/68/68/fashion/9,http://lorempixel.com/68/68/fashion/10');");
	db.execSQL("INSERT INTO Entities VALUES"
			+ "(4,'5','names','Nash,Maya,Third,Fourth,Fifth,Sixth,Seventh,Eighth,Nineth,Tenth');");
	db.execSQL("INSERT INTO Entities VALUES"
			+ "(5,'5','relations','Family Head,Wife,Third,Fourth,Fifth,Sixth,Seventh,Eighth,Nineth,Tenth');");
	db.execSQL("INSERT INTO Entities VALUES"
			+ "(6,'5','phones','9846,9995,9895,8089,9946,9961,null,9381,9847,9947');");
	
	

	db.execSQL("INSERT INTO Message VALUES"
			+ "(1,'A cat','This is a sample decsription from db.','11:10am  01/02/2013','http://lorempixel.com/360/160/cats/1');");
	db.execSQL("INSERT INTO Message VALUES"
			+ "(2,'Witout image','This is a sample without an image.','10:10am  10/10/2013','null');");
	db.execSQL("INSERT INTO Message VALUES"
			+ "(3,'Heading2','This is another sample decsription from db.','11:10am  02/11/2013','http://lorempixel.com/360/160/cats/2');");
}

public Cursor getDashboard(){
	return db.query("Dashboard", null, null, null, null, null, null);
}

public ArrayList<MenuModel> goBack(int i){
	int j=0;
	Cursor c= db.query("Menu", new String[]{"parentid"}, "_id="+i, null, null, null, null);
	if(c.getCount()>0){
		c.moveToFirst();
		Log.d("nzm", "Current Parent id:"+c.getInt(0));
		Cursor c1= db.query("Menu", new String[]{"parentid"}, "_id="+c.getInt(0), null, null, null, null);
		if(c1.getCount()>0)
			{
			c1.moveToFirst();
			j=c1.getInt(0);
			}
		else return null;
	}
	return getButtons(j);
}

public ArrayList<MenuModel> getButtons(int i) {
	Cursor c=db.query("Menu", null, "parentid="+i, null, null, null, "sortorder");
	ArrayList<MenuModel> buttons=new ArrayList<MenuModel>();
	if(c.getCount()>0){
	c.moveToFirst();
	do{
		MenuModel b_model;
		String text=c.getString(1);String width,height,bg=null;
		int id=c.getInt(0);
		Log.d("Nzm", "itemid="+id);
		Cursor c1=db.query("Menu_props", null, "itemid="+id, null, null, null, null);
		if(c1!=null && c1.getCount()!=0){
			c1.moveToFirst();
				Map<String, String> props=new HashMap<String, String>();
			do{
				props.put(c1.getString(2), c1.getString(3));
			}while(c1.moveToNext());
			width=props.get("width");
			height=props.get("height");
			bg=props.get("background");
			Log.d("nzm", bg);
		}else{
			width="wrap_content";
			height="wrap_content";
			Log.d("nzm", ""+bg);
		}
		b_model=new MenuModel(width, height, text);
		b_model.setBg(bg);
		b_model.setId(c.getInt(0));
		b_model.setTarget(c.getString(4));
		b_model.setTarget_id(c.getInt(5));
		buttons.add(b_model);
	}while(c.moveToNext());
	}
	return buttons;
	
}

public String getText(int id){
	String text;
	Cursor c= db.query("Menu",new String[]{"text"}, "_id="+id, null, null, null, null);
	c.moveToFirst();
	text=c.getString(0);
	return text;
}

public Cursor getEntityContents(int id){
	Cursor c=null;
	c=db.query("Entities", null, "_id="+id, null, null, null, null);
	if(c!=null){
		c.moveToFirst();
		int menuid=c.getInt(c.getColumnIndex("menu_id"));
		c=db.query("Entities", null, "menu_id="+menuid, null, null, null, null);
	}
	return c;
}

public ArrayList<MessageModel> getMessages(){
	ArrayList<MessageModel> msgs=new ArrayList<MessageModel>();
	Cursor c=db.query("Message", null, null, null, null, null, null);
	if(c!=null){
		c.moveToFirst();
		do{
			MessageModel msg=new MessageModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
			msgs.add(msg);
		}while(c.moveToNext());
	}
	return msgs;
}

}