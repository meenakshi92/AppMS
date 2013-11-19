package com.example.ms;

import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.NAME;
import static com.example.database.Constants.TABLE_NAME;
import android.net.Uri;
import android.os.Bundle;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.app.LoaderManager;

public class TrackIt extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{
	
	
	static final String[] PROJECTION = new String[]{_ID,NAME};
	static final String SELECTION =  "((" + NAME + " NOT NULL) AND (" + NAME + " != '' ))";
	private static final int LIST_ID = 0;
	TextView name,online,aid,gre,toefl,lor,admit,finance;
	View separator;
	ScrollView viewer;
	String s;
	TableLayout tl;
	TableRow.LayoutParams textParams;
	Matrix matrix = new Matrix();
 	Matrix savedMatrix = new Matrix();

	// We can be in one of these 3 states
	final int NONE = 0;
	final int DRAG = 1;
	final int ZOOM = 2;
	int mode = NONE;
	
	PointF start=new PointF();
	PointF mid=new PointF();
	float oldDist=1f;
	float[]arr=new float[9];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_it);
		// Show the Up button in the action bar.
		setupActionBar();
		
		tl = (TableLayout) findViewById(R.id.tableLayout);
		viewer = (ScrollView) findViewById(R.id.scrollview_trackit);
        viewer.setOnTouchListener(new OnTouchListener(){
       
			@Override
			 
			public boolean onTouch(View view, MotionEvent event) {
				int maskedAction = event.getActionMasked();
				
				switch (maskedAction) {
					case MotionEvent.ACTION_DOWN:
						savedMatrix.set(matrix);
						start.set(event.getX(), event.getY());
						mode = DRAG;
						break;
				
					case MotionEvent.ACTION_UP:
					
					case MotionEvent.ACTION_POINTER_UP:
						mode = NONE;
						break;
				
					case MotionEvent.ACTION_POINTER_DOWN:
						oldDist = spacing(event);
						if (oldDist > 10f) {
						savedMatrix.set(matrix);
						midPoint(mid, event);
						mode = ZOOM;
						
						}
					case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						if(viewer.getScaleX()==1 && viewer.getScaleY()==1){
							mode=NONE;
						}
						else
						{
						matrix.set(savedMatrix);
						matrix.postTranslate(event.getX() - start.x,
								event.getY() - start.y);
						}
						
					}
					else if (mode == ZOOM) {
						float newDist = spacing(event);
						if(newDist-oldDist<0)
							if(viewer.getScaleX()<=1 ||viewer.getScaleY()<=1)
							{mode=NONE;
							break;}
						if (newDist > 10f) {
						matrix.set(savedMatrix);
						float scale = newDist / oldDist;
						matrix.postScale(scale, scale, mid.x, mid.y);
						}
						}
					break;
					}
				
			    
					matrix.getValues(arr);
					viewer.setTranslationX(arr[Matrix.MTRANS_X]);
					viewer.setTranslationY(arr[Matrix.MTRANS_Y]);
					viewer.setScaleX(arr[Matrix.MSCALE_X]);
					viewer.setScaleY(arr[Matrix.MSCALE_Y]);
				
				
					return true;
			
			}
			private float spacing(MotionEvent event) {
				float x = event.getX(0) - event.getX(1);
				float y = event.getY(0) - event.getY(1);
				return (float)Math.sqrt(x * x + y * y);
				}
			
			private void midPoint(PointF point, MotionEvent event) {
				float x = event.getX(0) + event.getX(1);
				float y = event.getY(0) + event.getY(1);
				point.set(x / 2, y / 2);
				}
        	
        });
		getLoaderManager().initLoader(LIST_ID,null, this);
	}
	

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track_it, menu);
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
		
		Boolean b ;
		Integer i=1;
		cursor.moveToPosition(-1);
		while(cursor.moveToNext())
		{	
			s = cursor.getString(1);
			name = new TextView(this);
			name.setBackgroundColor(Color.WHITE);
			name.setText(s);
			SharedPreferences preferences = getApplicationContext().getSharedPreferences(s, android.content.Context.MODE_PRIVATE);
			TableRow row= new TableRow(this);
	        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,25);
	        row.setLayoutParams(lp);
	        
	        textParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
	                TableRow.LayoutParams.WRAP_CONTENT);
	        textParams.setMargins(1,0,1,0);
	        
	        
	        //Shared preference value check
	        b=preferences.getBoolean("Online",false);
			online = new TextView(this);
			if(b.equals(true))
				online.setBackgroundColor(Color.GREEN);
			else
				online.setBackgroundColor(Color.RED);
			
			b=preferences.getBoolean("Financial_Documents", false);
			aid=new TextView(this);
			if(b.equals(true))
				aid.setBackgroundColor(Color.GREEN);
			else
				aid.setBackgroundColor(Color.RED);
		
			
			b=preferences.getBoolean("Gre",false);
			gre=new TextView(this);
			if(b.equals(true))
				gre.setBackgroundColor(Color.GREEN);
			else
				gre.setBackgroundColor(Color.RED);
			
			b=preferences.getBoolean("Toefl",false);
			toefl=new TextView(this);
			if(b.equals(true))
				toefl.setBackgroundColor(Color.GREEN);
			else
				toefl.setBackgroundColor(Color.RED);
			
			b=preferences.getBoolean("lors",false);
			lor=new TextView(this);
			if(b.equals(true))
				lor.setBackgroundColor(Color.GREEN);
			else
				lor.setBackgroundColor(Color.RED);
			
			b=preferences.getBoolean("Admit",false);
			admit=new TextView(this);
			if(b.equals(true))
				admit.setBackgroundColor(Color.GREEN);
			else
				admit.setBackgroundColor(Color.RED);
			
			b=preferences.getBoolean("Financial_documents",false);
			finance=new TextView(this);
			if(b.equals(true))
				finance.setBackgroundColor(Color.GREEN);
			else
				finance.setBackgroundColor(Color.RED);
			
			
			name.setMaxLines(1);
			name.setClickable(true);
		
			name.setMaxWidth(17);
			name.setMaxLines(1);
			
			row.addView(name,textParams);
			row.addView(online,textParams);
			row.addView(aid,textParams);
			row.addView(gre,textParams);
			row.addView(toefl,textParams);
			row.addView(lor,textParams);
			row.addView(admit,textParams);
			
			
			name.setOnClickListener(new View.OnClickListener() {

			    @Override
			    public void onClick(View view) {
			    	TextView tv=new TextView(getApplicationContext());
			    	tv=(TextView)view;
			    	Intent intent=new Intent(TrackIt.this,Name.class);
					intent.putExtra("name", tv.getText());
					startActivity(intent);

			    }
			});
			
			
			tl.addView(row,i);
			separator = new View(getApplicationContext());
			separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
			separator.setBackgroundColor(Color.BLACK);
			tl.addView(separator,i+1);
			i=i+2;
			}
			
		}
	
				
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
