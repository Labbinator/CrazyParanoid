package se.kronosoft.crazyparanoidserver;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperTool extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "CrazyParanoid";
	private static final String GPS_TABLE = "GpsPossitions";
	private static final String LOGS_TABLE = "LogsTable";

	public DBHelperTool(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase dataB) {

		// dataB.execSQL("create table " + TABLE_NAME +
		// "(_id integer primary key autoincrement, title text, rating integer, descr text);");

		// ContentValues cv = new ContentValues();
	}

	public void onUpgrade(SQLiteDatabase dataB, int oldV, int newV) {
	}

	public void insertGpsPos(String pos) {

	}
	
	public /*HASHMAP?*/ void getGpsPos(){
		
	}

	public void insertLog(String logString){
		
	}
	public /*HASHMAP?*/ void getLogs(){
		
	}
}
