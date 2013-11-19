package com.example.providers;

import com.example.database.Data;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import static com.example.database.Constants.*;

public class UniversityProvider extends ContentProvider {
	
	Data data;
	
	Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME);
	Uri RECO_CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + RECO_TABLE_NAME);
	

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		if(uri.equals(Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME))){
			SQLiteDatabase db = data.getWritableDatabase();
			int delCount = 0;
			delCount = db.delete(TABLE_NAME, selection, selectionArgs);
			
			   if (delCount > 0 ) {
				   getContext().getContentResolver().notifyChange(uri, null);
				   }
			   return delCount;
		}
		else
		{
			SQLiteDatabase db = data.getWritableDatabase();
			int delCount = 0;
			delCount = db.delete(RECO_TABLE_NAME, selection, selectionArgs);
			
			   if (delCount > 0 ) {
				   getContext().getContentResolver().notifyChange(uri, null);
				   }
			   return delCount;
		}
	
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		
		if(uri.equals(Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME))){
			SQLiteDatabase db = data.getWritableDatabase();
			long id = db.insert(TABLE_NAME,null,values);
			getContext().getContentResolver().notifyChange(uri, null);
			Log.d("Test", "Added to " + TABLE_NAME);
	       
			return Uri.parse(TABLE_NAME + "/" + id);
		}
		else {
			SQLiteDatabase db = data.getWritableDatabase();
			long id = db.insert(RECO_TABLE_NAME,null,values);
			getContext().getContentResolver().notifyChange(uri, null);
			Log.d("Test", "Added to " + RECO_TABLE_NAME);
	       
			return Uri.parse(RECO_TABLE_NAME + "/" + id);
		}
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		data=new Data(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String arg4) {
		// TODO Auto-generated method stub
		if(uri.equals(Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME))) {
			SQLiteDatabase db = data.getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
			return cursor;
		}
		else{
			SQLiteDatabase db = data.getReadableDatabase();
			Cursor cursor = db.query(RECO_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
			return cursor;
		}
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int noRowsUpdated = 0;
		if(uri.equals(Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME))) {
			SQLiteDatabase db = data.getWritableDatabase();
			noRowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);
			Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
			Log.d("InsideContentProvUpdate", DatabaseUtils.dumpCursorToString(cursor));
			return noRowsUpdated;
		}
		else {
			SQLiteDatabase db = data.getWritableDatabase();
			noRowsUpdated = db.update(RECO_TABLE_NAME, values, selection, selectionArgs);
			Cursor cursor = db.query(RECO_TABLE_NAME, null, null, null, null, null, null);
			Log.d("InsideContentProvUpdate", DatabaseUtils.dumpCursorToString(cursor));
			return noRowsUpdated;
		}
		
	}
	

}
