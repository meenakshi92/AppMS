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
	

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = data.getWritableDatabase();
		int delCount = 0;
		delCount = db.delete( TABLE_NAME, selection, selectionArgs);
		
		   if (delCount > 0 ) {
			   getContext().getContentResolver().notifyChange(uri, null);
			   }
		   return delCount;


	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		String SELECTION =  "((" + NAME + " NOT NULL) AND (" + NAME + " != '' ))";
		String[] PROJECTION = new String[]{_ID,NAME,DEADLINE,FEE,NO_LORS,NO_TRANSCRIPTS};
		long id=0; String TAG = "Test";
		SQLiteDatabase db = data.getWritableDatabase();
		id=db.insert(TABLE_NAME,null,values);
		getContext().getContentResolver().notifyChange(uri, null);
		Log.d(TAG, "Added to " + TABLE_NAME);
		Cursor cursor = db.query(TABLE_NAME, PROJECTION, SELECTION, null, null, null, null);
		cursor.moveToFirst();
		while(cursor.moveToNext())
			for(int i = 0; i<cursor.getColumnCount(); i++)
				Log.d(TAG, cursor.getString(i));
		return Uri.parse(TABLE_NAME + "/" + id);		
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		data=new Data(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] projection, String selection, String[] selectionArgs,
			String arg4) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = data.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
		Log.d("InsideContentProv", DatabaseUtils.dumpCursorToString(cursor));
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
