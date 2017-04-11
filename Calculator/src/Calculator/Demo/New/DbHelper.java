package Calculator.Demo.New;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.database.DatabaseUtils;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
public class DbHelper extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME = "CalcDb.db";
	public static final String TABLE_NAME ="Value";
	public static final String VALLUES_COLLUMN_NAME ="Result";
	
	public DbHelper (Context context)
	{
		super (context,DATABASE_NAME,null,1);
	}
	
	//Adding Data on Database
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String str ="create table "+TABLE_NAME +" ("+VALLUES_COLLUMN_NAME+ " text" +")";
		db.execSQL(str);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// TODO Auto-generated method stub
		db.execSQL ("DROP TABLE IF EXISTS "+TABLE_NAME+"");
		onCreate(db);
	}
	
	//Inserting Updated/New_Added value on Database 
	public boolean insertResult (String val)
	{
		SQLiteDatabase db =this.getWritableDatabase();
		ContentValues contentValues = new ContentValues ();
		contentValues.put(VALLUES_COLLUMN_NAME, val);
		db.insert(TABLE_NAME, null, contentValues);
		return true;
	}
	
	//Getting Data from Database based on MR Button
	public Cursor getData ()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from "+TABLE_NAME+"", null);
		return res;
	}
}
