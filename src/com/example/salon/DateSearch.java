package com.example.salon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DateSearch extends Activity{
	
	Context con;
	static Context CON;
	static  String date;
	static  String booking_date;
	static int selected_year;
	static int selected_month;
	static int selected_day;
	static String selected_time_day;
	boolean service_selected=false;
	static String selected_time;
	TextView icon_date;
	TextView icon_ym;
	TextView icon_day;
	
	static ArrayAdapter<String> adapter;
	static ArrayList<String> dialog_list = new ArrayList<String>();
	HashMap<String, String> dialog_service = new HashMap<String, String>();
	static ArrayList<String> original_data = new ArrayList<String>();
	
	
	static LinearLayout linlaHeaderProgress;
	static ListView service_list;
	
	
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
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_search);
		
	//	CalendarView cal = (CalendarView) findViewById(R.id.calendarView1);
		Button search_salons = (Button) findViewById(R.id.search_button);
		 icon_date   = (TextView) findViewById(R.id.it_empty_from_date_number);
		 icon_ym   = (TextView) findViewById(R.id.it_empty_from_date_ym);
		 icon_day   = (TextView) findViewById(R.id.it_empty_from_date_day);
		 LinearLayout date_layout =(LinearLayout) findViewById(R.id.date_icon_layout);
		Spinner time_spinner = (Spinner) findViewById(R.id.time_spinner); 
		
        final EditText search_list = (EditText) findViewById(R.id.search_list);
	    service_list = (ListView) findViewById(R.id.service_list);
		linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
		con=this;
		CON=this;
		List<String> time_list = new ArrayList<String>();
		for(int i=8;i<21;i=i+1){
			
			if(i<10){
				time_list.add("0"+i+":"+"00");
				time_list.add("0"+i+":"+"30");
			}else{
				time_list.add(i+":"+"00");
				time_list.add(i+":"+"30");
			}
			
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, time_list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			time_spinner.setAdapter(dataAdapter);
			
			time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					selected_time = (String) ((TextView)arg1).getText();
				//	Toast.makeText(getApplicationContext(),selected_time, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			search_list.addTextChangedListener(new TextWatcher() {

					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub
						
					}

					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						// TODO Auto-generated method stub
						
					}

					public void onTextChanged(CharSequence arg0, int arg1, int arg2,
							int arg3) {
						
						String charText=arg0.toString();
						 charText = charText.toLowerCase();
					        dialog_list.clear();
					        if (charText.length() == 0) {
					        	dialog_list.addAll(original_data);
					        } else {
					            for (String dataraw : original_data) {
					            	
					                if (dataraw.toLowerCase().contains(charText)) {
					                	dialog_list.add(dataraw);
					                }
					            }
					        }
					        ArrayAdapter<String> adapter2;
					        adapter2= new  ArrayAdapter<String>(con,
				     android.R.layout.simple_list_item_single_choice, dialog_list);
					        service_list.setAdapter(adapter2);
						// TODO Auto-generated method stub
					
					} 
		        	
		        
		            
		                    });
			
			 if(isNetworkAvailable()){
		        	
				 new ServiceDialogTask().execute();
	        }else{
	        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
	                builder.setMessage("SalonSpa needs an active data connection to continue")
	                      .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
	                          public void onClick(DialogInterface dialog, int id) {
	                       	   if(isNetworkAvailable()){
	                       		new ServiceDialogTask().execute();
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
		
			
			
			 service_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			service_list.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					service_selected=true;
					ServiceSearch.serviceName = (String) ((TextView)arg1).getText();
				
						for(HashMap<String, String> i:ServiceDialogTask.serviceList){
							if(i.get(Service.KEY_SERVICE_NAME).equals(ServiceSearch.serviceName)){
								ServiceSearch.serviceID=i.get(Service.KEY_SERVICE_ID);
							}
							
						}
						
					// Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
					
					
					
				}});
		
		 final Calendar c = Calendar.getInstance();
          selected_year = c.get(Calendar.YEAR);
          selected_month = c.get(Calendar.MONTH);
          selected_day = c.get(Calendar.DAY_OF_MONTH);
          int day_of_week  = c.get(Calendar.DAY_OF_WEEK);
      
          final long getTime= c.getTimeInMillis();
     
          icon_date.setText(String.valueOf(selected_day));
          icon_ym.setText(String.valueOf(selected_month+1)+"/"+String.valueOf(selected_year));
          icon_day.setText(get_day_of_week(day_of_week));
          
  	    date = selected_day+"/"+selected_month+"/"+selected_year;
  	    booking_date= selected_year+"-"+selected_month+"-"+selected_day;
  	  search_salons.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(service_selected){
					selected_time_day=booking_date+" "+selected_time+":00";
					Intent tute_intent= new Intent(DateSearch.this,DateSearchResult.class);
					tute_intent.putExtra("LAST_ACTIVITY", "Main");
					startActivity(tute_intent);
						
				}else{
					Toast.makeText(getApplicationContext()," Please select the service you like :)", Toast.LENGTH_SHORT).show();	
				}
				
			}});
		
  	date_layout.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
			LayoutInflater inflater = getLayoutInflater();
			View view=inflater.inflate(R.layout.datepicker_dialog, null);
			DatePicker date_picker =(DatePicker) view.findViewById(R.id.date_picker);
			date_picker.setMinDate(getTime);
			date_picker.init(selected_year, selected_month, selected_day, new DatePicker.OnDateChangedListener() {
				
				@Override
				public void onDateChanged(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					  selected_year =year;
			          selected_month =monthOfYear;
			          selected_day = dayOfMonth;
					 int month=monthOfYear+1;
					 Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth); // Note that Month value is 0-based. e.g., 0 for January.
			        	int week_date = calendar.get(Calendar.DAY_OF_WEEK);
					    icon_date.setText(String.valueOf(dayOfMonth));
			            icon_ym.setText(String.valueOf(month)+"/"+String.valueOf(year));
			            icon_day.setText(get_day_of_week(week_date));
					   
			            booking_date= year+"-"+month+"-"+dayOfMonth;
						
					
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
	                       }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // Send the negative button event back to the host activity
	                      
	                   }
	               });
	        builder.show();
			
			//DatePickerDialog DatePicker =new DatePickerDialog(DateSearch.this, datePickerListener, 
				//	selected_year, selected_month-1,selected_day);
			//DatePicker.show();
		}});
		
  	
		/*cal.setOnDateChangeListener(new OnDateChangeListener(){

			@Override
			public void onSelectedDayChange(CalendarView arg0,
					int year, int month, int day) {
				// TODO Auto-generated method stub
				selected_day=day;
				selected_month=month;
				selected_year=year;
				month=month+1;
				 date = day+"/"+month+"/"+year;
				 booking_date= year+"-"+month+"-"+day;
				
				 AlertDialog.Builder builder = new AlertDialog.Builder(con);
		   			
					
					
		   			
					//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
					
					
					 
					String[] words = {"Select a Salon", "Select a Service","Back"};
					builder.setTitle("Date: "+date);
			        builder
			        .setItems(words, new DialogInterface.OnClickListener() {
			               public void onClick(DialogInterface dialog, int which) {
			            	   switch(which){
			            	   
			            	   
			            	   case 0:
			            		   Intent tute_intent= new Intent("com.example.salon.SALONSEARCH");
			            		   tute_intent.putExtra("LAST_ACTIVITY", "Main");
			   					startActivity(tute_intent);
			            		   break;
			            	   case 1:
			            		   Intent tute_intent2= new Intent("com.example.salon.SERVICESEARCH");
			            		   tute_intent2.putExtra("LAST_ACTIVITY", "Main");
			   					startActivity(tute_intent2);
			            		   break;
			            	   
			            	   
			            	   }
			            	   
			               }
			        });
			       
			        
			        builder.show();		
				
				
			}});
       
   */
	}
	
	

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
	
	

}
