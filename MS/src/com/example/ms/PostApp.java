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

public class PostApp extends Fragment implements OnCheckedChangeListener {
	String University_name;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
		        // Inflate the layout for this fragment
		 super.onCreateView(inflater, container, savedInstanceState);
    View v= inflater.inflate(R.layout.postapp, container, false);
    CheckBox cb1,cb2,cb3,cb4,cb5;

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
		 }

	 }
}
	
