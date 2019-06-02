package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;

public class StylistDialogTask extends AsyncTask<String,Integer,ArrayAdapter<String>>{
    
	ArrayList<String> dialog_list = new ArrayList<String>();
	HashMap<String, String> dialog_salon = new HashMap<String, String>();
	 ArrayAdapter<String> dataAdapter;
	static ArrayList<HashMap<String, String>> stylistList;
	String calling_activity;
	
	public StylistDialogTask(String calling_activity1){
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
	  	     			if(calling_activity.equals("DateSearchResult")){
	  	     			 json.SetUrl(JsonUrl.ApiUrl+"Calendar_api/get_employee_list_free_time_at_slected_date/format/json");	
	  	     			 json.SetParams("SelectedDate",DateSearchResult.selected_date);
	  	     			}else{
	  	     		     json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_styler/format/json"); 
	  	     			}
	  	     		json.SetParams("BranchId",SalonSearch.BranchID);
	                json.SetParams("companyId",SalonSearch.MapID);
		            json.SetParams("ServiceID",ServiceSearch.serviceID);
		                
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
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
	  				map.put(Stylist.KEY_FIRST_NAME, jsonObject.getString( Stylist.KEY_FIRST_NAME));
	  				map.put(Stylist.KEY_LAST_NAME, jsonObject.getString( Stylist.KEY_LAST_NAME));
	  				map.put(Stylist.KEY_STYLIST_ID, jsonObject.getString( Stylist.KEY_STYLIST_ID));
	  				
	  				stylistList.add(map);
	  				} catch (JSONException e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();
	  				}
	  				// adding HashList to ArrayList
	  				
	  			}
	  			for (int i = 0; i<stylistList.size(); i++) {
	  				
	  				dialog_salon=stylistList.get(i);
	  				String first_name=dialog_salon.get(Stylist.KEY_FIRST_NAME);
	  				String last_name=dialog_salon.get(Stylist.KEY_LAST_NAME);
	  				String full_name=first_name+" "+last_name;
	  				dialog_list.add(full_name);
					
	  				
	  			}
	  			
	  			
	  			// Getting adapter by passing xml data ArrayList
	  			if(calling_activity.equals("ServiceSearch")){
	  				
	  			  dataAdapter = new ArrayAdapter<String>(ServiceSearch.CON,android.R.layout.simple_spinner_item, dialog_list);
	  			  HashMap<String, String> dialog_list1 = StylistDialogTask.stylistList.get(0);
				  StylistSearch.stylistID = dialog_list1.get(Stylist.KEY_STYLIST_ID);
	  			   
	  		
	  			}else if(calling_activity.equals("ServiceSearchFragment")){
	  				
	  				dataAdapter = new ArrayAdapter<String>(ServiceSearchFragment.CON,android.R.layout.simple_spinner_item, dialog_list);
	  				HashMap<String, String> dialog_list1 = StylistDialogTask.stylistList.get(0);
	  				StylistSearch.stylistID = dialog_list1.get(Stylist.KEY_STYLIST_ID);
		  			   
	  			}else if(calling_activity.equals("SalonProfileTwo")){
	  				
	  				dataAdapter = new ArrayAdapter<String>(SalonDescriptionFragment.CON,android.R.layout.simple_spinner_item, dialog_list);
	  				HashMap<String, String> dialog_list1 = StylistDialogTask.stylistList.get(0);
	  				StylistSearch.stylistID = dialog_list1.get(Stylist.KEY_STYLIST_ID);
		  			   
	  			}else if(calling_activity.equals("DateSearchResult")){
	  				dataAdapter = new ArrayAdapter<String>(DateSearchResult.CON,android.R.layout.simple_spinner_item, dialog_list);
	  				HashMap<String, String> dialog_list1 = StylistDialogTask.stylistList.get(0);
	  				StylistSearch.stylistID = dialog_list1.get(Stylist.KEY_STYLIST_ID);
	  			}
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
			return dataAdapter;
		}

		@Override
		protected void onPostExecute(ArrayAdapter<String> result) {
		
			// TODO Auto-generated method stub
			if(calling_activity.equals("ServiceSearch")){
				
			ServiceSearch.linlaHeaderProgress2.setVisibility(View.GONE);
			ServiceSearch.stylist_spinner.setAdapter(result);
			if(result.getCount()==0){
				ServiceSearch.show_message("Sorry, no stylist available on this date.");
			}else{
			new TimeDialogTask("ServiceSearch").execute();
			}
			}else if(calling_activity.equals("ServiceSearchFragment")){
				
				ServiceSearchFragment.linlaHeaderProgress2.setVisibility(View.GONE);
				ServiceSearchFragment.stylist_spinner.setAdapter(result);
				if(result.getCount()==0){
					ServiceSearch.show_message("Sorry, no stylist available on this date.");
				}else{
				new TimeDialogTask("ServiceSearchFragment").execute();
				}
			}else if(calling_activity.equals("SalonProfileTwo")){
				
				SalonDescriptionFragment.linlaHeaderProgress2.setVisibility(View.GONE);
				SalonDescriptionFragment.stylist_spinner.setAdapter(result);
				if(result.getCount()==0){
					ServiceSearch.show_message("Sorry, no stylist available on this date.");
				}else{
				new TimeDialogTask("SalonProfileTwo").execute();
				}
			}else if(calling_activity.equals("DateSearchResult")){
				
				DateSearchResult.linlaHeaderProgress2.setVisibility(View.GONE);
				DateSearchResult.stylist_spinner.setAdapter(result);	
				
			}
			
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			if(calling_activity.equals("ServiceSearch")){
			ServiceSearch.linlaHeaderProgress2.setVisibility(View.VISIBLE);
			}else if(calling_activity.equals("ServiceSearchFragment")){
				ServiceSearchFragment.linlaHeaderProgress2.setVisibility(View.VISIBLE);
			}else if(calling_activity.equals("SalonProfileTwo")){
				SalonDescriptionFragment.linlaHeaderProgress2.setVisibility(View.VISIBLE);
			}else if(calling_activity.equals("DateSearchResult")){
				DateSearchResult.linlaHeaderProgress2.setVisibility(View.VISIBLE);
			}
			
			// TODO Auto-generated method stub
			super.onPreExecute();
		}


}
