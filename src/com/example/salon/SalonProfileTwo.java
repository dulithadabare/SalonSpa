package com.example.salon;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.widget.Toast;

public class SalonProfileTwo extends FragmentActivity{
	MyPageAdapter pageAdapter;
	 String last_activity;
	 
	 static String serviceID;
	 
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.salon_profile);
		 Bundle extras = getIntent().getExtras();
	        last_activity= extras.getString("LAST_ACTIVITY");
	       
	        
	        
		List<Fragment> fragments= new ArrayList<Fragment>();
		fragments.add(SalonDescriptionFragment.newInstance(last_activity));
		fragments.add(StylistSearchFragment.newInstance(last_activity));
		
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		final ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
		pager.setAdapter(pageAdapter);
		
	      //  Toast.makeText(getApplicationContext(),last_activity, Toast.LENGTH_SHORT).show();
		final ActionBar actionBar = getActionBar();
		//Tell the ActionBar we want to use Tabs.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    // Remove title and icon
	    actionBar.setDisplayShowHomeEnabled(false);
	    actionBar.setDisplayShowTitleEnabled(false);

		pager.setPageTransformer(false, new PageTransformer()
        {
           
          

			@Override
			public void transformPage(View arg0, float arg1) {
				// TODO Auto-generated method stub
				 final float invt = Math.abs(Math.abs(arg1) - 1);
				 arg0.setAlpha(invt);
			}
        });
	    // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	       

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(arg0.getPosition());
				
				
			}

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
	    };

	    // Add 3 tabs, specifying the tab's text and TabListener
	  
	        actionBar.addTab(actionBar.newTab().setText("Description").setTabListener(tabListener));
	      //  actionBar.addTab(actionBar.newTab().setText("Service").setTabListener(tabListener));
	        actionBar.addTab(actionBar.newTab().setText("Stylist").setTabListener(tabListener));
	    
	    
	    pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				super.onPageSelected(position);
				getActionBar().setSelectedNavigationItem(position);
			}
	    	
	    });
	
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}



	
	
}
