package com.example.salon;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class UserProfile extends FragmentActivity{
	MyPageAdapter pageAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.user_profile);
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(UserUpcomingFragment.newInstance("Hello"));
		fragments.add(UserHistoryFragment.newInstance("Hello"));
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		final ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
		pager.setAdapter(pageAdapter);
		
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
	  
	        actionBar.addTab(actionBar.newTab().setText("Current").setTabListener(tabListener));
	        actionBar.addTab(actionBar.newTab().setText("History").setTabListener(tabListener));
	       
	    
	    
	    pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				super.onPageSelected(position);
				getActionBar().setSelectedNavigationItem(position);
			}
	    	
	    });
	
	}
}
