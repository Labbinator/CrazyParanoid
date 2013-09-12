package se.kronosoft.crazyparanoidserver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperTool extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "CrazyParanoid";
	private static final String GPS_TABLE = "GpsPossitions";

	public DBHelperTool(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase dataB) {

		dataB.execSQL("create table " + GPS_TABLE +
		"(_id integer primary key autoincrement, time text, pos text);");
	}

	public void onUpgrade(SQLiteDatabase dataB, int oldV, int newV) {
	}

	public void insertGpsPos(String pos, String time) {
		ContentValues cv = new ContentValues();
		
		Log.e("insertGpsPos", pos);
		
		cv.put("time", time);
		cv.put("pos", pos);
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(GPS_TABLE, null, cv);
		db.close();
		
	}
	
	public Cursor getGpsPos(){		
		Cursor result = getReadableDatabase().rawQuery(
				"SELECT * FROM " + GPS_TABLE + " ORDER BY time DESC", null);
	
		return result;
	}
}
