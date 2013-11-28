package com.example.ms;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Details extends Fragment {
	
	private static String University_name,app_deadline,app_fee,lor,transcript,username,password;
	TextView Name,deadline,fee,lors,transcripts,username1,password1;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) 
	{
	        // Inflate the layout for this fragment
		super.onCreateView(inflater, container, savedInstanceState);
		View v= inflater.inflate(R.layout.details, container, false);
	    Name=(TextView)v.findViewById(R.id.university_name);
		Name.setText(University_name);
		deadline=(TextView)v.findViewById(R.id.Date_value);
		deadline.setText(app_deadline);
		fee=(TextView)v.findViewById(R.id.Fee_value);
		fee.setText(app_fee);
		lors=(TextView)v.findViewById(R.id.lors_value);
		lors.setText(lor);
		transcripts=(TextView)v.findViewById(R.id.transcripts_value);
		transcripts.setText(transcript);
		username1=(TextView)v.findViewById(R.id.username_value);
		username1.setText(username);
		password1=(TextView)v.findViewById(R.id.password_value);
		password1.setText(password);
	     
		return v;

	}
		
		public void onCreate(Bundle savedInstanceState)
		 {	super.onCreate(savedInstanceState);
			TaskList tl = new TaskList();
			tl =(TaskList)getActivity();
		    Bundle bundle=tl.sendData();
		 	University_name=bundle.getString("UniName");
		 	app_deadline=bundle.getString("deadline");
		 	app_fee=bundle.getString("fee");
		 	lor=bundle.getString("lor");
		 	transcript=bundle.getString("transcript");	
		 	username=bundle.getString("username");
		 	password=bundle.getString("password");
		 }


}
