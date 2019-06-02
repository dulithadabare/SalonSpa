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

public class SalonDialogTask extends AsyncTask<String,Integer,ArrayAdapter<String>>{
       
	ArrayList<String> dialog_list = new ArrayList<String>();
	HashMap<String, String> dialog_salon = new HashMap<String, String>();
	static ArrayList<HashMap<String, String>> salonList;

		@Override
		protected ArrayAdapter<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			 
			 if (android.os.Build.VERSION.SDK_INT > 9) {
        		 
		        	
	  	        	StrictMode.ThreadPolicy policy = 
	  	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  	        	StrictMode.setThreadPolicy(policy);
	  	        	}
	  	     		salonList = new ArrayList<HashMap<String, String>>();
	  	     			jsonParser json = new jsonParser(StylistSearch.CON);
	  	     			json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_sallon/format/json");
	  	       		
	  	     			String   Jsondata= json.ExecuteRequest();
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
	  				map.put(Salon.KEY_ID, jsonObject.getString( Salon.KEY_ID));
	  				map.put(Salon.KEY_SALON_NAME, jsonObject.getString( Salon.KEY_SALON_NAME));
	  				map.put(Salon.KEY_MAP_ID, jsonObject.getString( Salon.KEY_MAP_ID));
	  				
	  				salonList.add(map);
	  				} catch (JSONException e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();
	  				}
	  				// adding HashList to ArrayList
	  				
	  			}
	  			for (int i = 0; i<salonList.size(); i++) {
	  				
	  				dialog_salon=salonList.get(i);
					dialog_list.add(dialog_salon.get(Salon.KEY_SALON_NAME));
	  				
	  			}
	  			
	  			
	  			// Getting adapter by passing xml data ArrayList
	  			StylistSearch.dialog_list.addAll(dialog_list);
	  			StylistSearch.original_data.addAll(dialog_list);
	  			ArrayAdapter<String> adapter = new ArrayAdapter<String>(StylistSearch.CON,
	  			     android.R.layout.simple_list_item_1, dialog_list);     
	  		
			return adapter;
		}

		@Override
		protected void onPostExecute(ArrayAdapter<String> result) {
			// TODO Auto-generated method stub
			StylistSearch.linlaHeaderProgress2.setVisibility(View.GONE);
			StylistSearch.adapter=result;
			StylistSearch.salon_list.setAdapter(result);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			StylistSearch.linlaHeaderProgress2.setVisibility(View.VISIBLE);
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

}
