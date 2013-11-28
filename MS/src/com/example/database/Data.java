package com.example.database;

import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Data extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="MS.db";
	private static final int DATABASE_VERSION=1;
	private final static String SQL_CREATE_ENTRIES= "CREATE TABLE "+TABLE_NAME+
													"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
													+NAME+" TEXT NOT NULL,"
													+DEADLINE+" STRING,"
													+FEE+" DOUBLE,"
													+NO_LORS+" INTEGER,"
													+NO_TRANSCRIPTS+" INTEGER,"
													+USERNAME+" STRING,"
													+PASSWORD+" STRING);";
	private final static String SQL_RECO_CREATE_ENTRIES= "CREATE TABLE "+RECO_TABLE_NAME+
			"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+UNI_ID+" INTEGER, "
			+RECO_NAME + " STRING, " 
			+RECO_PHONE + " REAL, " 
			+RECO_EMAIL + " STRING, "
			+RECO_CHECK + " BOOLEAN DEFAULT 0" + ");";
	private final static String SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS "+TABLE_NAME;
	private final static String SQL_RECO_DELETE_ENTRIES="DROP TABLE IF EXISTS "+TABLE_NAME;
	public Data(Context ctx)
	{	super(ctx, DATABASE_NAME,null,DATABASE_VERSION);
	
	}
	public void onCreate(SQLiteDatabase db)
	{	db.execSQL(SQL_CREATE_ENTRIES);
		db.execSQL(SQL_RECO_CREATE_ENTRIES);
	}
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
	{	db.execSQL(SQL_DELETE_ENTRIES);
		db.execSQL(SQL_RECO_DELETE_ENTRIES);
		onCreate(db);
		
	}

}
