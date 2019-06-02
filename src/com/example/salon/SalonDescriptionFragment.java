package com.example.salon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SalonDescriptionFragment extends Fragment{

	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	LazyAdapter adapter;
	Context con;
	static Context CON;
	static LinearLayout linlaHeaderProgress;
	static LinearLayout linlaHeaderProgress2;
	static String last_activity="Hello";
	static Spinner stylist_spinner;
	static Spinner time_spinner;
	static String BranchName="Salon Name";
	static String BranchDescription="Salon Description";
	static String selected_date;
	static String selected_stylist_name;
	static String selected_stylist_id;
	static String selected_stylist_lname;
	static String selected_stylist_fname;
	static String selected_service_cost;
	static String selected_service_duration;
	static String selected_time;
	static String selected_endTime;
	static String selected_startTime;
	static int spinner_selected_pos;
	static int date_picker_day;
	static int date_picker_month;
	static int date_picker_year;
	static int year;
	static int month;
	static int day;
	static ProgressDialog mDialog;
	TextView icon_date;
	TextView icon_ym;
	TextView icon_day;

	
	
	 
	 
	 
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
	 
	 private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
		
	public static final SalonDescriptionFragment newInstance(String message)

	{
	SalonDescriptionFragment f = new SalonDescriptionFragment();
	Bundle bdl = new Bundle(1);
	bdl.putString(EXTRA_MESSAGE, message);
	
    last_activity=message;
	f.setArguments(bdl);
	return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//String message = getArguments().getString(EXTRA_MESSAGE);
		if(SalonAdapterTask.salonList!=null){
		for(HashMap<String, String> i:SalonAdapterTask.salonList){
			if((i.get(Salon.KEY_ID).equals(SalonSearch.BranchID)) && (i.get(Salon.KEY_MAP_ID).equals(SalonSearch.MapID))){
				SalonSearch.BranchName=i.get(Salon.KEY_SALON_NAME);
				SalonSearch.BranchDescription=i.get(Salon.KEY_DISCRIPTION);
				SalonSearch.SalonCity=i.get(Salon.KEY_CITY);
				SalonSearch.SalonCountry=i.get(Salon.KEY_COUNTRY);
				SalonSearch.SalonProvince=i.get(Salon.KEY_PROVINCE);
				SalonSearch.SalonZipcode=i.get(Salon.KEY_ZIPCODE);
				SalonSearch.SalonStreet1=i.get(Salon.KEY_STREET1);
				SalonSearch.SalonStreet2=i.get(Salon.KEY_STREET2);
				SalonSearch.SalonLongitude=i.get(Salon.KEY_LONGITUDE);
				SalonSearch.SalonLatitude=i.get(Salon.KEY_LATITUDE);
				
			}
			
		}
		}else{
			
			for(HashMap<String, String> i:UnifiedSalonTask.salonList){
				if((i.get(Salon.KEY_ID).equals(SalonSearch.BranchID)) && (i.get(Salon.KEY_MAP_ID).equals(SalonSearch.MapID))){
					SalonSearch.BranchName=i.get(Salon.KEY_SALON_NAME);
					SalonSearch.BranchDescription=i.get(Salon.KEY_DISCRIPTION);
					SalonSearch.SalonCity=i.get(Salon.KEY_CITY);
					SalonSearch.SalonCountry=i.get(Salon.KEY_COUNTRY);
					SalonSearch.SalonProvince=i.get(Salon.KEY_PROVINCE);
					SalonSearch.SalonZipcode=i.get(Salon.KEY_ZIPCODE);
					SalonSearch.SalonStreet1=i.get(Salon.KEY_STREET1);
					SalonSearch.SalonStreet2=i.get(Salon.KEY_STREET2);
					SalonSearch.SalonLongitude=i.get(Salon.KEY_LONGITUDE);
					SalonSearch.SalonLatitude=i.get(Salon.KEY_LATITUDE);
					
				}
				
			}
			
		}
		
		
		con=getActivity();
		CON=getActivity();
		
		/*
		ProgressDialog mDialog = new ProgressDialog(con);
        mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.show();
        */ 
        String Address = SalonSearch.SalonStreet1+","+SalonSearch.SalonStreet2+","+SalonSearch.SalonCity;
		View v = inflater.inflate(R.layout.salon_description_fragment, container, false);
		linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);
		QuickContactBadge badgeSmall = (QuickContactBadge)v.findViewById(R.id.salon_logo);  
		TextView branch_name = (TextView)v.findViewById(R.id.salonNAME);  
		TextView branch_des = (TextView)v.findViewById(R.id.SalonDes);
		TextView address = (TextView)v.findViewById(R.id.address);
		Button book_button = (Button) v.findViewById(R.id.book_salon);
		ImageView map_icon =(ImageView) v.findViewById(R.id.map_icon);
		badgeSmall.assignContactFromEmail("any@gmail.com", true);  
		badgeSmall.assignContactFromPhone("831-555-1212", true);
		badgeSmall.setMode(ContactsContract.QuickContact.MODE_SMALL); 
		branch_name.setText(SalonSearch.BranchName);
		branch_des.setText(SalonSearch.BranchDescription);
		address.setText(Address);
		
		  if(isNetworkAvailable()){
			  if(last_activity.equals("ServiceSearch")){
					
					new ServiceDetailsTask().execute();
					
				}
        }else{
        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setCancelable(false);
                builder.setMessage("SalonSpa needs an active data connection to continue")
                      .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int id) {
                       	   if(isNetworkAvailable()){
                       		  if(last_activity.equals("ServiceSearch")){
                      			
                      			new ServiceDetailsTask().execute();
                      			
                      		}
                       	   }else{
                       	   builder.show();
                       	   }
                              // FIRE ZE MISSILES!
                          }
                      })
                     ;
                // Create the AlertDialog object and return it
                builder.show();
        }
		 
		
	  
	    map_icon.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%d&q=%f,%f", Float.parseFloat(SalonSearch.SalonLatitude),  Float.parseFloat(SalonSearch.SalonLongitude),17, Float.parseFloat(SalonSearch.SalonLatitude), Float.parseFloat(SalonSearch.SalonLongitude));
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				con.startActivity(intent);
			}
			});
		
       book_button.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){
				if(last_activity.equals("ServiceSearch")){
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View view=inflater.inflate(R.layout.booking_dialog, null);
				
				stylist_spinner =(Spinner) view.findViewById(R.id.stylist_spinner);
				time_spinner=(Spinner) view.findViewById(R.id.free_time_spinner);
				TextView service_name = (TextView) view.findViewById(R.id.service_name);
				linlaHeaderProgress2 = (LinearLayout) view.findViewById(R.id.linlaHeaderProgress);
				 icon_date   = (TextView) view.findViewById(R.id.it_empty_from_date_number);
				 icon_ym   = (TextView) view.findViewById(R.id.it_empty_from_date_ym);
				 icon_day   = (TextView) view.findViewById(R.id.it_empty_from_date_day);
				 LinearLayout date_layout =(LinearLayout) view.findViewById(R.id.date_icon_layout);
				 TextView service_cost = (TextView) view.findViewById(R.id.service_cost);
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
				
				stylist_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						StylistSearch.stylistName = (String) ((TextView)arg1).getText();
						spinner_selected_pos=arg2;
						HashMap<String, String> dialog_list = StylistDialogTask.stylistList.get(spinner_selected_pos);
						StylistSearch.stylistID = dialog_list.get(Stylist.KEY_STYLIST_ID);
						StylistSearch.selected_stylist_fname=dialog_list.get(Stylist.KEY_FIRST_NAME);
						StylistSearch.selected_stylist_lname=dialog_list.get(Stylist.KEY_LAST_NAME);
						new TimeDialogTask("SalonProfileTwo").execute();
					//	Toast.makeText(con, selected_stylist_id, Toast.LENGTH_SHORT).show();
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				
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
	            	                	   new StylistDialogTask("SalonProfileTwo").execute();	
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
				
				builder.setView(view);
				
					new StylistDialogTask("SalonProfileTwo").execute();	
				
				
		        builder.setPositiveButton("Book", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                       // Send the positive button event back to the host activity
		                	   String endTime = GetIncreasedTime(selected_time,Integer.parseInt(ServiceSearch.selected_service_duration));
		                	   selected_endTime= selected_date+" "+endTime+":00";
		                	   selected_startTime=selected_date+" "+selected_time+":00";
		                	   
		                	   
		                	   mDialog = new ProgressDialog(con);
		                	   mDialog.setCancelable(false);
		                	   new SalonDesCheckEmailTask(SalonDescriptionFragment.this).execute();
		                	  
		                	
		                	   
		                       }
		               })
		               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                       // Send the negative button event back to the host activity
		                      
		                   }
		               });
		        builder.show();
				}
				else{
					Intent tute_intent= new Intent("com.example.salon.SERVICESEARCH");
					tute_intent.putExtra("LAST_ACTIVITY", "SalonProfile");
					
					//Bundle translateBundle = ActivityOptions.makeCustomAnimation(ActivityMain.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
					startActivity(tute_intent);
					
				}
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
		   
		   private void handleAuthorizeResult(int resultCode, Intent data) {
		        if (data == null) {
		            show_toast("Unknown error, click the button again");
		            return;
		        }
		        if (resultCode == -1) {
		            Log.i("TAG", "Retrying");
		            mDialog.show();
		            new SalonDesGetUserDetails(SalonDescriptionFragment.this, ActivityMain.selected_account_name, ActivityMain.SCOPE,
		            		ActivityMain.REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
		          //  getTask(this, mEmail, SCOPE, REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
		            return;
		        }
		        if (resultCode == 0) {
		        	show_toast("User rejected authorization.Cannot make booking without user details.");
		            return;
		        }
		        show_toast("Unknown error, click the button again");

		   }
		   
		   public void showErrorDialog(final int code) {
		       getActivity().runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		              Dialog d = GooglePlayServicesUtil.getErrorDialog(
		                  code,
		                  getActivity(),
		                  ActivityMain.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
		              d.show();
		            }
		        });
		    }
		   
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
		
		        	
		        	
			if (requestCode == ActivityMain.REQUEST_CODE_RECOVER_FROM_AUTH_ERROR) {
	            handleAuthorizeResult(resultCode, data);
	            return;
	        }
		        
					  
		        
		    
			super.onActivityResult(requestCode, resultCode, data);
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

		public void show_dialog(){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view=inflater.inflate(R.layout.user_details_dialog, null);
		    final EditText fname = (EditText) view.findViewById(R.id.fname);
		   final  EditText lname = (EditText) view.findViewById(R.id.lname);
		
		
			
			builder.setView(view);
			 
			
			builder.setTitle("Contact Details");
	        builder
	               .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                     ActivityMain.salonspa_user_givenname= fname.getText().toString();
	                     ActivityMain.salonspa_user_familyname= lname.getText().toString();
	                     
	                     new SalonDesUserCreateTask(SalonDescriptionFragment.this).execute();
	                   
	                   
	                   
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // Send the negative button event back to the host activity
	                	   show_toast("Booking cannot be made due to insufficient user details");
	                      
	                   }
	               });
	        builder.show();
		}
	/*	public void show_reminder_dialog(){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//	LayoutInflater inflater = getActivity().getLayoutInflater();
		//	View view=inflater.inflate(R.layout.user_details_dialog, null);
	//	    final EditText fname = (EditText) view.findViewById(R.id.fname);
		//   final  EditText lname = (EditText) view.findViewById(R.id.lname);
		
		
			builder.setTitle("Add Reminder");
			builder.setMessage("Would you like to add this appointmnet to your calendar?");
			//builder.setView(view);
			
	        builder
	               .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                //     ActivityMain.salonspa_user_givenname= fname.getText().toString();
	                //     ActivityMain.salonspa_user_familyname= lname.getText().toString();
	                	   Intent calIntent = new Intent(Intent.ACTION_INSERT);
	                	   calIntent.setType("vnd.android.cursor.item/event");
	                	   calIntent.putExtra(Events.TITLE, ServiceSearch.serviceName);
	                	   calIntent.putExtra(Events.EVENT_LOCATION, SalonSearch.BranchName);
	                	   calIntent.putExtra(Events.DESCRIPTION, "A Pig Roast on the Beach");
	                	   Calendar calendar = Calendar.getInstance();
	                	   calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 
	                	                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
	                	   long startTime = calendar.getTimeInMillis();
	                	   calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
	                			   startTime);
	                	   startActivity(calIntent);
	                   
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // Send the negative button event back to the host activity
	                	   show_toast("Booking cannot be made due to insufficient user details");
	                      
	                   }
	               });
	        builder.show();
		}
		
*/
	

}


