package com.example.ms;

import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.NAME;
import static com.example.database.Constants.TABLE_NAME;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TrackIt extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{
	
	//For TableLayout
	static final String[] PROJECTION = new String[]{_ID,NAME,NO_LORS};
	static final String SELECTION =  "((" + NAME + " NOT NULL) AND (" + NAME + " != '' ))";
	private static final int LIST_ID = 1;
	TextView name, online, aid, gre, toefl, lor, admit, finance;
	View separator;
	ScrollView viewer;
	String s;
	TableLayout tl;
	TableRow.LayoutParams textParams;
	
	//For Zoom
	Matrix matrix = new Matrix();
 	Matrix savedMatrix = new Matrix();

	
	
	private static float MIN_ZOOM = 1f;
	private static float MAX_ZOOM = 5f;

	private ScaleGestureDetector mScaleDetector;
	float[]arr=new float[9];
	static float mLastTouchX;
    static float mLastTouchY;
	public float mPosX;
	public float mPosY;
	public float mScaleFactor = 1.f;
	private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    private int mActivePointerIndex = 0;
	private int displayWidth;
	private int displayHeight;
 
    private static final float AXIS_X_MIN = -1f;
    private static final float AXIS_X_MAX = 1f;
    private static final float AXIS_Y_MIN = -1f;
    private static final float AXIS_Y_MAX = 1f;
    private RectF mCurrentViewport = new RectF(AXIS_X_MIN, AXIS_Y_MIN, AXIS_X_MAX, AXIS_Y_MAX);
    private Rect mContentRect;
    //private GestureDetector mGestureDetector;
    float getActiveX(MotionEvent ev) {
        return ev.getX(mActivePointerIndex);
    }

   
    float getActiveY(MotionEvent ev) {
        return ev.getY(mActivePointerIndex);
    }
    
    /*private void setViewportBottomLeft(float x, float y) {
        /*
         * Constrains within the scroll range. The scroll range is simply the viewport 
         * extremes (AXIS_X_MAX, etc.) minus the viewport size. For example, if the 
         * extremes were 0 and 10, and the viewport size was 2, the scroll range would 
         * be 0 to 8.
         */

      /*  float curWidth = mCurrentViewport.width();
        float curHeight = mCurrentViewport.height();
        x = Math.max(AXIS_X_MIN, Math.min(x, AXIS_X_MAX - curWidth));
        y = Math.max(AXIS_Y_MIN + curHeight, Math.min(y, AXIS_Y_MAX));

        mCurrentViewport.set(x, y - curHeight, x + curWidth, y);

        // Invalidates the View to update the display.
        ViewCompat.postInvalidateOnAnimation(viewer);
    }*/
    
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_it);
		// Show the Up button in the action bar.
		setupActionBar();
		
		mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
		/*mGestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				// TODO Auto-generated method stub
				float viewportOffsetX = distanceX * mCurrentViewport.width() 
			            / mContentRect.width();
			    float viewportOffsetY = -distanceY * mCurrentViewport.height() 
			            / mContentRect.height();
			    
			    // Updates the viewport, refreshes the display. 
			    setViewportBottomLeft(
			            mCurrentViewport.left + viewportOffsetX,
			            mCurrentViewport.bottom + viewportOffsetY);
			    
			    return true;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		}); */
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			display.getSize(size);
			displayWidth = size.x;
			displayHeight = size.y;
		}
		 else
		 {
		      
		       displayWidth = display.getWidth(); 
		       displayHeight = display.getHeight(); 
		 }

		tl = (TableLayout) findViewById(R.id.tableLayout);
		viewer = (ScrollView) findViewById(R.id.scrollview_trackit);
		
        viewer.setOnTouchListener(new OnTouchListener(){
       
			@Override
			 
			public boolean onTouch(View view, MotionEvent ev) {
				 	mScaleDetector.onTouchEvent(ev);
				    //mGestureDetector.onTouchEvent(ev);
				    final int action = ev.getAction();
				    switch (action & MotionEvent.ACTION_MASK) {
				    case MotionEvent.ACTION_DOWN: {
				        final float x = ev.getX();
				        final float y = ev.getY();
				        
				        mLastTouchX = x;
				        mLastTouchY = y;
				        mActivePointerId = ev.getPointerId(0);
				        break;
				    }
				        
				    case MotionEvent.ACTION_MOVE: {
				        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
				        final float x = ev.getX(pointerIndex);
				        final float y = ev.getY(pointerIndex);

				        // Only move if the ScaleGestureDetector isn't processing a gesture.
				        if (!mScaleDetector.isInProgress() && mScaleFactor != 1f) {
				            float dx = x - mLastTouchX;
				            float dy = y - mLastTouchY;
				            
				            mPosX += dx;
				            mPosY += dy;
				            
				            matrix.postTranslate(mPosX, mPosY);
				            
				        }

				        mLastTouchX = x;
				        mLastTouchY = y;

				        break;
				    }
				        
				    case MotionEvent.ACTION_UP: {
				        mActivePointerId = INVALID_POINTER_ID;
				        break;
				    }
				        
				    case MotionEvent.ACTION_CANCEL: {
				        mActivePointerId = INVALID_POINTER_ID;
				        break;
				    }
				    
				    case MotionEvent.ACTION_POINTER_UP: {
				        final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
				                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				        final int pointerId = ev.getPointerId(pointerIndex);
				        if (pointerId == mActivePointerId) {
				            // This was our active pointer going up. Choose a new
				            // active pointer and adjust accordingly.
				            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				            mLastTouchX = ev.getX(newPointerIndex);
				            mLastTouchY = ev.getY(newPointerIndex);
				            mActivePointerId = ev.getPointerId(newPointerIndex);
				        }
				        break;
				    }
				    }
					
						
					
					matrix.getValues(arr);
					viewer.setTranslationX(arr[Matrix.MTRANS_X]);
					viewer.setTranslationY(arr[Matrix.MTRANS_Y]);
					viewer.setScaleX(arr[Matrix.MSCALE_X]);
					viewer.setScaleY(arr[Matrix.MSCALE_Y]);
					return true;
			
			}
       });
		getLoaderManager().initLoader(LIST_ID,null, this);
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();
	        
	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
	        matrix.setScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
	        return true;
	    }
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
		
		Boolean b,c ;
		int i=1,numberOfLors;
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
	        c=preferences.getBoolean("Application_fee", false);
			online = new TextView(this);
			if(b & c)
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
			
			//setting lor
			SharedPreferences preferences1 = getApplicationContext().getSharedPreferences(s+"reco", android.content.Context.MODE_PRIVATE);
			numberOfLors=cursor.getInt(2);
			
			int countLor=0;
			boolean flag = true;
			String nameLor;
			while(countLor<numberOfLors)
			{	nameLor=Integer.toString(countLor*6);
				b=preferences1.getBoolean("reco_name"+nameLor,false);
				flag = flag & b;
				countLor++;
			}
			lor=new TextView(this);
			if(flag)
				lor.setBackgroundColor(Color.GREEN);
			else
				lor.setBackgroundColor(Color.RED);
			
			//customizing name
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


