package com.example.salon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;

public class TimeDialogTask extends AsyncTask<String,Integer,ArrayAdapter<String>>{
    
	ArrayList<String> dialog_list = new ArrayList<String>();
	HashMap<String, String> dialog_salon = new HashMap<String, String>();
	 ArrayAdapter<String> dataAdapter;
	ArrayList<HashMap<String, String>> stylistList;
	String calling_activity;
	
	public TimeDialogTask(String calling_activity1){
		calling_activity=calling_activity1;
	}
	
		@Override
		protected ArrayAdapter<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			 
			 if (android.os.Build.VERSION.SDK_INT > 9) {
     		 
		        	
	  	        	StrictMode.ThreadPolicy policy = 
	  	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  	        	StrictMode.setThreadPolicy(policy);
	  	        	
	  	        	}
	  	     		 stylistList = new ArrayList<HashMap<String, String>>();
	  	     			jsonParser json = new jsonParser(StylistSearch.CON);
	  	     		    json.SetUrl(JsonUrl.ApiUrl+"Calendar_api/get_free_time_slot_for_EMP/format/json"); 
	                 //json.SetParams("keyword","s");
	  	     	   
	              
	             
	  	     		if(calling_activity.equals("ServiceSearch")){
	  	     		     json.SetParams("companyId",SalonSearch.MapID); 
	  	     		     json.SetParams("SelectedDate",ServiceSearch.selected_date);
	  	     		     json.SetParams("EmpID", StylistSearch.stylistID);
	                     json.SetParams("ServiceID",ServiceSearch.serviceID);
	                     
	                }else if(calling_activity.equals("ServiceSearchFragment")){
	                	 json.SetParams("companyId",SalonSearch.MapID); 
	                	 json.SetParams("SelectedDate",ServiceSearchFragment.selected_date);
	                	 json.SetParams("EmpID", StylistSearch.stylistID);
		                 json.SetParams("ServiceID",ServiceSearch.serviceID);
		                 
	                }else if(calling_activity.equals("SalonProfileTwo")){
	                	 json.SetParams("companyId",SalonSearch.MapID); 
	                	 json.SetParams("SelectedDate",SalonDescriptionFragment.selected_date);
	                	 json.SetParams("EmpID", StylistSearch.stylistID);
		                 json.SetParams("ServiceID",ServiceSearch.serviceID);
		                 
	                }else if(calling_activity.equals("SalonProfile_StylistFragment")){
	                	 json.SetParams("companyId",SalonSearch.MapID); 
	                	 json.SetParams("SelectedDate",ServiceSearch.selected_date);
	                	 json.SetParams("EmpID", StylistSearch.stylistID);
		                 json.SetParams("ServiceID",ServiceSearch.serviceID);
		                 
	                }else if(calling_activity.equals("StylistSearch")){
	                	 json.SetParams("companyId",SalonSearch.MapID); 
	                	 json.SetParams("SelectedDate",ServiceSearch.selected_date);
	                	 json.SetParams("EmpID", StylistSearch.stylistID);
		                 json.SetParams("ServiceID",ServiceSearch.serviceID);
	                }else if(calling_activity.equals("StylistSearchFragment")){ 
	                	 json.SetParams("companyId",SalonSearch.MapID); 
	                	 json.SetParams("SelectedDate",StylistSearchFragment.selected_date);
	                	 json.SetParams("EmpID", StylistSearch.stylistID);
		                 json.SetParams("ServiceID",ServiceSearch.serviceID);
	                }else if(calling_activity.equals("UserHistoryFragment")){ 
	                	 json.SetParams("companyId",SalonSearch.MapID); 
	                	 json.SetParams("SelectedDate",UserHistoryFragment.selected_date);
	                	 json.SetParams("EmpID", StylistSearch.stylistID);
		                 json.SetParams("ServiceID",ServiceSearch.serviceID);
	                }else if(calling_activity.equals("UserUpcomingFragment")){ 
	                	 json.SetParams("companyId",SalonSearch.MapID); 
	                	 json.SetParams("SelectedDate",UserUpcomingFragment.selected_date);
	                	 json.SetParams("EmpID", StylistSearch.stylistID);
		                 json.SetParams("ServiceID",ServiceSearch.serviceID);
	                }
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
	  	     		JSONArray jsonArray=	json.ConvertToJsonobj(Jsondata);
	  	     		
	  			//XMLParser parser = new XMLParser(this);
	  			//String xml = parser.getXmlFromUrl(URL); // getting XML from URL
	  			//Document doc = parser.getDomElement(xml); // getting DOM element
	  			
	  			//NodeList nl = doc.getElementsByTagName("DoctorInfo");
	  			// looping through all song nodes <song>
	  	     		ArrayList<String> time_list = new ArrayList<String>();
	  	     		time_list.clear();
	  			for (int i = 0; i <jsonArray.length(); i++) {
	  				// creating new HashMap
	  				try {
						String date_time= jsonArray.get(i).toString();
						try {
							Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(date_time);
							SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm");
							time_list.add(printFormat.format(date));
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	  			
	  			}
	  			
	  			// Getting adapter by passing xml data ArrayList
	  			if((calling_activity.equals("ServiceSearch")) || (calling_activity.equals("SalonProfile_StylistFragment")) || (calling_activity.equals("StylistSearch"))){
	  			 dataAdapter = new ArrayAdapter<String>(ServiceSearch.CON,android.R.layout.simple_spinner_item, time_list);
	  			}else if(calling_activity.equals("ServiceSearchFragment")){
	  				 dataAdapter = new ArrayAdapter<String>(ServiceSearchFragment.CON,android.R.layout.simple_spinner_item, time_list);
	  			}else if(calling_activity.equals("SalonProfileTwo")){
	  				 dataAdapter = new ArrayAdapter<String>(SalonDescriptionFragment.CON,android.R.layout.simple_spinner_item, time_list);
	  			}else if(calling_activity.equals("StylistSearchFragment")){
	  				 dataAdapter = new ArrayAdapter<String>(StylistSearchFragment.CON,android.R.layout.simple_spinner_item, time_list);
	  			}else if(calling_activity.equals("UserHistoryFragment")){
	  				 dataAdapter = new ArrayAdapter<String>(UserHistoryFragment.CON,android.R.layout.simple_spinner_item, time_list);
	  			}else if(calling_activity.equals("UserUpcomingFragment")){
	  				 dataAdapter = new ArrayAdapter<String>(UserUpcomingFragment.CON,android.R.layout.simple_spinner_item, time_list);
	  			}
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  			   
	  		
			return dataAdapter;
		}

		@Override
		protected void onPostExecute(ArrayAdapter<String> result) {
		
			// TODO Auto-generated method stub
			if((calling_activity.equals("ServiceSearch")) || (calling_activity.equals("SalonProfile_StylistFragment")) || (calling_activity.equals("StylistSearch"))){
				
			ServiceSearch.linlaHeaderProgress2.setVisibility(View.GONE);
			ServiceSearch.time_spinner.setAdapter(result);
			if(result.getCount()==0){
				ServiceSearch.show_message("Sorry, this stylist doesn't have any time slots available");
			}
			
			}else if(calling_activity.equals("ServiceSearchFragment")){
				
				ServiceSearchFragment.linlaHeaderProgress2.setVisibility(View.GONE);
				ServiceSearchFragment.time_spinner.setAdapter(result);	
				if(result.getCount()==0){
					ServiceSearchFragment.show_message("Sorry, this stylist doesn't have any time slots available");
				}
				
			}else if(calling_activity.equals("SalonProfileTwo")){
				
				SalonDescriptionFragment.linlaHeaderProgress2.setVisibility(View.GONE);
				SalonDescriptionFragment.time_spinner.setAdapter(result);	
				if(result.getCount()==0){
					SalonDescriptionFragment.show_message("Sorry, this stylist doesn't have any time slots available");
				}
				
			}else if(calling_activity.equals("StylistSearchFragment")){
				
				StylistSearchFragment.linlaHeaderProgress2.setVisibility(View.GONE);
				StylistSearchFragment.time_spinner.setAdapter(result);	
				if(result.getCount()==0){
					StylistSearchFragment.show_message("Sorry, this stylist doesn't have any time slots available");
				}
			}else if(calling_activity.equals("UserHistoryFragment")){
				
				UserHistoryFragment.linlaHeaderProgress2.setVisibility(View.GONE);
				UserHistoryFragment.time_spinner.setAdapter(result);	
				if(result.getCount()==0){
					UserHistoryFragment.show_message("Sorry, this stylist doesn't have any time slots available");
				}
			}else if(calling_activity.equals("UserUpcomingFragment")){
				
				UserUpcomingFragment.linlaHeaderProgress2.setVisibility(View.GONE);
				UserUpcomingFragment.time_spinner.setAdapter(result);	
				if(result.getCount()==0){
					UserUpcomingFragment.show_message("Sorry, this stylist doesn't have any time slots available");
				}
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			if((calling_activity.equals("ServiceSearch")) || (calling_activity.equals("SalonProfile_StylistFragment")) || (calling_activity.equals("StylistSearch"))){
				
			ServiceSearch.linlaHeaderProgress2.setVisibility(View.VISIBLE);
			
			}else if(calling_activity.equals("ServiceSearchFragment")){
				
				ServiceSearchFragment.linlaHeaderProgress2.setVisibility(View.VISIBLE);
				
			}else if(calling_activity.equals("SalonProfileTwo")){
				
				SalonDescriptionFragment.linlaHeaderProgress2.setVisibility(View.VISIBLE);
				
			}else if(calling_activity.equals("StylistSearchFragment")){
				
				StylistSearchFragment.linlaHeaderProgress2.setVisibility(View.VISIBLE);
				
			}else if(calling_activity.equals("UserHistoryFragment")){
				
				UserHistoryFragment.linlaHeaderProgress2.setVisibility(View.VISIBLE);
				
			}else if(calling_activity.equals("UserUpcomingFragment")){
				
				UserUpcomingFragment.linlaHeaderProgress2.setVisibility(View.VISIBLE);
				
			}
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		
	

}
