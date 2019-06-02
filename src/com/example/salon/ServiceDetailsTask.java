package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class ServiceDetailsTask extends AsyncTask<String,Integer,String>{

	@Override
	protected String doInBackground(String... BranchDetails) {
		// TODO Auto-generated method stub
		
		
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		ArrayList<HashMap<String, String>> ServiceList = new ArrayList<HashMap<String, String>>();
	     			jsonParser json = new jsonParser(SalonDescriptionFragment.CON);
	     			
	                 json.SetUrl("http://192.168.6.7/Aggrigator/index.php/api/Search_api/get_Services_for_company/format/json"); 
	                 //json.SetParams("keyword","s");
	                json.SetParams("BranchId",SalonSearch.BranchID);
	                json.SetParams("companyId",SalonSearch.MapID);
	                json.SetParams("ServiceId",ServiceSearch.serviceID);
	                
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
	                 JSONArray jsonArray=    json.ConvertToJsonobj(Jsondata);
	     		
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
				map.put(Service.KEY_SERVICE_ID, jsonObject.getString(Service.KEY_SERVICE_ID));
				map.put( Service.KEY_SERVICE_NAME, jsonObject.getString( Service.KEY_SERVICE_NAME));
				map.put( Service.KEY_SERVICE_DISCRIPTION, jsonObject.getString( Service.KEY_CUSTOM_SERVICE_DES));
				
				ServiceSearch.selected_service_cost=jsonObject.getString( Service.KEY_SERVICE_CUSTOM_PRICE);
				ServiceSearch.selected_service_duration=jsonObject.getString( Service.KEY_SERVICE_DURATION);
				
				map.put( Service.KEY_SERVICE_DURATION, jsonObject.getString( Service.KEY_SERVICE_DURATION));
				map.put( Service.KEY_SERVICE_PRICE,jsonObject.getString( Service.KEY_SERVICE_CUSTOM_PRICE));
				map.put(Service.KEY_SERVICE_THUMB_URL, jsonObject.getString(Service.KEY_SERVICE_THUMB_URL));
				map.put( Service.KEY_SERVICE_PADTIME, jsonObject.getString( Service.KEY_SERVICE_PADTIME));
				map.put(Service.KEY_SERVICE_COLOUR, jsonObject.getString(Service.KEY_SERVICE_COLOUR));
				map.put(Service.KEY_SERVICE_CATEGORYID, jsonObject.getString(Service.KEY_SERVICE_CATEGORYID));
				map.put(Service.KEY_SERVICE_CATEGORY_NAME, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_NAME));
			//	map.put(Service.KEY_SERVICE_CATEGORY_DES, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_DES));
			//	map.put(Service.KEY_SERVICE_CATEGORY_ITEM, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_ITEM));
				map.put(Service.KEY_SERVICE_CATEGORY_URL, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_URL));
				ServiceList.add(map);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// adding HashList to ArrayList
				
			}
			
			
			// Getting adapter by passing xml data ArrayList
	        
			  
		//	ServiceAdapter ServiceAdapter=new ServiceAdapter(ServiceSearchFragment.CON, ServiceList);   
		    
		return "Done";
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		SalonDescriptionFragment.linlaHeaderProgress.setVisibility(View.GONE);
	//	ServiceSearchFragment.ServiceAdapter=result;
		//ServiceSearchFragment.list.setAdapter(result);
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		SalonDescriptionFragment.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
