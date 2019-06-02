package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;

public class SalonDesUserCreateTask extends AsyncTask<String,Integer,String>{

	 String errorMessage="error";
	 SalonDescriptionFragment activity;
	 SalonDesUserCreateTask(SalonDescriptionFragment activity2){
		 this.activity=activity2;
	 }
	@Override
	protected String doInBackground(String... BranchDetails) {
		// TODO Auto-generated method stub
		 String userCreate="test";
		
	
		
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		
	     			jsonParser json = new jsonParser(SalonDescriptionFragment.CON);
	     			
	     			 json.SetUrl(JsonUrl.ApiUrl+"user_api/create_user/format/json"); 
	     		    
	     	           json.SetParams("email",ActivityMain.selected_account_name);
	     	           json.SetParams("fname",ActivityMain.salonspa_user_givenname);
	     	           json.SetParams("lname",ActivityMain.salonspa_user_familyname);
	     	           json.SetParams("gender",ActivityMain.salonspa_user_gender);
	     	           json.SetParams("picture_url",ActivityMain.salonspa_user_picture);
	     	           json.SetParams("googleId",ActivityMain.salonspa_user_googleid);
	     	           json.SetParams("mobNo",ActivityMain.user_phone_number);
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
	                // JSONArray jsonArray=    json.ConvertToJsonobj(Jsondata);
	     		     try {
	     		    	 JSONObject jsonObj = new JSONObject(Jsondata);
	                      userCreate=  jsonObj.get("data").toString();
	                      errorMessage=  jsonObj.get("error").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			//XMLParser parser = new XMLParser(this);
			//String xml = parser.getXmlFromUrl(URL); // getting XML from URL
			//Document doc = parser.getDomElement(xml); // getting DOM element
			
			//NodeList nl = doc.getElementsByTagName("DoctorInfo");
			// looping through all song nodes <song>
			
			
			
			// Getting adapter by passing xml data ArrayList
	        
			  
		//	ServiceAdapter ServiceAdapter=new ServiceAdapter(ServiceSearchFragment.CON, ServiceList);   
	     		   
		    
		return userCreate;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
	//	ServiceSearchFragment.ServiceAdapter=result;
		//ServiceSearchFragment.list.setAdapter(result);
		 if(result.equals("0")){
			 activity.show_toast(errorMessage);
      	  
      	  
        }else{
      	  ActivityMain.salonspa_user_id=result;
      	  new SalonDesBookingTask(activity).execute();
        }
            
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		
		
		SalonDescriptionFragment.mDialog.setMessage("Logging in..");
	
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
