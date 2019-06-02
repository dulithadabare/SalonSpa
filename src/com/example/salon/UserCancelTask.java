package com.example.salon;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;

public class UserCancelTask extends AsyncTask<String,Integer,String>{
    
	String CancelStatus="NA";
	
	UserUpcomingFragment activity;
	UserCancelTask(UserUpcomingFragment activity2){
		 this.activity=activity2;
	 }
	@Override
	protected String doInBackground(String... BranchDetails) {
		// TODO Auto-generated method stub
		 
	
		
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		
	     			jsonParser json = new jsonParser(UserUpcomingFragment.CON);
	     			
	     			 json.SetUrl(JsonUrl.ApiUrl+"user_api/cancle_user_appoinments/format/json"); 
	     		    
	     	           json.SetParams("appointment_Id",Appointment.AppointmentId);
	               
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
	                // JSONArray jsonArray=    json.ConvertToJsonobj(Jsondata);
	                 try {
	     		    	 JSONObject jsonObj = new JSONObject(Jsondata);
	     		    	CancelStatus=  jsonObj.get("status").toString();
	                    
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
	     		   
		    
		return CancelStatus;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		UserUpcomingFragment.mDialog.dismiss();
	//	ServiceSearchFragment.ServiceAdapter=result;
		//ServiceSearchFragment.list.setAdapter(result);
		 if(CancelStatus.equals("error")){
			 activity.show_toast("Appointmnet could not be cancelled.Please try again.");
    	  
      }else{
   	   
   	   activity.show_toast("The Appointment was cancelled.");
    	new UserUpcomingTask().execute();  
      }
          
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		
		UserUpcomingFragment.mDialog.show();
		UserUpcomingFragment.mDialog.setMessage("Cancelling your appointment...");
	
		// TODO Auto-generated method stub
		super.onPreExecute();
	}


}
