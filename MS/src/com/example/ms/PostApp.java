package com.example.ms;

import static android.provider.BaseColumns._ID;
import static com.example.database.Constants.RECO_CHECK;
import static com.example.database.Constants.RECO_EMAIL;
import static com.example.database.Constants.RECO_INIT;
import static com.example.database.Constants.RECO_NAME;
import static com.example.database.Constants.TABLE_NAME;

import android.content.ContentValues;

import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class PostApp extends Fragment implements OnCheckedChangeListener, LoaderManager.LoaderCallbacks<Cursor> {
	String University_name;
	int num_lors;
	long id;
	ListView recoList;
	String[] fromColumns ={RECO_NAME, RECO_INIT, RECO_EMAIL} ;
	int[] toViews = new int[]{R.id.recoName, R.id.recoInitials, R.id.recoEmail};
	SimpleCursorAdapter mAdapter;
	
	private static final int LIST_ID = 0;
	static final String[] PROJECTION = new String[]{_ID,RECO_NAME,RECO_INIT,RECO_EMAIL};
	static String SELECTION = null;
	Uri CONTENT_URI=Uri.parse("content://" + "com.example.providers.UniversityProvider" + "/" + TABLE_NAME);
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
				        Bundle savedInstanceState) {
				        // Inflate the layout for this fragment
				 super.onCreateView(inflater, container, savedInstanceState);
		    View v = inflater.inflate(R.layout.postapp, container, false);
		    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7;
		    Button editRecos;
		
		    cb1 = (CheckBox)v.findViewById(R.id.checkBox_pa1);
		    cb1.setChecked(getFromSP("Online"));
		    cb1.setOnCheckedChangeListener(this);
		    
		    cb2 = (CheckBox)v.findViewById(R.id.checkBox_pa2);
		    cb2.setChecked(getFromSP("Gre"));
		    cb2.setOnCheckedChangeListener(this);
		    
		    cb3 = (CheckBox)v.findViewById(R.id.checkBox_pa3);
		    cb3.setChecked(getFromSP("Toefl"));
		    cb3.setOnCheckedChangeListener(this);
		    
		    cb4 = (CheckBox)v.findViewById(R.id.checkBox_pa4);
		    cb4.setChecked(getFromSP("Financial_documents"));
		    cb4.setOnCheckedChangeListener(this);
		    
		    cb5=(CheckBox)v.findViewById(R.id.checkBox_pa5);
		    cb5.setChecked(getFromSP("Application_fee"));
		    cb5.setOnCheckedChangeListener(this);
		    
		    cb6=(CheckBox)v.findViewById(R.id.checkBox_pa6);
		    cb6.setChecked(getFromSP("Financial_Aid"));
		    cb6.setOnCheckedChangeListener(this);
		    
		    cb7=(CheckBox)v.findViewById(R.id.checkBox_pa7);
		    cb7.setChecked(getFromSP("Admit"));
		    cb7.setOnCheckedChangeListener(this);
		    
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
		    
		    recoList = (ListView) v.findViewById(R.id.recoList);
		    
		    mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.recolistrow, null, fromColumns, toViews, 0);
		    
		    recoList.setAdapter(mAdapter);
			getActivity().getSupportLoaderManager().initLoader(LIST_ID, null, (LoaderCallbacks<Cursor>) this);
		    
		    return v;
		    
		
	 }
	 public void onCreate(Bundle savedInstanceState)
	 {	
		super.onCreate(savedInstanceState); 
		TaskList tl = (TaskList)getActivity();
	 	Bundle bundle = tl.sendData();
	 	University_name = bundle.getString("UniName");
	 	num_lors = Integer.parseInt(bundle.getString("lor"));
	 	id = bundle.getLong("id");
	 	SELECTION = " _ID = " + Long.toString(id);
	 }
	 private boolean getFromSP(String key){
		 SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences(University_name, android.content.Context.MODE_PRIVATE);
		 return preferences.getBoolean(key, false);
	 }
	 private void saveInSp(String key,boolean value){
		 SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences(University_name, android.content.Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor = preferences.edit();
		 editor.putBoolean(key, value);
		 editor.commit();
	 	}
	 @Override
	 public void onCheckedChanged(CompoundButton buttonView,
        boolean isChecked) {
    // TODO Auto-generated method stub
		 switch(buttonView.getId()){
		 case R.id.checkBox_pa1:
			 saveInSp("Online",isChecked);
			 break;
		 
		 case R.id.checkBox_pa2:
			 saveInSp("Gre",isChecked);
			 break;

		 case R.id.checkBox_pa3:
			 saveInSp("Toefl",isChecked);
			 break;

		 case R.id.checkBox_pa4:
			 saveInSp("Financial_documents",isChecked);
			 break;
			 
		 case R.id.checkBox_pa5:
			 saveInSp("Application_fee",isChecked);
			 break;
			 
		 case R.id.checkBox_pa6:
			 saveInSp("Financial_Aid",isChecked);
			 break;
			 
		 case R.id.checkBox_pa7:
			 saveInSp("Admit",isChecked);
			 break;
		 }

	 }
	
	protected void onListItemClicked(ListView l, View v, int position, long id) {
		ContentValues values = new ContentValues();
		CheckBox isDone = (CheckBox) v.findViewById(R.id.recoCheck);
		isDone.toggle();
		values.put(RECO_CHECK, isDone.isActivated()?1:0);
		int noUpdated = (getActivity().getContentResolver()).update(CONTENT_URI, values ,SELECTION, null);
	}
	
	@Override
	public android.support.v4.content.Loader<Cursor> onCreateLoader(int arg0,
			Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity(),CONTENT_URI, PROJECTION, SELECTION, null, null);
	}
	@Override
	public void onLoadFinished(android.support.v4.content.Loader<Cursor> arg0,
			Cursor arg1) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(arg1);
	}
	@Override
	public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}
}
	
