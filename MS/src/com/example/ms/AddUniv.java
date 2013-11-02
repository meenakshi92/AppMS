package com.example.ms;

import static com.example.database.Constants.DEADLINE;
import static com.example.database.Constants.FEE;
import static com.example.database.Constants.NAME;
import static com.example.database.Constants.NO_LORS;
import static com.example.database.Constants.NO_TRANSCRIPTS;
import static com.example.database.Constants.TABLE_NAME;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AddUniv extends Activity {

	static EditText univName, appFee, deadline;
	Button pickDate, send;
	Spinner numLors, numTranscripts;
	
	//Database elements
	Uri mNewUri;
	Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME);
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_univ);
		univName = (EditText) findViewById(R.id.univ_name);
	    appFee = (EditText) findViewById(R.id.app_fee);
	    deadline = (EditText) findViewById(R.id.deadline);
	    
	    pickDate = (Button) findViewById(R.id.pick_date);
	    numLors = (Spinner) findViewById(R.id.num_lors);
	    numTranscripts = (Spinner) findViewById(R.id.num_transcripts);
	    
	    ArrayAdapter<CharSequence> adapterLor = ArrayAdapter.createFromResource(this, R.array.num_lors, android.R.layout.simple_spinner_item);
	    adapterLor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    numLors.setAdapter(adapterLor);
	    
	    ArrayAdapter<CharSequence> adapterTrans = ArrayAdapter.createFromResource(this, R.array.num_lors, android.R.layout.simple_spinner_item);
	    adapterTrans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    numTranscripts.setAdapter(adapterTrans);
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
	    	case R.id.num_lors:
		    	Spinner spinnerLor = (Spinner) findViewById(R.id.num_lors);
		    	spinnerLor.setOnItemSelectedListener(this);
		    	break;
	    	case R.id.num_transcripts:
		    	Spinner spinnerTrans = (Spinner) findViewById(R.id.num_transcripts);
		    	spinnerTrans.setOnItemSelectedListener(this);
		    	break;
	    	}
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	        // Another interface callback
	    }
	}
	   public void commitChanges(View view) {
		   try
	   		{
		           ContentValues values = new ContentValues();
		           String number="0";
		           String nullString="Null";
		          
		           String editText = univName.getText().toString();
		           if (editText.matches("")) 
		           	values.put(NAME, nullString);
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
		           mNewUri = getContentResolver().insert(CONTENT_URI, values);
		           
		           finish();
           }
           catch(NumberFormatException e){ 
        	   		Toast.makeText(this, "Please enter a valid application fee", Toast.LENGTH_LONG).show();
           }
	   
	    }
}
