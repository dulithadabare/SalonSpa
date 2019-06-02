package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;

public class UserHistoryTask extends AsyncTask<String,Integer,AppointmentHistoryAdapter>{
    
	static ArrayList<HashMap<String, String>> appointmentList;
	@Override
	protected AppointmentHistoryAdapter doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		 appointmentList = new ArrayList<HashMap<String, String>>();
	     			jsonParser json = new jsonParser(UserHistoryFragment.CON);
	     		    json.SetUrl(JsonUrl.ApiUrl+"user_api/get_user_appointments/format/json");
	     		 
	     		    json.SetParams("customer_id",ActivityMain.salonspa_user_id); 
            	    json.SetParams("today",ActivityMain.Today);
            	    json.SetParams("type","past");
                 
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
	            map.put(Appointment.KEY_APPOINTMENT_ID, jsonObject.getString(Appointment.KEY_APPOINTMENT_ID));
	            map.put( Appointment.KEY_BRANCH_ID, jsonObject.getString( Appointment.KEY_BRANCH_ID));
	            map.put(Appointment.KEY_BRANCH_NAME, jsonObject.getString(Appointment.KEY_BRANCH_NAME));
	            map.put(Appointment.KEY_CUSTOMER_ID, jsonObject.getString(Appointment.KEY_CUSTOMER_ID));
	            map.put(Appointment.KEY_EMPLOYEE_FNAME,jsonObject.getString(Appointment.KEY_EMPLOYEE_FNAME));
	            map.put(Appointment.KEY_EMPLOYEE_ID,jsonObject.getString(Appointment.KEY_EMPLOYEE_ID));
	            map.put(Appointment.KEY_MAP_ID,jsonObject.getString(Appointment.KEY_MAP_ID));
	            map.put(Appointment.KEY_EMPLOYEE_LNAME,jsonObject.getString(Appointment.KEY_EMPLOYEE_LNAME));
	            map.put(Appointment.KEY_SERVICE_DISCRIPTION,jsonObject.getString(Appointment.KEY_SERVICE_DISCRIPTION));
	            map.put(Appointment.KEY_SERVICE_DURATION,jsonObject.getString(Appointment.KEY_SERVICE_DURATION));
	            map.put(Appointment.KEY_SERVICE_ID,jsonObject.getString(Appointment.KEY_SERVICE_ID));
	            map.put(Appointment.KEY_SERVICE_NAME,jsonObject.getString(Appointment.KEY_SERVICE_NAME));
	            map.put(Appointment.KEY_SERVICE_PRICE,jsonObject.getString(Appointment.KEY_SERVICE_PRICE));
	            map.put(Appointment.KEY_SERVICE_THUMB_URL,jsonObject.getString(Appointment.KEY_SERVICE_THUMB_URL));
	            map.put(Appointment.KEY_START_TIME     ,jsonObject.getString(Appointment.KEY_START_TIME));
	       
				appointmentList.add(map);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// adding HashList to ArrayList
				
			}
			
			
			// Getting adapter by passing xml data ArrayList
			  
			AppointmentHistoryAdapter AppointmentHistoryAdapter=new AppointmentHistoryAdapter(UserHistoryFragment.CON, appointmentList);   
		    
		return AppointmentHistoryAdapter;
	}

	@Override
	protected void onPostExecute(AppointmentHistoryAdapter result) {
		// TODO Auto-generated method stub
		UserHistoryFragment.linlaHeaderProgress.setVisibility(View.GONE);
		UserHistoryFragment.AppointmentHistoryAdapter=result;
		UserHistoryFragment.list.setAdapter(result);
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		UserHistoryFragment.linlaHeaderProgress.setVisibility(View.VISIBLE);
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
