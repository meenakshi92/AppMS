package com.example.database;

import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.TABLE_NAME;
import static com.example.database.Constants.NAME;
import static com.example.database.Constants.FEE;
import static com.example.database.Constants.NO_LORS;
import static com.example.database.Constants.DEADLINE;
import static com.example.database.Constants.NO_TRANSCRIPTS;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Data extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="MS.db";
	private static final int DATABASE_VERSION=1;
	private final static String SQL_CREATE_ENTRIES= "CREATE TABLE "+TABLE_NAME+
													"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
													+NAME+" TEXT NOT NULL,"
													+DEADLINE+" DATE,"
													+FEE+" DOUBLE,"
													+NO_LORS+" INTEGER,"
													+NO_TRANSCRIPTS+" INTEGER);";
	private final static String SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS "+TABLE_NAME;
	public Data(Context ctx)
	{	super(ctx, DATABASE_NAME,null,DATABASE_VERSION);
	
	}
	public void onCreate(SQLiteDatabase db)
	{	db.execSQL(SQL_CREATE_ENTRIES);
		
	}
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
	{	db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
		
	}

}
