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

public class ServiceDialogTask extends AsyncTask<String,Integer,ArrayAdapter<String>>{
    
	ArrayList<String> dialog_list = new ArrayList<String>();
	HashMap<String, String> dialog_salon = new HashMap<String, String>();
	static ArrayList<HashMap<String, String>> serviceList;

		@Override
		protected ArrayAdapter<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			 
			 if (android.os.Build.VERSION.SDK_INT > 9) {
     		 
		        	
	  	        	StrictMode.ThreadPolicy policy = 
	  	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  	        	StrictMode.setThreadPolicy(policy);
	  	        	}
	  	     		serviceList = new ArrayList<HashMap<String, String>>();
	  	     			jsonParser json = new jsonParser(DateSearch.CON);
	  	     		    json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_services/format/json");
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
	  				map.put(Service.KEY_SERVICE_ID, jsonObject.getString( Service.KEY_SERVICE_ID));
	  				map.put( Service.KEY_SERVICE_NAME, jsonObject.getString( Service.KEY_SERVICE_NAME));
	  				
	  				serviceList.add(map);
	  				} catch (JSONException e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();
	  				}
	  				// adding HashList to ArrayList
	  				
	  			}
	  			for (int i = 0; i<serviceList.size(); i++) {
	  				
	  				dialog_salon=serviceList.get(i);
					dialog_list.add(dialog_salon.get(Service.KEY_SERVICE_NAME));
	  				
	  			}
	  			
	  			
	  			// Getting adapter by passing xml data ArrayList
	  			DateSearch.dialog_list.addAll(dialog_list);
	  			DateSearch.original_data.addAll(dialog_list);
	  			ArrayAdapter<String> adapter = new ArrayAdapter<String>(DateSearch.CON,
	  					android.R.layout.simple_list_item_single_choice, dialog_list);     
	  		
			return adapter;
		}

		@Override
		protected void onPostExecute(ArrayAdapter<String> result) {
			// TODO Auto-generated method stub
			DateSearch.linlaHeaderProgress.setVisibility(View.GONE);
			DateSearch.adapter=result;
			DateSearch.service_list.setAdapter(result);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			DateSearch.linlaHeaderProgress.setVisibility(View.VISIBLE);
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

}
