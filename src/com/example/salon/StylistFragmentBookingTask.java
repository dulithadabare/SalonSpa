package com.example.salon;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;

public class StylistFragmentBookingTask extends AsyncTask<String,Integer,String>{
    
	String Schedule_id="NA";
	
	StylistSearchFragment activity;
	 StylistFragmentBookingTask(StylistSearchFragment activity2){
		 this.activity=activity2;
	 }
	@Override
	protected String doInBackground(String... BranchDetails) {
		// TODO Auto-generated method stub
		 String bookingStatus="test";
	
		
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		
	     			jsonParser json = new jsonParser(StylistSearchFragment.CON);
	     			
	     			 json.SetUrl(JsonUrl.ApiUrl+"bookingcart/bsession_update_booking/format/json"); 
	     		    
	     	           json.SetParams("BranchId",SalonSearch.BranchID);
	     	           json.SetParams("CustomerID",ActivityMain.salonspa_user_id);
	     	           json.SetParams("EmployeeId",StylistSearch.stylistID);
	     	           json.SetParams("ServiceId",ServiceSearch.serviceID);
	     	           json.SetParams("Service_CompanyId",SalonSearch.MapID);
	     	           json.SetParams("branchName",SalonSearch.BranchName);
	     	           json.SetParams("Status","pencilledin");
	     	           json.SetParams("empFname",StylistSearch.selected_stylist_fname);
	     	           json.SetParams("empLname",StylistSearch.selected_stylist_lname);
	     	           json.SetParams("cost",ServiceSearch.selected_service_cost);
	     	           json.SetParams("SartTime",StylistSearchFragment.selected_startTime);
	     	           json.SetParams("Endtime",StylistSearchFragment.selected_endTime);
     
	               
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
	                // JSONArray jsonArray=    json.ConvertToJsonobj(Jsondata);
	     		     try {
	     		    	 JSONObject jsonObj = new JSONObject(Jsondata);
	     		    	if(jsonObj.has("shedule_Data")){
	                      bookingStatus=  jsonObj.getString("shedule_Data");
	                      JSONObject new_jsonObj = new JSONObject(bookingStatus);
	                      if(new_jsonObj.has("SheduleId")){
	                      Schedule_id = new_jsonObj.getString("SheduleId");
	                      }
	                    
	     		    	}
	                    
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
	     		   
		    
		return bookingStatus;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		StylistSearchFragment.mDialog.dismiss();
	//	ServiceSearchFragment.ServiceAdapter=result;
		//ServiceSearchFragment.list.setAdapter(result);
		 if(Schedule_id.equals("NA")){
			 activity.show_toast("Appointmnet Failed.Please Try Again.");
    	  
      }else{
   	   
   	   activity.show_toast("Booking was successfull! You can manage your appointments in the user profile.");
    	  
      }
          
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		
		
		StylistSearchFragment.mDialog.setMessage("Booking your appointment...");
	
		// TODO Auto-generated method stub
		super.onPreExecute();
	}


}
