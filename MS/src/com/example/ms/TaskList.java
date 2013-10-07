package com.example.ms;
 
import java.util.ArrayList;


 
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
 
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import static com.example.database.Constants.*;
public class TaskList extends FragmentActivity implements LoaderCallbacks<Cursor> {
 
 private static final int LIST_ID = 0;
 
private static final String[] PROJECTION = new String[]{_ID,DEADLINE,FEE,NO_LORS,NO_TRANSCRIPTS};
private static final String SELECTION = null;
ViewPager ViewPager;
 TabsAdapter TabsAdapter;
 String text,app_deadline,app_fee,lor,transcript;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        //create a new ViewPager and set to the pager we have created in Ids.xml
        ViewPager = new ViewPager(this);
        ViewPager.setId(R.id.pager);
        setContentView(ViewPager);
        
        //Getting University name
       // Bundle bundle=this.getIntent().getExtras();
        Intent i=getIntent();
        text=i.getStringExtra("UniName");
        
        
 
        //Create a new Action bar and set title to strings.xml
        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setTitle(R.string.title_activity_task_list);
 
        //Attach the Tabs to the fragment classes and set the tab title.
        TabsAdapter = new TabsAdapter(this, ViewPager);
        TabsAdapter.addTab(bar.newTab().setText("Details"),
                Details.class, null);
        TabsAdapter.addTab(bar.newTab().setText("Documents required"),
          Documents.class, null);
        TabsAdapter.addTab(bar.newTab().setText("Post application"),
          PostApp.class, null);
       
        getLoaderManager().initLoader(LIST_ID, null, this);
        
        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
 
    }
 
 @Override
 protected void onSaveInstanceState(Bundle outState) {
  super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
 
 }
 // create TabsAdapter to create tabs and behavior
 public static class TabsAdapter extends FragmentPagerAdapter
  implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
 
  private final Context mContext;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
 
        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;
 
            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }
 
  public TabsAdapter(FragmentActivity activity, ViewPager pager) {
   super(activity.getSupportFragmentManager());
            mContext = activity;
            mActionBar = activity.getActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }
 
  public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
 
        }
 
  @Override
  public void onPageScrollStateChanged(int state) {
   // TODO Auto-generated method stub
 
  }
 
  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
   // TODO Auto-generated method stub
 
  }
 
  @Override
  public void onPageSelected(int position) {
   // TODO Auto-generated method stub
   mActionBar.setSelectedNavigationItem(position);
  }
 
  @Override
  public void onTabReselected(Tab tab, FragmentTransaction ft) {
   // TODO Auto-generated method stub
 
  }
 
  @Override
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
   Object tag = tab.getTag();
            for (int i=0; i<mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
  }
 
  @Override
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
   // TODO Auto-generated method stub
 
  }
 
  @Override
  public Fragment getItem(int position) {
   TabInfo info = mTabs.get(position);
   		 return Fragment.instantiate(mContext, info.clss.getName(), info.args);
   			
  }
 
  @Override
  public int getCount() {
   return mTabs.size();
  }
 
 }
 public Bundle sendData()
 {	Bundle bundle=new Bundle();
 	bundle.putString("UniName", text);
 	bundle.putString("deadline", app_deadline);
 	bundle.putString("fee", app_fee);
 	bundle.putString("lor", lor);
 	bundle.putString("transcript", transcript);
 	
 	return bundle;
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
	app_deadline = cursor.getString(2);
	app_fee = Double.toString(cursor.getDouble(3));
	lor = Integer.toString(cursor.getInt(4));
	transcript = Integer.toString(cursor.getInt(5));
	
}

@Override
public void onLoaderReset(Loader<Cursor> arg0) {
	// TODO Auto-generated method stub
	
}
 
}