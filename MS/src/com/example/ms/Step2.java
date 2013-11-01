package com.example.ms;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Step2 extends Fragment {
	ViewGroup rootView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
       rootView = (ViewGroup) inflater.inflate( R.layout.step2, container, false);
       /*Button b=(Button)rootView.findViewById(R.id.go_to2);
      	final int x= container.getId();
       	b.setOnClickListener(new OnClickListener(){
   		public void onClick(View v)
   		{	Fragment fragment=new Step1();
   			FragmentTransaction ft = getFragmentManager().beginTransaction();
            //ft.replace(x,fragment)
            //ft.commit();
   			ft.show(fragment);
   			
   			
   		}
   	});*/

        return rootView;
    }
}