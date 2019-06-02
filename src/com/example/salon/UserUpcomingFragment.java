package com.example.salon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UserUpcomingFragment extends Fragment{
	
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	static NextAppointmentAdapter NextAppointmentAdapter;
	static LinearLayout linlaHeaderProgress;
	static LinearLayout linlaHeaderProgress2;
	static RelativeLayout no_result_layout;
	static ListView list;
	static Context CON;
	Context con;
	static ProgressDialog mDialog;
	static AlertDialog.Builder NoticeDialog;
	
	static Spinner time_spinner;
	static int date_picker_day;
	static int date_picker_month;
	static int date_picker_year;
	static String selected_date;
	static int year;
	static int month;
	static int day;
	
	TextView icon_date;
	TextView icon_ym;
	TextView icon_day;
   
	
	static String selected_time;
	static String selected_endTime;
	static String selected_startTime;
	static int spinner_selected_pos;
	
	public static final UserUpcomingFragment newInstance(String message)

	{
		UserUpcomingFragment f = new UserUpcomingFragment();
	Bundle bdl = new Bundle(1);
	bdl.putString(EXTRA_MESSAGE, message);
	f.setArguments(bdl);
	return f;
	}
	
	 public String get_day_of_week(int date){
			String day="NA";
			switch (date) {
			
			case Calendar.MONDAY:
			  day= "Monday";
			  break;
			case Calendar.TUESDAY:
				day= "Tuesday";
				break;
			case Calendar.WEDNESDAY:
				day= "Wednesday";
				break;
			case Calendar.THURSDAY:
				day= "Thursday";
				break;
			case Calendar.FRIDAY:
				day= "Friday";
				break;
			case Calendar.SATURDAY:
				day= "Saturday";
				break;
			case Calendar.SUNDAY:
				day= "Sunday";	    
				break;
				  
			}
			return day;
		}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//String message = getArguments().getString(EXTRA_MESSAGE);
		View v = inflater.inflate(R.layout.user_profile_fragment, container, false);
		linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);
		 no_result_layout    = (RelativeLayout) v.findViewById(R.id.no_result_layout);
		
     /*  book_button.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){
				
				Intent tute_intent= new Intent("com.example.salon.SERVICESEARCH");
				startActivity(tute_intent);
			
			}
			
		});*/
		
		
	
		
		list=(ListView)v.findViewById(R.id.listall);
		con = getActivity();
		CON = getActivity();
		
		NoticeDialog = new AlertDialog.Builder(con);
		NoticeDialog.setCancelable(false);		
		//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
		
		NoticeDialog.setMessage("Sorry no appointments found.");
		 
		
		NoticeDialog.setTitle("Hair Treatment");
		NoticeDialog
               .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the positive button event back to the host activity
                	   Intent tute_intent= new Intent("android.intent.action.MAIN");
       				startActivity(tute_intent);
                       }
               });
		
        
		if(ActivityMain.salonspa_user_id.equals("")){
			 mDialog = new ProgressDialog(con);
      	     mDialog.setCancelable(false);
			new UserCheckEmailTask(UserUpcomingFragment.this).execute();
		}else{
			new UserUpcomingTask().execute();
		}
		
		
       
        list.setOnItemClickListener(new OnItemClickListener() {
            
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Appointment.AppointmentId = (String) ((TextView)arg1.findViewById(R.id.AppointmentID)).getText();
				ServiceSearch.serviceName = (String) ((TextView)arg1.findViewById(R.id.Name)).getText();
				for(HashMap<String, String> i:UserUpcomingTask.appointmentList){
					if(i.get(Appointment.KEY_APPOINTMENT_ID).equals(Appointment.AppointmentId)){

		     	           SalonSearch.BranchID=i.get(Appointment.KEY_BRANCH_ID);
		     	           StylistSearch.stylistID=i.get(Appointment.KEY_EMPLOYEE_ID);
		     	           ServiceSearch.serviceID=i.get(Appointment.KEY_SERVICE_ID);
		     	           SalonSearch.MapID=i.get(Appointment.KEY_MAP_ID);
		     	           SalonSearch.BranchName=i.get(Appointment.KEY_BRANCH_NAME);
		     	           StylistSearch.selected_stylist_fname=i.get(Appointment.KEY_EMPLOYEE_FNAME);
		     	           StylistSearch.selected_stylist_lname=i.get(Appointment.KEY_EMPLOYEE_LNAME);
		     	           StylistSearch.stylistName =  StylistSearch.selected_stylist_fname+" "+StylistSearch.selected_stylist_lname;
		     	           ServiceSearch.selected_service_cost=i.get(Appointment.KEY_SERVICE_PRICE);
		     	           ServiceSearch.selected_service_duration=i.get(Appointment.KEY_SERVICE_DURATION);
					}
					
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(con);
				//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
				String[] words = {"Reschedule", "Cancel Appointment", "Back"};
				 
				
				builder.setTitle(ServiceSearch.serviceName);
				builder.setItems(words, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		               // The 'which' argument contains the index position
		               // of the selected item
		            	   
		            	   switch(which){
		            	   case 0:
		       		                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		       		   					LayoutInflater inflater = getActivity().getLayoutInflater();
		       		   					View view=inflater.inflate(R.layout.stylist_booking_dialog, null);
		       		   					linlaHeaderProgress2 = (LinearLayout) view.findViewById(R.id.linlaHeaderProgress);
		       		   					//Spinner spinner1 =(Spinner) view.findViewById(R.id.spinner1);
		       		   					 icon_date   = (TextView) view.findViewById(R.id.it_empty_from_date_number);
		       		   					 icon_ym   = (TextView) view.findViewById(R.id.it_empty_from_date_ym);
		       		   					 icon_day   = (TextView) view.findViewById(R.id.it_empty_from_date_day);
		       		   					 LinearLayout date_layout =(LinearLayout) view.findViewById(R.id.date_icon_layout);
		       		   					 time_spinner=(Spinner) view.findViewById(R.id.free_time_spinner);
		       		   					 TextView stylist_name = (TextView) view.findViewById(R.id.stylist_name);
		       		   					 TextView service_name = (TextView) view.findViewById(R.id.service_name);
		       		   					 TextView service_cost = (TextView) view.findViewById(R.id.service_cost);

		       		   					 stylist_name.setText(StylistSearch.stylistName);
		       		   					 service_name.setText(ServiceSearch.serviceName);
		       		   					 service_cost.setText("Rs."+ServiceSearch.selected_service_cost);
		       		   					 DisplayMetrics displaymetrics = new DisplayMetrics();
		       		   					 getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		       		   					 int width = displaymetrics.widthPixels;
		       		   					 service_name.setWidth(width/3);
		       		   					 
		       		   					  final Calendar c = Calendar.getInstance();
		       		   	                   year = c.get(Calendar.YEAR);
		       		   	                   month = c.get(Calendar.MONTH);
		       		   	                   day = c.get(Calendar.DAY_OF_MONTH);
		       		   	                  
		       		   	                   final long getTime= c.getTimeInMillis();
		       		   	             
		       		   	                   int correct_month=month+1;
		       		   		                  
		       		   	                   int day_of_week  = c.get(Calendar.DAY_OF_WEEK);
		       		   	                   
		       		   	                      icon_date.setText(String.valueOf(day));
		       		                          icon_ym.setText(String.valueOf(correct_month)+"/"+String.valueOf(year));
		       		   	                      icon_day.setText(get_day_of_week(day_of_week));
		       		   		                  selected_date=year+"-"+correct_month+"-"+day;
		       		   					  
		       		   	                 
		       		   	                 new TimeDialogTask("UserUpcomingFragment").execute();
		       		   	                 
		       		   	                 date_layout.setOnClickListener(new OnClickListener(){

		       		   		             		@Override
		       		   		             		public void onClick(View arg0) {
		       		   		             			// TODO Auto-generated method stub
		       		   		             			AlertDialog.Builder builder = new AlertDialog.Builder(con);
		       		   		            			LayoutInflater inflater = getActivity().getLayoutInflater();
		       		   		            			View view=inflater.inflate(R.layout.datepicker_dialog, null);
		       		   		            			DatePicker date_picker =(DatePicker) view.findViewById(R.id.date_picker);
		       		   		            			
		       		   		            		    date_picker.setMinDate(getTime);
		       		   		            			date_picker.init(year, month, day, new DatePicker.OnDateChangedListener() {
		       		   		            				
		       		   		            				@Override
		       		   		            				public void onDateChanged(DatePicker view, int year, int monthOfYear,
		       		   		            						int dayOfMonth) {
		       		   		            					// TODO Auto-generated method stub
		       		   		            					 int month=monthOfYear+1;
		       		   		            					 Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth); // Note that Month value is 0-based. e.g., 0 for January.
		       		   		            			        	int week_date = calendar.get(Calendar.DAY_OF_WEEK);
		       		   		            					 icon_date.setText(String.valueOf(dayOfMonth));
		       		   		            			            icon_ym.setText(String.valueOf(month)+"/"+String.valueOf(year));
		       		   		            			            icon_day.setText(get_day_of_week(week_date));
		       		   		            					   
		       		   		            			            selected_date= year+"-"+month+"-"+dayOfMonth;
		       		   		            			          
		       		   		            					
		       		   		            				}
		       		   		            			});
		       		   		            			builder.setView(view);
		       		   		            		   // builder.setMessage("test");
		       		   		            			//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
		       		   		            			
		       		   		            			builder.setTitle("Select Date");
		       		   		            	        builder
		       		   		            	               .setPositiveButton("Select", new DialogInterface.OnClickListener() {
		       		   		            	                   public void onClick(DialogInterface dialog, int id) {
		       		   		            	                       // Send the positive button event back to the host activity
		       		   		            	                 new TimeDialogTask("UserUpcomingFragment").execute();
		       		   		            	                       }
		       		   		            	               })
		       		   		            	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		       		   		            	                   public void onClick(DialogInterface dialog, int id) {
		       		   		            	                       // Send the negative button event back to the host activity
		       		   		            	                      
		       		   		            	                   }
		       		   		            	               });
		       		   		            	        builder.show();
		       		   		             		}});
		       		   					  
		       		   					time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

		       		   						@Override
		       		   						public void onItemSelected(AdapterView<?> arg0, View arg1,
		       		   								int arg2, long arg3) {
		       		   							// TODO Auto-generated method stub
		       		   							selected_time = (String) ((TextView)arg1).getText();
		       		   							
		       		   							
		       		   						//	Toast.makeText(con, selected_time, Toast.LENGTH_SHORT).show();
		       		   							
		       		   						}

		       		   						@Override
		       		   						public void onNothingSelected(AdapterView<?> arg0) {
		       		   							// TODO Auto-generated method stub
		       		   							
		       		   						}
		       		   					});
		       		   					
		       		   					
		       		   					//spinner1.setAdapter(dataAdapter);
		       		   					
		       		   					builder.setView(view);
		       		   					
		       		   			        builder.setPositiveButton("Book", new DialogInterface.OnClickListener() {
		       		   			                   public void onClick(DialogInterface dialog, int id) {
		       		   			                       // Send the positive button event back to the host activity
		       		   			                	   String endTime = GetIncreasedTime(selected_time,Integer.parseInt(ServiceSearch.selected_service_duration));
		       		   			                	   selected_endTime= selected_date+" "+endTime+":00";
		       		   			                	   selected_startTime=selected_date+" "+selected_time+":00";
		       		   			                	   
		       		   			                	   
		       		   			                	   mDialog = new ProgressDialog(con);
		       		   			                	   mDialog.setCancelable(false);
		       		   			                	   new UserUpcomingBookingTask(UserUpcomingFragment.this).execute();
		       		   			                	  
		       		   			                	
		       		   			                       }
		       		   			               })
		       		   			               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		       		   			                   public void onClick(DialogInterface dialog, int id) {
		       		   			                       // Send the negative button event back to the host activity
		       		   			                      
		       		   			                   }
		       		   			               });
		       		   			        builder.show();  
		       		                       
		       		              
		       		               
		            		   break;
		            		   
		            	   case 1:
		            		   AlertDialog.Builder builder1 = new AlertDialog.Builder(con);
		           			
		       				
		       				
		           			
		       				//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
		       				
		       				builder1.setMessage("Do you want to cancel this appointment?");
		       				 
		       				
		       				builder1.setTitle(ServiceSearch.serviceName);
		       		        builder1
		       		               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		       		                   public void onClick(DialogInterface dialog, int id) {
		       		                       // Send the positive button event back to the host activity
		       		                	  new UserCancelTask(UserUpcomingFragment.this).execute();
		       		                       }
		       		               })
		       		               .setNegativeButton("No", new DialogInterface.OnClickListener() {
		       		                   public void onClick(DialogInterface dialog, int id) {
		       		                       // Send the negative button event back to the host activity
		       		                      
		       		                   }
		       		               });
		       		        builder1.show();
		            		   break;
		            	   
		            	   }
		           }
		    });
		        builder.show();
				
			}

		});
		return v;
	}
	
	public static void  show_message(String message){
		Toast.makeText(CON, message, Toast.LENGTH_SHORT).show();
	}
	
	public void show_toast(final String message){
		
		   getActivity().runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            	Toast.makeText(con, message, Toast.LENGTH_SHORT).show();
		            }
		        });
				
			}
	
	
	public String GetIncreasedTime(String strStartTime, int iMinute)

	 {


	  String[] hm = strStartTime.split(":");

	  int hour = Integer.parseInt(hm[0]);

	  int Min = Integer.parseInt(hm[1]);

	  

	 

	  

	 


	  int itotaltime = hour * 60 + Min;

	  itotaltime += iMinute; 


	  while (itotaltime < 0) 

	  {   

	    itotaltime += 1440;     

	  }


	  int nh = (itotaltime / 60) % 24;  

	  int nm = itotaltime % 60;

	  

	  
	    

	  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
	//  sdf.setLenient(false);
	  Date DtNewTime;

	  

	  try {

	   DtNewTime = sdf.parse(nh+":"+nm);

	 //  Toast.makeText(MainActivity.this, "text : " + DtNewTime , Toast.LENGTH_LONG).show();

	   return sdf.format(DtNewTime);

	  } catch (ParseException e) {

	   // TODO Auto-generated catch block

	   e.printStackTrace();

	  }  

	  return "1";  

	 }



}
