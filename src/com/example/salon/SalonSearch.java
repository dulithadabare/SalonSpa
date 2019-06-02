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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SalonSearch extends Activity{
	
	//FeaturedAdapter FeaturedAdapter;
	static LazyAdapter adapter;
	static Context CON;
	static LinearLayout linlaHeaderProgress;
	static ListView list;
	
	static String BranchID;
	static String MapID;
	static String SalonCity="Colombo";
	static String SalonCountry="Sri Lanka";
	static String SalonProvince="Western Province";
	static String SalonStreet1;
	static String SalonStreet2;
	static String SalonLongitude;
	static String SalonLatitude;
	static String SalonZipcode;
	static String BranchName="Salon Name";
	static String BranchDescription="Salon Description";
	static String Longitude;
	static String Latitude;
	Context con;
	int search_text=0;

	int selected_sort=1;
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
	}
	
	 private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
	
		
        setContentView(R.layout.salon_search);
        con=this;
		CON=this;
     
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        Bundle extras = getIntent().getExtras();
        final String last_activity= extras.getString("LAST_ACTIVITY");
     
		
		list=(ListView)findViewById(R.id.listall);
		list.setDividerHeight(0);
		list.setDivider(null);
		
	
		final EditText  search = (EditText) this.findViewById(R.id.inputSearch);
		final ImageView  sort_icon = (ImageView) this.findViewById(R.id.sort_icon);
	
		
		
		search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.searchicon, 0);
	
		  search.addTextChangedListener(new TextWatcher() {
          
				public void afterTextChanged(Editable arg0) {
					
					
				}

				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
					
				}

				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					     
				     //   search_text=1;
				
						adapter.filter(arg0.toString());
						
					
					// TODO Auto-generated method stub
				
				} 
	        	
	        
	            
	                    });
		  
		   
	        if(isNetworkAvailable()){
	        	
	        	  if(last_activity.equals("Main")){
	       	       new SalonAdapterTask().execute("Main");
	       		  } else if(last_activity.equals("ServiceSearch")){
	       			 String toast_message="Pick a Salon for your "+ServiceSearch.serviceName;
			    	 Toast.makeText(getApplicationContext(), toast_message, Toast.LENGTH_LONG).show();
	       			  new SalonAdapterTask().execute("ServiceFlow");
	       		  }
	        }else{
	        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
	                builder.setMessage("SalonSpa needs an active data connection to continue")
	                      .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
	                          public void onClick(DialogInterface dialog, int id) {
	                       	   if(isNetworkAvailable()){
	                       		  if(last_activity.equals("Main")){
	               	       	       new SalonAdapterTask().execute("Main");
	               	       		  } else if(last_activity.equals("ServiceSearch")){
	               	       			  new SalonAdapterTask().execute("ServiceFlow");
	               	       		  }
	                       	   }else{
	                       	   builder.show();
	                       	   }
	                              // FIRE ZE MISSILES!
	                          }
	                      })
	                     ;
	                // Create the AlertDialog object and return it
	                builder.show();
	        }
		
			 
	      
	        
list.setOnItemClickListener(new OnItemClickListener() {
	            
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					 BranchID = (String) ((TextView)arg1.findViewById(R.id.BranchID)).getText();
             	     MapID = (String) ((TextView)arg1.findViewById(R.id.MapID)).getText();
                   if(last_activity.equals("ServiceSearch")){
                	   Intent tute_intent3= new Intent("com.example.salon.SALONPROFILETWO");
   					   tute_intent3.putExtra("LAST_ACTIVITY", last_activity);
   					
					   
   					startActivity(tute_intent3);
                   }else{
                	   
                	   
  					// Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
					Intent tute_intent3= new Intent("com.example.salon.SALONPROFILE");
					tute_intent3.putExtra("LAST_ACTIVITY", last_activity);
					startActivity(tute_intent3);}
				
				}

			});

sort_icon.setOnClickListener(new OnClickListener(){

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		  AlertDialog.Builder builder = new AlertDialog.Builder(con);
			
	     	 
			
			
			//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
			
			String[] words = {"Nearby Salons","Sort by Name","Sort by Price"};
			builder.setTitle("Sort");
	        builder.setSingleChoiceItems(words,selected_sort, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					selected_sort=which;
				
				}
			});
	        
	        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}});
	        builder.setPositiveButton("Done", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if(selected_sort==0){
						 final LocationManager mlocManager = (LocationManager) 
							        getSystemService(Context.LOCATION_SERVICE);
							LocationListener mlocListener = new LocationListener(){

								@Override
								public void onLocationChanged(Location arg0) {
									// TODO Auto-generated method stub
									double latitude= arg0.getLatitude();
									double longitude = arg0.getLongitude();
									Toast.makeText(getApplicationContext(), String.valueOf(latitude)+" "+String.valueOf(longitude), Toast.LENGTH_SHORT).show();
							        Longitude=String.valueOf(longitude);
							        Latitude=String.valueOf(latitude);
									new SalonAdapterTask().execute("LocationFlow");
									mlocManager.removeUpdates(this);
								}

								@Override
								public void onProviderDisabled(String arg0) {
									// TODO Auto-generated method stub
								    selected_sort=1;
									AlertDialog.Builder builder = new AlertDialog.Builder(con);
									//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
									
									builder.setMessage("Please enable Location Services to search nearby Salons :)");
									 
									
									builder.setTitle("Notice");
							        builder
							               .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
							                   public void onClick(DialogInterface dialog, int id) {
							                	   if(last_activity.equals("Main")){
							        	       	       new SalonAdapterTask().execute("Main");
							        	       		  } else if(last_activity.equals("ServiceSearch")){
							        	       			  new SalonAdapterTask().execute("ServiceFlow");
							        	       		  }
							                	   
							                   }});
							        builder.show();
								}

								@Override
								public void onProviderEnabled(String arg0) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
									// TODO Auto-generated method stub
									
								}
								
							};
							mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
							            0, mlocListener);
					}else if(selected_sort==1){
						  if(last_activity.equals("Main")){
	        	       	       new SalonAdapterTask().execute("Main");
	        	       		  } else if(last_activity.equals("ServiceSearch")){
	        	       			  new SalonAdapterTask().execute("ServiceFlow");
	        	       		  }
					}
				}});
	        builder.show();	
	}});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.service_category_menu, menu);
        return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	    switch (item.getItemId()) {
        case R.id.category_select:
        	
        	//DialogFragment dialog = new BookingDialog();
        	  
     	   AlertDialog.Builder builder = new AlertDialog.Builder(con);
			
     	 
				
			
				//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
				
				
				 
				String[] words = {"Sort by Name","Sort by Price"};
				builder.setTitle("Sort");
		      builder.setSingleChoiceItems(words,0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
		        
		        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}});
		        builder.setPositiveButton("Done", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}});
		        builder.show();		    break;
		    
     
        }
		
		return true;
	}

	
	
}
