package com.example.ms;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;

public class Process extends Activity {
	AnimationDrawable processAnimation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process);
		// Show the Up button in the action bar.
		setupActionBar();
		 ImageView processImage = (ImageView) findViewById(R.id.process_image);
		  processImage.setBackgroundResource(R.drawable.animation);
		  processAnimation = (AnimationDrawable) processImage.getBackground();
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
		getMenuInflater().inflate(R.menu.process, menu);
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
	/*public boolean onTouchEvent(MotionEvent event) {
		  if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    processAnimation.start();
		    return true;
		  }
		  return super.onTouchEvent(event);
		}*/
	public void onWindowFocusChanged(boolean onFocus)
	{	processAnimation.start();
		
	}

}
