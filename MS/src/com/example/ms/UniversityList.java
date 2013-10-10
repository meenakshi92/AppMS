package com.example.ms;

import java.util.ArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import android.app.ListActivity;	
import android.app.LoaderManager;
import android.view.View;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
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
		private MultiChoiceModeListener multichoice;
		private ActionMode mActionMode;
		Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME);
	

	@SuppressLint("ResourceAsColor")
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
		
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		multichoice=new MultiChoiceModeListener(){
			private int selCount = 0;
			private int j = 0;
			String SELEC = "";
	        ArrayList<Long> idList = new ArrayList<Long>();
	        Long id1;

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
                case R.id.delete:
                	
                	for(j = 0; j<idList.size(); j++) {
                		if(j == 0)
                			SELEC = " _ID = " + Long.toString(idList.get(j));
                		else
                			SELEC += " OR _ID = " + Long.toString(idList.get(j));
                	}

                	Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME);
                	
                	int rows = getContentResolver().delete(CONTENT_URI,SELEC,null);
                	if(rows>1)
                	Toast.makeText(getBaseContext(), Integer.toString(rows)+" universities deleted ", Toast.LENGTH_SHORT).show();
                	else
                		Toast.makeText(getBaseContext(), Integer.toString(rows)+" university deleted ", Toast.LENGTH_SHORT).show();
                	getLoaderManager().restartLoader(LIST_ID, null, UniversityList.this);
                	mode.finish();
                    return true;
                case R.id.edit:
                    Intent i=new Intent(getApplicationContext(),Edit.class);
                    SELEC = " _ID = " + Long.toString(idList.get(0));
                    Cursor cursor = getContentResolver().query(Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME), PROJECTION, SELEC, null, null);
                    cursor.moveToFirst();
                    i.putExtra("id", cursor.getLong(0));
                    startActivity(i);
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
				
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				  MenuInflater inflater = mode.getMenuInflater();
		            inflater.inflate(R.menu.context_menu, menu);
		            return true;
				
			}

			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				// TODO Auto-generated method stub
				selCount = 0;
	            idList.clear();
				listview.setBackgroundColor(Color.WHITE);
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				if (selCount == 1){
				       MenuItem item = menu.findItem(R.id.edit);
				       item.setVisible(true);
				       MenuItem item1 = menu.findItem(R.id.delete);
				       item1.setVisible(true);
				       return true;
				   } else {
				       MenuItem item = menu.findItem(R.id.delete);
				       item.setVisible(true);
				       MenuItem item1 = menu.findItem(R.id.edit);
				       item1.setVisible(false);
				       return true;
				   }
				
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				// TODO Auto-generated method stub
				if (checked) {
			         selCount++;
			         idList.add(id);
			     } else {
			         selCount--;
			         idList.remove(id);
			     }
				id1=id;
			     mode.setTitle(selCount + " selected");

			     mode.invalidate();
				
			}
			};
		listview.setMultiChoiceModeListener(multichoice);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			View row;
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(row != null){
					row.setBackgroundResource(R.color.orange);
				}
				row = view;
				view.setBackgroundColor(Color.BLUE);
				 if (mActionMode != null) {
			            return false;
			        }

			        // Start the CAB using the ActionMode.Callback defined above
			        mActionMode = UniversityList.this.startActionMode(multichoice);
			        view.setSelected(true);
			        return true;	
			}
	});

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
		bundle.putLong("id", id);
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
