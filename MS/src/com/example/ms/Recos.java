/* 
 * Attempted the entire layoutSetting programmatically instead of XML 
 */

package com.example.ms;

import static com.example.database.Constants.RECO_EMAIL;
import static com.example.database.Constants.RECO_INIT;
import static com.example.database.Constants.RECO_NAME;
import static com.example.database.Constants.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
	private int num_lors;
	private long id;
	String SELECTION = null;
	int noUpdated;
	

	Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		num_lors = intent.getIntExtra("lor", 1);
		id = intent.getLongExtra("id", 1);
		SELECTION=" _ID = "+ Long.toString(id);
		
		LinearLayout linearLayout = new LinearLayout(this);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		linearLayout.setLayoutParams(params);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		
		for(int i = 0; i < num_lors; i++) {
			linearLayout.addView(textView(i));
			for(int j = 3*i; j < (i+1)*3; j++) {
				linearLayout.addView(editText(j));
			}
		}
		linearLayout.addView(submitButton());
		setContentView(linearLayout);
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
	            ContentValues values = new ContentValues();
	            for (int i = 0; i < editTextList.size(); i++) {
		            	editText = editTextList.get(i);
		                switch(i%3) {
		                case 0: values.put(RECO_NAME, editText.getText().toString()); 
		                		break;
		                case 1: values.put(RECO_INIT, editText.getText().toString());
		                		break;
		                case 2: values.put(RECO_EMAIL, editText.getText().toString());
		                		break;
		                }
		                if(i%3 == 2)
		                	noUpdated = getContentResolver().update(CONTENT_URI, values,SELECTION,null);
	            }
		        finish();
	        }
	   };
	  
	   private EditText editText(int id) {
	        EditText editText = new EditText(this);
	        editText.setId(Integer.valueOf(id));
	        switch(id%3){
	        case 0: editText.setHint("Professor's Name");
	        		break;
	        case 1: editText.setHint("Initials"); 
	        		int maxLength = 3;
	        		InputFilter[] FilterArray = new InputFilter[1];
	        		FilterArray[0] = new InputFilter.LengthFilter(maxLength);
	        		editText.setFilters(FilterArray);
	        		break;
	        case 2: editText.setHint("E-mail address");
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
