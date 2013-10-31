 package com.example.ms;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View uniButton=findViewById(R.id.university_list);
		uniButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext() ,UniversityList.class);
				startActivity(i);
			}
		});
		View processButton=findViewById(R.id.process);
		processButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext() ,Process.class);
				startActivity(i);
			}
		});
		View aboutButton=findViewById(R.id.about);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new AboutDialog();
				newFragment.show(getFragmentManager(), "OK");
				// TODO Auto-generated method stub
				
			}
		});
		View trackerButton=findViewById(R.id.tracker);
		trackerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),TrackIt.class);
				startActivity(i);
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		public void onBackPressed() {
			
			Intent i= new Intent("package.homescreenactivity");
		    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
		    startActivity(i);
		    finish(); 
		}
}

class AboutDialog extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(R.string.about_title)
			   .setMessage(R.string.about_message)
			   .setPositiveButton(R.string.ok_message, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User pressed OK!
			}
		});
		
		AlertDialog dialog = builder.create();
		return dialog;
	}
}