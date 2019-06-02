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

public class CategoryDialogTask extends AsyncTask<String,Integer,ArrayAdapter<String>>{
    
	ArrayList<String> dialog_list = new ArrayList<String>();
	HashMap<String, String> dialog_category = new HashMap<String, String>();
	static ArrayList<HashMap<String, String>> categoryList;

		@Override
		protected ArrayAdapter<String> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			 
			 if (android.os.Build.VERSION.SDK_INT > 9) {
     		 
		        	
	  	        	StrictMode.ThreadPolicy policy = 
	  	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  	        	StrictMode.setThreadPolicy(policy);
	  	        	}
	  	     		categoryList = new ArrayList<HashMap<String, String>>();
	  	     			jsonParser json = new jsonParser(StylistSearch.CON);
	  	     		String Jsondata= 	json.getJsonFromUrl("http://192.168.6.7/Aggrigator/index.php/api/Search_api/get_Service_Categories/format/json"); 
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
	  				map.put("CatogoryName", jsonObject.getString("CatogoryName"));
	  				map.put("CatogoryId", jsonObject.getString("CatogoryId"));
	  				categoryList.add(map);
	  				} catch (JSONException e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();
	  				}
	  				// adding HashList to ArrayList
	  				
	  			}
	  			for (int i = 0; i<categoryList.size(); i++) {
	  				
	  				dialog_category=categoryList.get(i);
					dialog_list.add(dialog_category.get("CatogoryName"));
	  				
	  			}
	  			
	  			
	  			// Getting adapter by passing xml data ArrayList
	  			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ServiceSearch.CON,
	  			     android.R.layout.simple_list_item_1, dialog_list);     
	  		
			return adapter;
		}

		@Override
		protected void onPostExecute(ArrayAdapter<String> result) {
			// TODO Auto-generated method stub
			ServiceSearch.linlaHeaderProgress2.setVisibility(View.GONE);
			ServiceSearch.category_list.setAdapter(result);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			ServiceSearch.linlaHeaderProgress2.setVisibility(View.VISIBLE);
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

}
