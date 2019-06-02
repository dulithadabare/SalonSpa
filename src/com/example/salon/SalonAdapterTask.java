package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class SalonAdapterTask extends AsyncTask<String,Integer,LazyAdapter>{

static ArrayList<HashMap<String, String>> salonList;
String Jsondata;
	@Override
	protected LazyAdapter doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		 
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}
            salonList = new ArrayList<HashMap<String, String>>();
     			jsonParser json = new jsonParser(SalonSearch.CON);
     			
     			
     		if(arg0[0].equals("ServiceFlow")){
				 json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_sallon/format/json"); 
                 json.SetParams("ServiceID",ServiceSearch.serviceID);
                 
                 Jsondata= json.ExecuteRequest();
		
			}else if(arg0[0].equals("LocationFlow")){
				  json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_salon_by_geo_location/format/json");
				  json.SetParams("Longitude",SalonSearch.Longitude);
				  json.SetParams("Latitude",SalonSearch.Latitude);
				  json.SetParams("radius","5");
				  
				  Jsondata= json.ExecuteRequest();
			}else{
				json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_sallon/format/json");
     		
     		    Jsondata= json.ExecuteRequest();
			}
				
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
			map.put(Salon.KEY_MAP_ID, jsonObject.getString( Salon.KEY_MAP_ID));
			map.put(Salon.KEY_CITY, jsonObject.getString( Salon.KEY_CITY));
			map.put(Salon.KEY_COUNTRY, jsonObject.getString( Salon.KEY_COUNTRY));
			map.put(Salon.KEY_ZIPCODE, jsonObject.getString( Salon.KEY_ZIPCODE));
			map.put(Salon.KEY_PROVINCE, jsonObject.getString( Salon.KEY_PROVINCE));
			map.put(Salon.KEY_STREET1, jsonObject.getString( Salon.KEY_STREET1));
			map.put(Salon.KEY_STREET2, jsonObject.getString( Salon.KEY_STREET2));
			map.put(Salon.KEY_LONGITUDE, jsonObject.getString( Salon.KEY_LONGITUDE));
			map.put(Salon.KEY_LATITUDE, jsonObject.getString( Salon.KEY_LATITUDE));
			map.put(Salon.KEY_SALON_NAME, jsonObject.getString( Salon.KEY_SALON_NAME));
			map.put(Salon.KEY_BRANCH_EMAIL, jsonObject.getString( Salon.KEY_BRANCH_EMAIL));
			map.put(Salon.KEY_DISCRIPTION, jsonObject.getString( Salon.KEY_DISCRIPTION));
			map.put(Salon.KEY_THUMB_URL,jsonObject.getString( Salon.KEY_THUMB_URL));
			map.put(Salon.KEY_CATOGORY, jsonObject.getString( Salon.KEY_CATOGORY));
			salonList.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// adding HashList to ArrayList
			
		}
		
		
		// Getting adapter by passing xml data ArrayList
      
	  		LazyAdapter adapter=new LazyAdapter(SalonSearch.CON, salonList);   
		    
		return adapter;
	}

	@Override
	protected void onPostExecute(LazyAdapter result) {
		// TODO Auto-generated method stub
		SalonSearch.linlaHeaderProgress.setVisibility(View.GONE);
		SalonSearch.adapter=result;
		SalonSearch.list.setAdapter(result);
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		SalonSearch.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
