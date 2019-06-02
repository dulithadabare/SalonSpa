package com.example.salon;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;

public class UserUpcomingBookingTask extends AsyncTask<String,Integer,String>{
    
	 String bookingStatus="NA";
	
	UserUpcomingFragment activity;
	UserUpcomingBookingTask(UserUpcomingFragment activity2){
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
	     			
	     			 json.SetUrl(JsonUrl.ApiUrl+"bookingcart/bsession_update_existing_booking/format/json"); 
	     		    
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
	     	           json.SetParams("SartTime",UserUpcomingFragment.selected_startTime);
	     	           json.SetParams("Endtime",UserUpcomingFragment.selected_endTime);
	     	           json.SetParams("SheduleId",Appointment.AppointmentId);
     
	               
	             //    json.SetParams("CompanyID","1");
	                 String Jsondata= json.ExecuteRequest();
	                // JSONArray jsonArray=    json.ConvertToJsonobj(Jsondata);
	                 try {
	     		    	 JSONObject jsonObj = new JSONObject(Jsondata);
	     		    	bookingStatus=  jsonObj.get("status").toString();
	                    
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
		UserUpcomingFragment.mDialog.dismiss();
	//	ServiceSearchFragment.ServiceAdapter=result;
		//ServiceSearchFragment.list.setAdapter(result);
		 if(result.equals("error")){
			 activity.show_toast("Couldn't reschedule appointment");
    	  
      }else{
   	   
    	  new UserUpcomingTask().execute();
   	   activity.show_toast("Appointment was successfully resceduled!");
    	  
      }
          
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		
		UserUpcomingFragment.mDialog.show();
		UserUpcomingFragment.mDialog.setMessage("Booking your appointment...");
	
		// TODO Auto-generated method stub
		super.onPreExecute();
	}


}
