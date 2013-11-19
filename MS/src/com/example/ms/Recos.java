/* 
 * Attempted the entire layoutSetting programmatically instead of XML 
 */

package com.example.ms;

import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.RECO_EMAIL;
import static com.example.database.Constants.RECO_INIT;
import static com.example.database.Constants.RECO_NAME;
import static com.example.database.Constants.RECO_TABLE_NAME;
import static com.example.database.Constants.UNI_ID;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Recos extends Activity {

	private List<EditText> editTextList = new ArrayList<EditText>();
	private List<Boolean> isNew = new ArrayList<Boolean>();
	private int num_lors;
	private long id;
	String SELECTION = null, SELEC = null;
	String[] PROJECTION = {_ID, RECO_NAME, RECO_INIT, RECO_EMAIL};
	Uri mNewUri;

	ArrayList<Integer> _IDs = new ArrayList<Integer>();
	
	LinearLayout linearLayout;
	
	Uri RECO_CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + RECO_TABLE_NAME);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recos);
		Intent intent = getIntent();
		num_lors = intent.getIntExtra("lor", 1);
		id = intent.getLongExtra("id", 1);
		SELECTION= UNI_ID + " = "+ Long.toString(id);
		for(int i = 0; i < num_lors; i++) isNew.add(true);
		
		Cursor cursor = getContentResolver().query(RECO_CONTENT_URI, PROJECTION, SELECTION, null, null);
		cursor.moveToPosition(-1);
		while(cursor.moveToNext()) {
			_IDs.add(cursor.getInt(0));
		}
		
		linearLayout = (LinearLayout)findViewById(R.id.recosLinear);
		
		for(int i = 0; i < num_lors; i++) {
			linearLayout.addView(textView(i));
			
			for(int j = 3*i; j < (i+1)*3; j++) {
				if(cursor.moveToPosition(i)){
					linearLayout.addView(editText(j,cursor.getString(j%3+1)));
					isNew.set(i, false);
				}
				else linearLayout.addView(editText(j,null));
			}
		}
		linearLayout.addView(submitButton());
		
		cursor.close();
	}
	
		private Button submitButton() {
	        Button button = new Button(this);
	        button.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
	        button.setText("Submit");
	        button.setOnClickListener(submitListener);
	        return button;
	    }
		private View.OnClickListener submitListener = new View.OnClickListener() {
	       
		  public void onClick(View view) {
	            
			  	EditText editText;
			  	boolean flag = false;
			  	ContentValues values = new ContentValues();
            	values.put(UNI_ID, id);
	            
	            for (int i = 0; i < editTextList.size(); i++) {
		            	editText = editTextList.get(i);
		            	values.put(UNI_ID, id);
		                switch(i%3) {
		                case 0: if(!editText.getText().toString().equals("")){
		                			values.put(RECO_NAME, editText.getText().toString());
		                			flag = true; 
		                		}
		                		else values.put(RECO_NAME, "null");
		                		break;
		                case 1: if(!editText.getText().toString().equals("")){
		                			values.put(RECO_INIT, editText.getText().toString());
		                			flag = true;
		                		}
		                		else values.put(RECO_INIT, "null");
		                		break;
		                case 2: if(!editText.getText().toString().equals("")){
		                			values.put(RECO_EMAIL, editText.getText().toString());
		                			flag = true;
		                		}
		                		else values.put(RECO_EMAIL, "null");
		                		break;
		                }
		                if(i%3 == 2){
		                	if(i/3 < _IDs.size())
		                		SELEC = SELECTION + " AND " + _ID + " = " + _IDs.get(i/3);
			                if(flag)	{
			                	flag = false;
			                	if(isNew.get(i/3))
			                		mNewUri = getContentResolver().insert(RECO_CONTENT_URI, values);
			                	else
			                		getContentResolver().update(RECO_CONTENT_URI, values, SELEC, null);
			                }
			                else if(i/3 < _IDs.size()) {
			                	getContentResolver().delete(RECO_CONTENT_URI, SELEC, null);
			                }
		
			                values.clear();
		                }
	            }
		        finish();
	        }
		};
	  
		private EditText editText(int id, String value) {
	        EditText editText = new EditText(this);
	        editText.setId(Integer.valueOf(id));
	        switch(id%3){
	        case 0: if(value == null) editText.setHint("Professor's Name");
	        		else editText.setText(value); 
	        		break;
	        case 1: if(value == null) {
	        			editText.setHint("Initials"); 
		        		int maxLength = 3;
		        		InputFilter[] FilterArray = new InputFilter[1];
		        		FilterArray[0] = new InputFilter.LengthFilter(maxLength);
		        		editText.setFilters(FilterArray);
	        		}
					else editText.setText(value);
	        		break;
	        case 2: if(value == null) editText.setHint("E-mail address");
					else editText.setText(value);
	        		break;
	        }
	        editTextList.add(editText);
	        return editText;
	  }
   
	  private TextView textView(int id) {
			 TextView textView = new TextView(this);
		     textView.setText("Recommender #" + Integer.toString(id+1) + "'s Details");
			 return textView;
	  }
}
