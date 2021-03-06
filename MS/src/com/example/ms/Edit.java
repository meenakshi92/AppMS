package com.example.ms;

import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.*;
import android.app.LoaderManager;


@SuppressLint("NewApi")
public class Edit extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int LIST_ID = 0;
	static EditText univName, appFee, deadline,username,password;
	Button pickDate, send;
	Spinner numLors, numTranscripts;
	ArrayAdapter<CharSequence> adapterLor,adapterTrans;
	private static final String[] PROJECTION = new String[]{_ID, NAME, DEADLINE,FEE,NO_LORS,NO_TRANSCRIPTS,USERNAME,PASSWORD};
	private static String SELECTION = null;
	private long id;
	//Database elements
	Uri mNewUri;
	Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + "University");
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		Intent i = getIntent();
        id = i.getLongExtra("id",1);
        SELECTION=" _ID = "+ Long.toString(id);
		univName = (EditText) findViewById(R.id.univ_name1);
	    appFee = (EditText) findViewById(R.id.app_fee1);
	    deadline = (EditText) findViewById(R.id.deadline1);
	    username=(EditText) findViewById(R.id.univUsername);
	    password=(EditText)findViewById(R.id.univPasswd);
	    
	    
	    pickDate = (Button) findViewById(R.id.pick_date1);
	    numLors = (Spinner) findViewById(R.id.num_lors1);
	    numTranscripts = (Spinner) findViewById(R.id.num_transcripts1);
	    
	    adapterLor= ArrayAdapter.createFromResource(this, R.array.num_lors, android.R.layout.simple_spinner_item);
	    adapterLor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    numLors.setAdapter(adapterLor);
	    
	    
	    adapterTrans = ArrayAdapter.createFromResource(this, R.array.num_lors, android.R.layout.simple_spinner_item);
	    adapterTrans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    numTranscripts.setAdapter(adapterTrans);
	    getLoaderManager().initLoader(LIST_ID,null,this);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_univ, menu);
		return true;
	}
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user

			deadline.setText(new StringBuilder() 
            .append(day)
            .append("-").append(month + 1)
            .append("-").append(year).append(""));
			
		}
	}
	
	@SuppressLint("ValidFragment")
	public class SpinnerActivity extends Fragment implements OnItemSelectedListener {
	 
	    
	    public void onItemSelected(AdapterView<?> parent, View view, 
	            int pos, long id) {
	        // An item was selected. You can retrieve the selected item using
	        // parent.getItemAtPosition(pos)
	    	switch(((Spinner)view).getId()) {
	    	case R.id.num_lors1:
		    	Spinner spinnerLor = (Spinner) findViewById(R.id.num_lors1);
		    	spinnerLor.setOnItemSelectedListener(this);
		    	break;
	    	case R.id.num_transcripts1:
		    	Spinner spinnerTrans = (Spinner) findViewById(R.id.num_transcripts1);
		    	spinnerTrans.setOnItemSelectedListener(this);
		    	break;
	    	}
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	        // Another interface callback
	    }
	}
	   public void commitChanges(View view) {
		   try{
		    	for(int i=0;i<1;i++)
		    	{
			    String number="0";
	            String nullString="Not specified";
	            ContentValues values = new ContentValues();
	            String editText = univName.getText().toString();
	            if (editText.matches("")) 
	            {	//values.put(NAME, nullString);
	            	Toast.makeText(this, "Please enter the name of the university", Toast.LENGTH_LONG).show();
	            	break;
	            }
	            else
		    	values.put(NAME, editText);
	            
	            editText=appFee.getText().toString();
	            if(editText.matches(""))
	            	values.put(FEE, Double.valueOf(number).doubleValue());
	            else
	                values.put(FEE, Double.parseDouble(appFee.getText().toString()));
	           
	            editText = deadline.getText().toString();
	            if (editText.matches("")) 
	            	values.put(DEADLINE, nullString);
	            else
		    	values.put(DEADLINE, editText);
	            
		    	values.put(NO_LORS, Integer.parseInt(numLors.getSelectedItem().toString()));
		    	values.put(NO_TRANSCRIPTS, Integer.parseInt(numTranscripts.getSelectedItem().toString()));
		    	
		    	 editText=username.getText().toString();
		            if (editText.matches("")) 
		            	values.put(USERNAME, nullString);
		            else
			    	values.put(USERNAME, editText);
		            
		            editText=password.getText().toString();
		            if (editText.matches("")) 
		            	values.put(PASSWORD, nullString);
		            else
			    	values.put(PASSWORD, editText);
		    	
		    	getContentResolver().update(CONTENT_URI, values,SELECTION,null);
		    	
		    	finish(); 
		    	}
			    }
			    catch(NumberFormatException e){ 
	            	Toast.makeText(this, "Please enter a valid application fee", Toast.LENGTH_LONG).show();
	            } 
	    }
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/University");
		return new CursorLoader(this ,CONTENT_URI, PROJECTION, SELECTION, null,null);
		
	}
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		cursor.moveToFirst();
		String str=cursor.getString(1);
		univName.setText(str, TextView.BufferType.EDITABLE);
		str=cursor.getString(2);
		if(str.equals("Not specified"))
			deadline.setHint("Set Date");
		else
			deadline.setText(str,TextView.BufferType.EDITABLE);
		if(cursor.getDouble(3)==0.0)
			appFee.setHint("Application Fee");
		else
			appFee.setText(Double.toString(cursor.getDouble(3)),TextView.BufferType.EDITABLE);
		String s=Integer.toString(cursor.getInt(4));
		numLors.setSelection(adapterLor.getPosition(s));
		s=Integer.toString(cursor.getInt(5));
		numTranscripts.setSelection(adapterTrans.getPosition(s));
		str=cursor.getString(6);
		if(str.equals("Not specified"))
			username.setHint("Enter username of application");
		else
			username.setText(str,TextView.BufferType.EDITABLE);
		str=cursor.getString(7);
		if(str.equals("Not specified"))
			password.setHint("Enter password");
		else
			password.setText(str,TextView.BufferType.EDITABLE);
		Log.d("Test", DatabaseUtils.dumpCursorToString(cursor));
		cursor.close();
	}
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
