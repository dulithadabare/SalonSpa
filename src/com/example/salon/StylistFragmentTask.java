package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class StylistFragmentTask extends AsyncTask<String,Integer,StylistFragmentAdapter>{

	@Override
	protected StylistFragmentAdapter doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		ArrayList<HashMap<String, String>> StylistList = new ArrayList<HashMap<String, String>>();
	     			jsonParser json = new jsonParser(StylistSearchFragment.CON);
	     		if(arg0[0].equals("DateFlow")){
	     			json.SetUrl(JsonUrl.ApiUrl+"calendar_api/get_employee_list_free_time_at_slected_date"); 	
	     			
	     		}else{
	     		 json.SetUrl(JsonUrl.ApiUrl+"Search_api/search_by_styler/format/json"); 
	     		}
                 //json.SetParams("keyword","s");
	     		 if(arg0[0].equals("SalonProfile")){
	     			 
                    json.SetParams("BranchId",SalonSearch.BranchID);
                    json.SetParams("companyId",SalonSearch.MapID);
                    
                }else if(arg0[0].equals("SalonProfileTwo")){
                	
                	json.SetParams("BranchId",SalonSearch.BranchID);
                    json.SetParams("companyId",SalonSearch.MapID);
                    json.SetParams("ServiceID",ServiceSearch.serviceID);
                    
                }else if(arg0[0].equals("DateFlow")){
                	
                	json.SetParams("BranchId",SalonSearch.BranchID);
                    json.SetParams("companyId",SalonSearch.MapID);
                    json.SetParams("ServiceID",ServiceSearch.serviceID);
                    json.SetParams("SelectedDate",DateSearch.booking_date);
                }
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
		
           //Element e = (Element) nl.item(i);
           // adding each child node to HashMap key => value
           map.put(Stylist.KEY_FIRST_NAME, jsonObject.getString(Stylist.KEY_FIRST_NAME));
           map.put( Stylist.KEY_LAST_NAME, jsonObject.getString( Stylist.KEY_LAST_NAME));
           map.put(Stylist.KEY_STYLIST_ID, jsonObject.getString( Stylist.KEY_STYLIST_ID));
           map.put(Stylist.KEY_STYLIST_DES, jsonObject.getString( Stylist.KEY_STYLIST_DES));
           map.put(Stylist.KEY_STYLIST_JOBTITLE, jsonObject.getString( Stylist.KEY_STYLIST_JOBTITLE));
           map.put(Stylist.KEY_STYLIST_THUMB_URL,jsonObject.getString( Stylist.KEY_STYLIST_THUMB_URL));
           map.put("companyDetails", jsonObject.getString("0"));
      
      
				StylistList.add(map);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
				// adding HashList to ArrayList
				
			}
			
			
			// Getting adapter by passing xml data ArrayList
			  
	  		StylistFragmentAdapter StylistAdapter=new StylistFragmentAdapter(StylistSearchFragment.CON, StylistList);   
		    
		return StylistAdapter;
	}

	@Override
	protected void onPostExecute(StylistFragmentAdapter result) {
		// TODO Auto-generated method stub
		StylistSearchFragment.linlaHeaderProgress.setVisibility(View.GONE);
		StylistSearchFragment.StylistAdapter=result;
		StylistSearchFragment.list.setAdapter(result);
		if(result.getCount()==0){
			StylistSearchFragment.no_result_layout.setVisibility(View.VISIBLE);
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		StylistSearchFragment.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}


}
