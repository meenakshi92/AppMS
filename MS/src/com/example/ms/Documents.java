package com.example.ms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Documents extends Fragment implements OnCheckedChangeListener {
	String University_name;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		 super.onCreateView(inflater, container, savedInstanceState);
		        // Inflate the layout for this fragment
    View v= inflater.inflate(R.layout.documents, container, false);
    CheckBox cb2,cb3,cb4,cb5;

    cb2 = (CheckBox)v.findViewById(R.id.checkBox2);
    cb2.setChecked(getFromSP("cb2"));
    cb2.setOnCheckedChangeListener(this);
    cb3 = (CheckBox)v.findViewById(R.id.checkBox3);
    cb3.setChecked(getFromSP("cb3"));
    cb3.setOnCheckedChangeListener(this);
    cb4 = (CheckBox)v.findViewById(R.id.checkBox4);
    cb4.setChecked(getFromSP("cb4"));
    cb4.setOnCheckedChangeListener(this);
    cb4 = (CheckBox)v.findViewById(R.id.checkBox5);
    cb4.setChecked(getFromSP("cb5"));
    cb4.setOnCheckedChangeListener(this);
    return v;
    

}
	 public void onCreate(Bundle savedInstanceState)
	 {	
		 super.onCreate(savedInstanceState);
		 TaskList tl=(TaskList)getActivity();
	 	Bundle bundle=tl.sendData();
	 	University_name=bundle.getString("UniName");
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
		 
		 case R.id.checkBox2:
			 saveInSp("cb2",isChecked);
			 break;

		 case R.id.checkBox3:
			 saveInSp("cb3",isChecked);
			 break;

		 case R.id.checkBox4:
			 saveInSp("cb4",isChecked);
			 break;
			 
		 case R.id.checkBox5:
			 saveInSp("cb5",isChecked);
			 break;
		 }

	 }
}
	
