package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class ServiceAdapterTask extends AsyncTask<String,Integer,ServiceAdapter>{
	static ArrayList<HashMap<String, String>> ServiceList;
	String Jsondata;
	@Override
	protected ServiceAdapter doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		ServiceList = new ArrayList<HashMap<String, String>>();
	     	
	     			jsonParser json = new jsonParser(ServiceSearch.CON);

	     			if(arg0[0].equals("SalonFlow")){
	     				 json.SetUrl(JsonUrl.ApiUrl+"Search_api/get_Services_for_company/format/json"); 
		                 json.SetParams("BranchId",SalonSearch.BranchID);
		                 json.SetParams("companyId",SalonSearch.MapID);
		                  Jsondata= json.ExecuteRequest();
	     		
	     			}else if(arg0[0].equals("SalonFlow_Category_Selected")){
	     				 json.SetUrl(JsonUrl.ApiUrl+"Search_api/get_Services_for_company/format/json"); 
		                 json.SetParams("BranchId",SalonSearch.BranchID);
		                 json.SetParams("companyId",SalonSearch.MapID);
		                 json.SetParams("CatogoryId",ServiceSearch.selected_category_id);
		                  Jsondata= json.ExecuteRequest();
	     			}
	     			else if(arg0[0].equals("Stylist_Selected_Category_Selected")){
	     				 json.SetUrl(JsonUrl.ApiUrl+"Search_api/get_Services_for_company/format/json"); 
		                 json.SetParams("BranchId",SalonSearch.BranchID);
		                 json.SetParams("companyId",SalonSearch.MapID);
		                 json.SetParams("EmpID",StylistSearch.stylistID);
		                 json.SetParams("CatogoryId",ServiceSearch.selected_category_id);
		                  Jsondata= json.ExecuteRequest();
	     			}
	     			else if((arg0[0].equals("SalonFlow_StylistFragment")) || (arg0[0].equals("StylistFlow"))){
	     				json.SetUrl(JsonUrl.ApiUrl+"Search_api/get_Services_for_company/format/json"); 
		                json.SetParams("BranchId",SalonSearch.BranchID);
		                json.SetParams("companyId",SalonSearch.MapID);
		                json.SetParams("EmpID",StylistSearch.stylistID);
		                  Jsondata= json.ExecuteRequest();
	     			}else if(arg0[0].equals("Category_Selected")){
	     				json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_services/format/json"); 
		                json.SetParams("CatogoryId",ServiceSearch.selected_category_id);
		                
		                  Jsondata= json.ExecuteRequest();
	     			}
	     			else{
	     				json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_services/format/json");
	     				
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
				map.put(Service.KEY_SERVICE_ID, jsonObject.getString(Service.KEY_SERVICE_ID));
				map.put( Service.KEY_SERVICE_NAME, jsonObject.getString( Service.KEY_SERVICE_NAME));
				
				map.put( Service.KEY_SERVICE_DURATION, jsonObject.getString( Service.KEY_SERVICE_DURATION));
				if((arg0[0].equals("SalonFlow")) || (arg0[0].equals("SalonFlow_StylistFragment")) || (arg0[0].equals("StylistFlow"))){
					map.put( Service.KEY_SERVICE_PRICE,jsonObject.getString( Service.KEY_SERVICE_CUSTOM_PRICE));
					map.put( Service.KEY_SERVICE_DISCRIPTION, jsonObject.getString( Service.KEY_CUSTOM_SERVICE_DES));
					map.put( Service.KEY_SERVICE_DURATION, jsonObject.getString( Service.KEY_SERVICE_DURATION));
				}else{
				map.put( Service.KEY_SERVICE_PRICE,jsonObject.getString( Service.KEY_SERVICE_PRICE));
				map.put( Service.KEY_SERVICE_DISCRIPTION, jsonObject.getString( Service.KEY_SERVICE_DISCRIPTION));
				}
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
	        
			  
			ServiceAdapter ServiceAdapter=new ServiceAdapter(ServiceSearch.CON, ServiceList,arg0[0]);   
		    
		return ServiceAdapter;
	}

	@Override
	protected void onPostExecute(ServiceAdapter result) {
		// TODO Auto-generated method stub
		ServiceSearch.linlaHeaderProgress.setVisibility(View.GONE);
		ServiceSearch.ServiceAdapter=result;
		ServiceSearch.list.setAdapter(result);
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		ServiceSearch.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
