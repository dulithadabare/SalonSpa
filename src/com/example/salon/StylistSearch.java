package com.example.salon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class StylistSearch extends Activity{

    static ListView list;
    static ListView salon_list;
	static StylistAdapter StylistAdapter;
	static ArrayAdapter<String> adapter;
	static Context CON;
	static LinearLayout linlaHeaderProgress;
	static LinearLayout linlaHeaderProgress2;
	static String stylistID;
	static String stylistName;
	static String selected_stylist_lname;
	static String selected_stylist_fname;
	static String salonID;
	static String salonName;
	 
	static String salon_mapID;
	static String salon_BranchID;

	Context con;
	AlertDialog alert;
	static ArrayList<String> dialog_list = new ArrayList<String>();
	HashMap<String, String> dialog_salon = new HashMap<String, String>();
	static ArrayList<String> original_data = new ArrayList<String>();
	
	 Calendar c = Calendar.getInstance(); 
	 
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
		
        setContentView(R.layout.stylist_search);
        con=this;
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		
		 list=(ListView)findViewById(R.id.listall);
		final EditText  search = (EditText) this.findViewById(R.id.inputSearch);
		final ImageView  sort_icon = (ImageView) this.findViewById(R.id.sort_icon);
		linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
		search.setHint("Search Stylists..");
		
		CON=this;
		
		  search.addTextChangedListener(new TextWatcher() {

				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					
						StylistAdapter.filter(arg0.toString());
					
					// TODO Auto-generated method stub
				
				} 
	        	
	        
	            
	                    });
		  
		  if(isNetworkAvailable()){
			  new StylistAdapterTask().execute("NotSet");
        }else{
        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setCancelable(false);
                builder.setMessage("SalonSpa needs an active data connection to continue")
                      .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int id) {
                       	   if(isNetworkAvailable()){
                       		 new StylistAdapterTask().execute("NotSet");
                       	   }else{
                       	   builder.show();
                       	   }
                              // FIRE ZE MISSILES!
                          }
                      });
                // Create the AlertDialog object and return it
                
                builder.show();
        }
		
	            
	       
	       
list.setOnItemClickListener(new OnItemClickListener() {
	            
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					stylistID = (String) ((TextView)arg1.findViewById(R.id.StylistID)).getText();
					stylistName = (String) ((TextView)arg1.findViewById(R.id.Name)).getText();
					

					String[] name = StylistSearch.stylistName.split(" ");

					  StylistSearch.selected_stylist_fname=name[0];
					  StylistSearch.selected_stylist_lname= name[1];
					  
					  
					SalonSearch.MapID = (String) ((TextView)arg1.findViewById(R.id.salonID)).getText();
					
					for(HashMap<String, String> i:StylistAdapterTask.StylistList){
						if((i.get(Stylist.KEY_STYLIST_ID).equals(stylistID)) && (i.get(Stylist.KEY_MAP_ID).equals(SalonSearch.MapID))){
							SalonSearch.BranchID=i.get(Stylist.KEY_STYLIST_BRANCH_ID);
							SalonSearch.BranchName=i.get(Stylist.KEY_STYLIST_COMPANY);
						}
					}
					Intent tute_intent= new Intent(StylistSearch.this,ServiceSearch.class);
					tute_intent.putExtra("LAST_ACTIVITY", "StylistSearch");
					Bundle translateBundle = ActivityOptions.makeCustomAnimation(StylistSearch.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
					startActivity(tute_intent,translateBundle);
					
				}

			});

sort_icon.setOnClickListener(new OnClickListener(){

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		  
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
			 
		LayoutInflater inflater = getLayoutInflater();
		View view=inflater.inflate(R.layout.dialog_salon, null);
		EditText search = (EditText) view.findViewById(R.id.editText1);
		salon_list =(ListView) view.findViewById(R.id.listView1);
		linlaHeaderProgress2 = (LinearLayout) view.findViewById(R.id.linlaHeaderProgress2);
	//	Spinner spinner1 =(Spinner) view.findViewById(R.id.spinner1);
		
		new SalonDialogTask().execute("NotSet");
		
		salon_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				 String salon_name = (String) ((TextView)arg1).getText();
			
					for(HashMap<String, String> i:SalonDialogTask.salonList){
						if(i.get(Salon.KEY_SALON_NAME).equals(salon_name)){
							SalonSearch.MapID=i.get(Salon.KEY_MAP_ID);
							SalonSearch.BranchID=i.get(Salon.KEY_ID);
						}
						
					}
					new StylistAdapterTask().execute("Salon_Selected");
				// Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
				 alert.cancel();
				
				
			}});
		 search.addTextChangedListener(new TextWatcher() {

				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					
					String charText=arg0.toString();
					 charText = charText.toLowerCase();
				        dialog_list.clear();
				        if (charText.length() == 0) {
				        	dialog_list.addAll(original_data);
				        } else {
				            for (String dataraw : original_data) {
				            	
				                if (dataraw.toLowerCase().contains(charText)) {
				                	dialog_list.add(dataraw);
				                }
				            }
				        }
				        ArrayAdapter<String> adapter2;
				        adapter2= new  ArrayAdapter<String>(con,
			     android.R.layout.simple_list_item_1, dialog_list);
				        salon_list.setAdapter(adapter2);
					// TODO Auto-generated method stub
				
				} 
	        	
	        
	            
	                    });
	//spinner1.setAdapter(dataAdapter);
		//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
		
		builder.setView(view);
		 
		
		builder.setTitle("Stylist by Salon");
        builder
               .setPositiveButton("All Salons", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the positive button event back to the host activity
                	   new StylistAdapterTask().execute("NotSet");
                	   alert.cancel();
                       }
               });
           
        
       
        alert= builder.create();
        alert.show();
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
  			 
			LayoutInflater inflater = getLayoutInflater();
			View view=inflater.inflate(R.layout.dialog_salon, null);
			EditText search = (EditText) view.findViewById(R.id.editText1);
			salon_list =(ListView) view.findViewById(R.id.listView1);
			linlaHeaderProgress2 = (LinearLayout) view.findViewById(R.id.linlaHeaderProgress2);
		//	Spinner spinner1 =(Spinner) view.findViewById(R.id.spinner1);
			
			new SalonDialogTask().execute("NotSet");
			
			salon_list.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					 String salon_name = (String) ((TextView)arg1).getText();
				
						for(HashMap<String, String> i:SalonDialogTask.salonList){
							if(i.get(Salon.KEY_SALON_NAME).equals(salon_name)){
								SalonSearch.MapID=i.get(Salon.KEY_MAP_ID);
								SalonSearch.BranchID=i.get(Salon.KEY_ID);
							}
							
						}
						new StylistAdapterTask().execute("Salon_Selected");
					// Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
					 alert.cancel();
					
					
				}});
			 search.addTextChangedListener(new TextWatcher() {

					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub
						
					}

					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						// TODO Auto-generated method stub
						
					}

					public void onTextChanged(CharSequence arg0, int arg1, int arg2,
							int arg3) {
						
						String charText=arg0.toString();
						 charText = charText.toLowerCase();
					        dialog_list.clear();
					        if (charText.length() == 0) {
					        	dialog_list.addAll(original_data);
					        } else {
					            for (String dataraw : original_data) {
					            	
					                if (dataraw.contains(charText)) {
					                	dialog_list.add(dataraw);
					                }
					            }
					        }
					        ArrayAdapter<String> adapter2;
					        adapter2= new  ArrayAdapter<String>(con,
  			     android.R.layout.simple_list_item_1, dialog_list);
					        salon_list.setAdapter(adapter2);
						// TODO Auto-generated method stub
					
					} 
		        	
		        
		            
		                    });
		//spinner1.setAdapter(dataAdapter);
			//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
			
			builder.setView(view);
			 
			
			builder.setTitle("Stylist by Salon");
	        builder
	               .setPositiveButton("All Salons", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // Send the positive button event back to the host activity
	                	   new StylistAdapterTask().execute("NotSet");
	                	   alert.cancel();
	                       }
	               });
	           
	        
	       
	        alert= builder.create();
	        alert.show();
            
        }
		
		return true;
		
		
	}

	
	
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
	}
}
