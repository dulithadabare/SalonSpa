package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchableActivity extends FragmentActivity{
	
	
	MyPageAdapter pageAdapter;
	String query="";
	
	 private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	 
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.searchable_activity);
		
		if(isNetworkAvailable()){
		}else{
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setMessage("SalonSpa needs an active data connection to continue")
		              .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
		                  public void onClick(DialogInterface dialog, int id) {
		               	   if(isNetworkAvailable()){
		                   
		               	   }else{
		               	   builder.show();
		               	   }
		                      // FIRE ZE MISSILES!
		                  }
		              })
		              .setNegativeButton("Close App", new DialogInterface.OnClickListener() {
		                  public void onClick(DialogInterface dialog, int id) {
		                      // User cancelled the dialog
		               	   finish();
		                  }
		              });
		        // Create the AlertDialog object and return it
		        builder.show();
		}
        
		
	   Intent intent = getIntent();
	      if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	     query = intent.getStringExtra(SearchManager.QUERY);
	     SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
	     suggestions.saveRecentQuery(query, null);
	      
	     }
		List<Fragment> fragments = getFragments();
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		final ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
		pager.setOffscreenPageLimit(2);
		pager.setAdapter(pageAdapter);
		
		final ActionBar actionBar = getActionBar();
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
	  
	        actionBar.addTab(actionBar.newTab().setText("Salons").setTabListener(tabListener));
	        actionBar.addTab(actionBar.newTab().setText("Services").setTabListener(tabListener));
	        actionBar.addTab(actionBar.newTab().setText("Stylists").setTabListener(tabListener));
	       
	    
	    
	    pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				super.onPageSelected(position);
				getActionBar().setSelectedNavigationItem(position);
			}
	    	
	    });
	
	}

	private List<Fragment> getFragments() {
		// TODO Auto-generated method stub
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(UnifiedSalonFragment.newInstance(query));
		fList.add(UnifiedServiceFragment.newInstance(query));
		fList.add(UnifiedStylistFragment.newInstance(query));
		
		 
		return fList;
	}

/*	StylistAdapter StylistAdapter;
	Context con;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchable_activity);
		ListView list=(ListView)findViewById(R.id.listall);
		con=this;
		String query="";
		
		Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      query = intent.getStringExtra(SearchManager.QUERY);
	      
	    }
	    
	    

		  
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}
     		ArrayList<HashMap<String, String>> StylistList = new ArrayList<HashMap<String, String>>();
     			jsonParser json = new jsonParser(con);
     		String Jsondata= 	json.getJsonFromUrl("http://192.168.6.7/Aggrigator/index.php/api/Search_api/search_by_styler/format/json"); 
     		JSONArray jsonArray=	json.ConvertToJsonobj(Jsondata);
     		
		//XMLParser parser = new XMLParser(this);
		//String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		//Document doc = parser.getDomElement(xml); // getting DOM element
		
		//NodeList nl = doc.getElementsByTagName("DoctorInfo");
		// looping through all song nodes <song>
		for (int i = 0; i <jsonArray.length(); i++) {
			// creating new HashMap
			 JSONObject jsonObject;
			try {
				jsonObject = jsonArray.getJSONObject(i);
		
			HashMap<String, String> map = new HashMap<String, String>();
			//Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
	
          //Element e = (Element) nl.item(i);
          // adding each child node to HashMap key => value
          map.put(Stylist.KEY_FISRT_NAME, jsonObject.getString(Stylist.KEY_FISRT_NAME));
          map.put( Stylist.KEY_LAST_NAME, jsonObject.getString( Stylist.KEY_LAST_NAME));
          map.put(Stylist.KEY_STYLIST_ID, jsonObject.getString( Stylist.KEY_STYLIST_ID));
          map.put(Stylist.KEY_STYLIST_JOBTITLE, jsonObject.getString( Stylist.KEY_STYLIST_JOBTITLE));
          map.put(Stylist.KEY_STYLIST_THUMB_URL,jsonObject.getString( Stylist.KEY_STYLIST_THUMB_URL));
          map.put("companyDetails", jsonObject.getString("0"));
     
     
			StylistList.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// adding HashList to ArrayList
			
		}
		
		
		// Getting adapter by passing xml data ArrayList
        StylistAdapter=new StylistAdapter(con, StylistList);        
        list.setAdapter(StylistAdapter);
        StylistAdapter.filter(query.toString());
		
		
	}
*/
	
	    
}
