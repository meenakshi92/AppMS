package com.example.ms;

import android.net.Uri;
import android.os.Bundle;

import android.app.ListActivity;	
import android.app.LoaderManager;
import android.view.View;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ListView;
import android.widget.TextView;
import static com.example.database.Constants.*;
import com.example.database.*;

public class UniversityList extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{
		private static final int LIST_ID = 0;
		static final String[] PROJECTION = new String[]{_ID,NAME};
		static final String SELECTION =  "((" + NAME + " NOT NULL) AND (" + NAME + " != '' ))";
		ListView listview;
		String[] fromColumns ={NAME} ;
		int[] toViews = new int[]{R.id.name_entry};
		SimpleCursorAdapter mAdapter;
		Data data;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_university_list);
		// Show the Up button in the action bar.
		setupActionBar();
		mAdapter = new SimpleCursorAdapter(this,R.layout.list_university_list, null,
                fromColumns, toViews,0);
        setListAdapter(mAdapter);
        listview = getListView();
        listview.setAdapter(mAdapter);
		getLoaderManager().initLoader(LIST_ID, null, this);

	}
	
	public void callAddUniv(View arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent(getApplicationContext(), AddUniv.class);
		startActivity(i);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME);

		return new CursorLoader(this,CONTENT_URI, PROJECTION, SELECTION, null, null);
	
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		//Log.d("Test", DatabaseUtils.dumpCursorToString(cursor));
		//cursor.moveToFirst();
		//while(cursor.moveToNext())
		//	Log.d("Test", cursor.getString(0));
		mAdapter.swapCursor(cursor);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
		
		
	}
	protected void onListItemClick(ListView l, View v, int position, long id)
	{	
		//String text = (String)((Cursor)l.getItemAtPosition(position)).toString();
		String text = ((TextView)v.findViewById(R.id.name_entry)).getText().toString();
		Bundle bundle = new Bundle();
		bundle.putString("UniName", text);
		Intent intent = new Intent(getApplicationContext(), TaskList.class); 
	    intent.putExtras(bundle);
	    startActivity(intent); 
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}

}
