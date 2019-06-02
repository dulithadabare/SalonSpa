package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class StylistAdapterTask extends AsyncTask<String,Integer,StylistAdapter>{
	
    static ArrayList<HashMap<String, String>> StylistList;
    String	Jsondata;
	@Override
	protected StylistAdapter doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		StylistList = new ArrayList<HashMap<String, String>>();
	     			jsonParser json = new jsonParser(StylistSearch.CON);
		     		
	                 //json.SetParams("keyword","s");
		     		 if(arg0[0].equals("Salon_Selected")){
		     			json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_styler/format/json"); 
	                    json.SetParams("BranchId",SalonSearch.BranchID);
	                    json.SetParams("companyId",SalonSearch.MapID);
	                    Jsondata= json.ExecuteRequest();
	                }else{
	                	json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_styler/format/json");
		                    Jsondata= json.ExecuteRequest();
	                }
	             //    json.SetParams("CompanyID","1");
	                  
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
					String jsonString = jsonObject.getString("0");
					
					JSONArray jsonArray1= new JSONArray(jsonString);
					JSONObject custom_json_obj=jsonArray1.getJSONObject(0);
					
				
			
				HashMap<String, String> map = new HashMap<String, String>();
				//Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
		
           //Element e = (Element) nl.item(i);
           // adding each child node to HashMap key => value
           map.put(Stylist.KEY_FIRST_NAME, jsonObject.getString(Stylist.KEY_FIRST_NAME));
           map.put( Stylist.KEY_LAST_NAME, jsonObject.getString( Stylist.KEY_LAST_NAME));
           map.put(Stylist.KEY_STYLIST_ID, jsonObject.getString( Stylist.KEY_STYLIST_ID));
           map.put(Stylist.KEY_STYLIST_BRANCH_ID,jsonObject.getString( Stylist.KEY_STYLIST_BRANCH_ID));
           map.put(Stylist.KEY_STYLIST_JOBTITLE, jsonObject.getString( Stylist.KEY_STYLIST_JOBTITLE));
           map.put(Stylist.KEY_STYLIST_THUMB_URL,jsonObject.getString( Stylist.KEY_STYLIST_THUMB_URL));
           map.put(Stylist.KEY_STYLIST_COMPANY,custom_json_obj.getString( Stylist.KEY_STYLIST_COMPANY));
           map.put(Stylist.KEY_MAP_ID,custom_json_obj.getString( Stylist.KEY_MAP_ID));
           
           
           map.put("companyDetails", jsonObject.getString("0"));
      
      
				StylistList.add(map);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
				// adding HashList to ArrayList
				
			}
			
			
			// Getting adapter by passing xml data ArrayList
			  
			StylistAdapter StylistAdapter=new StylistAdapter(StylistSearch.CON, StylistList);   
		    
		return StylistAdapter;
	}

	@Override
	protected void onPostExecute(StylistAdapter result) {
		// TODO Auto-generated method stub
		StylistSearch.linlaHeaderProgress.setVisibility(View.GONE);
		StylistSearch.StylistAdapter=result;
		StylistSearch.list.setAdapter(result);
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		StylistSearch.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
