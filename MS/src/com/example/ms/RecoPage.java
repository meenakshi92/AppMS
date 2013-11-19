package com.example.ms;

import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.*;
import android.content.Loader;
import android.content.SharedPreferences;

import android.content.Intent;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class RecoPage extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	String University_name;
	int num_lors,i;
	
	long id;
	String s;
	TextView initial ,email;
	CheckBox name;
	LinearLayout linearLayout;
	TableLayout tl;
	View separator,separator1,separator2;
	static final String[] PROJECTION = new String[]{_ID,RECO_NAME,RECO_INIT,RECO_EMAIL};
	private static final int LIST_ID = 0, SEPARATOR = 10;
	static String SELECTION = null;
	
	Uri RECO_CONTENT_URI = Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + RECO_TABLE_NAME);
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
				        Bundle savedInstanceState) {
				        // Inflate the layout for this fragment
				 super.onCreateView(inflater, container, savedInstanceState);
		    View v = inflater.inflate(R.layout.recopage, container, false);
		    Button editRecos;
		    
		    tl = (TableLayout)v.findViewById(R.id.RecoPageTable);
		    editRecos = (Button) v.findViewById(R.id.editRecos);
		    editRecos.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), Recos.class);
					intent.putExtra("lor", num_lors);
					intent.putExtra("id",id);
					
					startActivity(intent);
				}
			});
		    
			getActivity().getLoaderManager().initLoader(LIST_ID, null, (LoaderCallbacks<Cursor>) this);
		    
		   return v;
	 }
	 public void onCreate(Bundle savedInstanceState)
	 {	
		super.onCreate(savedInstanceState); 
		
	 	Bundle bundle = ((TaskList)getActivity()).sendData();
	 	University_name = bundle.getString("UniName");
	 	num_lors = Integer.parseInt(bundle.getString("lor"));
	 	id = bundle.getLong("id");
	 	SELECTION = UNI_ID + " = " + Long.toString(id);
	 	
	 }
		
	 private boolean getFromSP(String key){
		 SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences(University_name+"reco", android.content.Context.MODE_PRIVATE);
		 return preferences.getBoolean(key, false);
	 }
	 private void saveInSp(String key,boolean value){
		 SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences(University_name+"reco", android.content.Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor = preferences.edit();
		 editor.putBoolean(key, value);
		 editor.commit();
	 	}
	 
	
	@Override
	public void onResume() {
		super.onResume();
		
		getActivity().getLoaderManager().restartLoader(LIST_ID, null, this);
	}
	
	
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity(),RECO_CONTENT_URI, PROJECTION, SELECTION, null, null);
		
	}
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		
		
		//Refresh the TableLayout
		
				
		tl.removeAllViews();
		int i=0;
		cursor.moveToPosition(-1);
		while(cursor.moveToNext())
		{	TableRow row= new TableRow(getActivity());
        	TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,25);
        	row.setLayoutParams(lp);
        
        	TableRow row1= new TableRow(getActivity());
        	row.setLayoutParams(lp);
        
        	TableRow row2= new TableRow(getActivity());
        	row.setLayoutParams(lp);
			
			final String k=Integer.toString(i);
			s = cursor.getString(1);
			name = new CheckBox(getActivity());
			name.setText(s);
			name.setTextSize(20);
			name.setChecked(getFromSP("reco_name"+k));
			initial=new TextView(getActivity());
			initial.setText(cursor.getString(2));
			initial.setTextSize(15);
			email=new TextView(getActivity());
			email.setText(cursor.getString(3));
			email.setTextSize(14);
			
			name.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    		        // TODO Auto-generated method stub
    					 saveInSp("reco_name"+k,isChecked);
    		    }
			});

			row.addView(name);
			row1.addView(initial);
			row2.addView(email);
			
			separator1 = new View(getActivity());
			separator1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
			separator1.setBackgroundColor(Color.WHITE);
			separator1.setId(SEPARATOR);
			
			separator2 = new View(getActivity());
			separator2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
			separator2.setBackgroundColor(Color.WHITE);
			separator2.setId(SEPARATOR);
			
			tl.addView(row,i);
			tl.addView(separator1,i+1);
			tl.addView(row1,i+2);
			tl.addView(separator2,i+3);
			tl.addView(row2,i+4);
			
			separator = new View(getActivity());
			separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
			separator.setBackgroundColor(Color.BLACK);
			separator.setId(SEPARATOR);
			tl.addView(separator,i+5);
			i=i+6;
			
			
		}	
		
		cursor.close();
	}
}

	
