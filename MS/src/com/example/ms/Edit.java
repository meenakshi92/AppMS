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
import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.*;
import android.app.LoaderManager;


@SuppressLint("NewApi")
public class Edit extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int LIST_ID = 0;
	static EditText univName, appFee, deadline;
	Button pickDate, send;
	Spinner numLors, numTranscripts;
	private static final String[] PROJECTION = new String[]{_ID,DEADLINE,FEE,NO_LORS,NO_TRANSCRIPTS};
	private static final String SELECTION = null;
	
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
		setContentView(R.layout.activity_edit);
		univName = (EditText) findViewById(R.id.univ_name1);
	    appFee = (EditText) findViewById(R.id.app_fee1);
	    deadline = (EditText) findViewById(R.id.deadline1);
	    
	    pickDate = (Button) findViewById(R.id.pick_date1);
	    numLors = (Spinner) findViewById(R.id.num_lors1);
	    numTranscripts = (Spinner) findViewById(R.id.num_transcripts1);
	    
	    ArrayAdapter<CharSequence> adapterLor = ArrayAdapter.createFromResource(this, R.array.num_lors, android.R.layout.simple_spinner_item);
	    adapterLor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    numLors.setAdapter(adapterLor);
	    
	    ArrayAdapter<CharSequence> adapterTrans = ArrayAdapter.createFromResource(this, R.array.num_lors, android.R.layout.simple_spinner_item);
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
	    	ContentValues values = new ContentValues();
	    	values.put(NAME, univName.getText().toString());
	    	values.put(DEADLINE, deadline.getText().toString());
	    	values.put(FEE, Double.parseDouble(appFee.getText().toString()));
	    	values.put(NO_LORS, Integer.parseInt(numLors.getSelectedItem().toString()));
	    	values.put(NO_TRANSCRIPTS, Integer.parseInt(numTranscripts.getSelectedItem().toString()));
	    	mNewUri = getContentResolver().insert(CONTENT_URI, values);
	    	
	    	Bundle bundle = new Bundle();
			bundle.putString("UniName", mNewUri.toString());
			Intent intent = new Intent(getApplicationContext(), UniversityList.class); 
		    intent.putExtras(bundle);
		    startActivity(intent); 
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
		/*univName.setText(cursor.getString(1), TextView.BufferType.EDITABLE);
		deadline.setText(cursor.getString(2),TextView.BufferType.EDITABLE);
		appFee.setText(Double.toString(cursor.getDouble(3)),TextView.BufferType.EDITABLE);
		numLors.setPrompt(Integer.toString(cursor.getInt(4)));
		numTranscripts.setPrompt(Integer.toString(cursor.getInt(5)));
		*/
		Log.d("Test", DatabaseUtils.dumpCursorToString(cursor));
		
	}
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
