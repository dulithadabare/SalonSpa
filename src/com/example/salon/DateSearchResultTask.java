package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class DateSearchResultTask extends AsyncTask<String,Integer,DateSearchResultAdapter>{

static ArrayList<HashMap<String, String>> salonList;
	@Override
	protected DateSearchResultAdapter doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		 
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}
            salonList = new ArrayList<HashMap<String, String>>();
     			jsonParser json = new jsonParser(DateSearchResult.CON);
     			 json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_sallon/format/json");
     		 json.SetParams("ServiceID",ServiceSearch.serviceID);
     		 json.SetParams("timeslot",DateSearch.selected_time_day);
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
			map.put(Service.KEY_SERVICE_CUSTOM_PRICE, jsonObject.getString(Service.KEY_SERVICE_CUSTOM_PRICE));
			map.put(Service.KEY_SERVICE_DURATION, jsonObject.getString(Service.KEY_SERVICE_DURATION));
			salonList.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// adding HashList to ArrayList
			
		}
		
		
		// Getting adapter by passing xml data ArrayList
      
		DateSearchResultAdapter adapter=new DateSearchResultAdapter(DateSearchResult.CON, salonList);   
		    
		return adapter;
	}

	@Override
	protected void onPostExecute(DateSearchResultAdapter result) {
		// TODO Auto-generated method stub
		DateSearchResult.linlaHeaderProgress.setVisibility(View.GONE);
		DateSearchResult.DateSearchResultAdapter=result;
		DateSearchResult.list.setAdapter(result);
		if(result.getCount()==0){
			DateSearchResult.no_result_layout.setVisibility(View.VISIBLE);
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		DateSearchResult.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}


}
