package com.example.ms;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class Name extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name);
		// Show the Up button in the action bar.
		TextView name=new TextView(this);
		name=(TextView)findViewById(R.id.name_of_university);
		 Intent i = getIntent();
	     name.setText( i.getStringExtra("name"));
		
	}
}
