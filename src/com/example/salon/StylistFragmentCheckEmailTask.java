package com.example.salon;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;

public class StylistFragmentCheckEmailTask extends AsyncTask<String,Integer,String>{

	
	StylistSearchFragment activity;
	 StylistFragmentCheckEmailTask(StylistSearchFragment activity2){
		 this.activity=activity2;
	 }
	@Override
	protected String doInBackground(String... BranchDetails) {
		// TODO Auto-generated method stub
		 String userCheck="test";
	
		
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		
	     			jsonParser json = new jsonParser(StylistSearchFragment.CON);
	     			
	                 json.SetUrl(JsonUrl.ApiUrl+"user_api/check_user_email/format/json"); 
	                 //json.SetParams("keyword","s");
	                json.SetParams("email",ActivityMain.selected_account_name);
	                
	               
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
	                // JSONArray jsonArray=    json.ConvertToJsonobj(Jsondata);
	     		     try {
	     		    	 JSONObject jsonObj = new JSONObject(Jsondata);
	                      userCheck=  jsonObj.get("data").toString();
	                    
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
	     		   
		    
		return userCheck;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
	//	ServiceSearchFragment.ServiceAdapter=result;
		//ServiceSearchFragment.list.setAdapter(result);
		 if(result.equals("0")){
      	  new StylistFragmentGetUserDetails(activity, ActivityMain.selected_account_name, ActivityMain.SCOPE,
      			  ActivityMain.REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
      	 
      	  
        }else{
      	  ActivityMain.salonspa_user_id=result;
      	  new StylistFragmentBookingTask(activity).execute();
        }
            
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		
		
		StylistSearchFragment.mDialog.setMessage("Booking in progress");
		StylistSearchFragment.mDialog.show();
		
		// TODO Auto-generated method stub
		super.onPreExecute();
	}


}
