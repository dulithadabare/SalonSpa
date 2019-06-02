package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class UnifiedSalonTask extends AsyncTask<String,Integer,UnifiedSalonFragmentAdapter>{

static ArrayList<HashMap<String, String>> salonList;
	@Override
	protected UnifiedSalonFragmentAdapter doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		 
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}
            salonList = new ArrayList<HashMap<String, String>>();
     			jsonParser json = new jsonParser(UnifiedSalonFragment.CON);
     			json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_sallon/format/json");
	     		String Jsondata= json.ExecuteRequest();
     	
     		    JSONArray jsonArray=json.ConvertToJsonobj(Jsondata);
     		
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
      
		UnifiedSalonFragmentAdapter adapter=new UnifiedSalonFragmentAdapter(UnifiedSalonFragment.CON, salonList);   
		    
		return adapter;
	}

	@Override
	protected void onPostExecute(UnifiedSalonFragmentAdapter result) {
		// TODO Auto-generated method stub
		UnifiedSalonFragment.linlaHeaderProgress.setVisibility(View.GONE);
		UnifiedSalonFragment.adapter=result;
		UnifiedSalonFragment.list.setAdapter(result);
		UnifiedSalonFragment.adapter.filter(UnifiedSalonFragment.QUERY);
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		UnifiedSalonFragment.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}


}
