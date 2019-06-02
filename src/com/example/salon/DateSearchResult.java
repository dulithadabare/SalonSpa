package com.example.salon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class DateSearchResult extends Activity {
	static DateSearchResultAdapter DateSearchResultAdapter;
	static LinearLayout linlaHeaderProgress;
	static LinearLayout linlaHeaderProgress2;
	static RelativeLayout no_result_layout;
	static Context CON;
	static ListView list;
	static ListView category_list;
	
	static String serviceID;
	static String serviceName;
	
	static Spinner stylist_spinner;
	static int date_picker_day;
	static int date_picker_month;
	static int date_picker_year;
	static String selected_date;
	static int year;
	static int month;
	static int day;
	//static String selected_service_cost;
	//static String selected_service_duration;
	static String selected_endTime;
	static String selected_startTime;
	static String selected_time;
	static int spinner_selected_pos;
	static String selected_category_id;
	
	Context con;
	AlertDialog alert;

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
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.date_result_search);
		con=this;
		
		
		 list=(ListView)findViewById(R.id.listall);
		 linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
		 no_result_layout    = (RelativeLayout) findViewById(R.id.no_result_layout);
		 this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		final EditText  search = (EditText) this.findViewById(R.id.inputSearch);
		final ImageView  sort_icon = (ImageView) this.findViewById(R.id.sort_icon);
		
		CON=this;
		
	//	Bundle extras = getIntent().getExtras();
	//	final String last_activity = extras.getString("LAST_ACTIVITY");
		
		
		
		  search.addTextChangedListener(new TextWatcher() {

				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					
						DateSearchResultAdapter.filter(arg0.toString());
					
					// TODO Auto-generated method stub
				
				} 
	        	
	        
	            
	                    });
		  
		  if(isNetworkAvailable()){
	        
				    	 new DateSearchResultTask().execute(); 
				    	 String toast_message="Salons available at "+DateSearch.selected_time+" on "+DateSearch.booking_date+" for "+ServiceSearch.serviceName;
				    	 Toast.makeText(getApplicationContext(), toast_message, Toast.LENGTH_LONG).show();
				     
        }else{
        final AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setCancelable(false);
                builder.setMessage("SalonSpa needs an active data connection to continue")
                      .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int id) {
                       	   if(isNetworkAvailable()){
                       		 new DateSearchResultTask().execute();
                       		 String toast_message="With "+StylistSearch.stylistName+" At "+SalonSearch.BranchName;
    				    	 Toast.makeText(getApplicationContext(), toast_message, Toast.LENGTH_LONG).show();
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
		 
	        
	        
	        list.setOnItemClickListener(new OnItemClickListener() {
	            
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					SalonSearch.BranchID = (String) ((TextView)arg1.findViewById(R.id.BranchID)).getText();
					SalonSearch.MapID = (String) ((TextView)arg1.findViewById(R.id.MapID)).getText();
					
					//Toast.makeText(getApplicationContext(),"+"+last_activity+"+", Toast.LENGTH_SHORT).show();
					
						
						for(HashMap<String, String> i:DateSearchResultTask.salonList){
							if((i.get(Salon.KEY_ID).equals(SalonSearch.BranchID)) && (i.get(Salon.KEY_MAP_ID).equals(SalonSearch.MapID))){
								ServiceSearch.selected_service_cost=i.get(Service.KEY_SERVICE_CUSTOM_PRICE);
								ServiceSearch.selected_service_duration=i.get(Service.KEY_SERVICE_DURATION);
								
							}
						}
						
						AlertDialog.Builder builder = new AlertDialog.Builder(con);
						LayoutInflater inflater = getLayoutInflater();
						View view=inflater.inflate(R.layout.time_selected_booking_dialog, null);
						stylist_spinner =(Spinner) view.findViewById(R.id.stylist_spinner);
						TextView service_name = (TextView) view.findViewById(R.id.service_name);
						TextView time = (TextView) view.findViewById(R.id.time);
						 icon_date   = (TextView) view.findViewById(R.id.it_empty_from_date_number);
						 icon_ym   = (TextView) view.findViewById(R.id.it_empty_from_date_ym);
						 icon_day   = (TextView) view.findViewById(R.id.it_empty_from_date_day);
					//	 LinearLayout date_layout =(LinearLayout) view.findViewById(R.id.date_icon_layout);
						linlaHeaderProgress2 = (LinearLayout) view.findViewById(R.id.linlaHeaderProgress);
						 TextView service_cost = (TextView) view.findViewById(R.id.service_cost);
						 service_name.setText(ServiceSearch.serviceName);
						 time.setText(DateSearch.selected_time);
						 service_cost.setText("Rs."+ServiceSearch.selected_service_cost);
						 DisplayMetrics displaymetrics = new DisplayMetrics();
						 getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
						 int width = displaymetrics.widthPixels;
						 service_name.setWidth(width/3);
						 
						 Calendar calendar = new GregorianCalendar(DateSearch.selected_year, DateSearch.selected_month, DateSearch.selected_day); // Note that Month value is 0-based. e.g., 0 for January.
				        	int week_date = calendar.get(Calendar.DAY_OF_WEEK);
						  
		                   year = DateSearch.selected_year;
		                   month = DateSearch.selected_month;
		                   day = DateSearch.selected_day;
		                  
		                   int correct_month=month+1;
		                   
		                   icon_date.setText(String.valueOf(day));
		                   icon_ym.setText(String.valueOf(correct_month)+"/"+String.valueOf(year));
		                   icon_day.setText(get_day_of_week(week_date));
			                  
			                  
			                 selected_date=year+"-"+correct_month+"-"+day;
						  
					/*	if(ActivityMain.Flow.equals("DateFlow")){
							year=DateSearch.selected_year; 
							month=DateSearch.selected_month; 
							day=DateSearch.selected_day;
							selected_date=year+"-"+month+"-"+day;
						}*/
						
			              /*   date_layout.setOnClickListener(new OnClickListener(){

				             		@Override
				             		public void onClick(View arg0) {
				             			// TODO Auto-generated method stub
				             			
				             			AlertDialog.Builder builder = new AlertDialog.Builder(con);
				            			LayoutInflater inflater = getLayoutInflater();
				            			View view=inflater.inflate(R.layout.datepicker_dialog, null);
				            			DatePicker date_picker =(DatePicker) view.findViewById(R.id.date_picker);
				            			
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
				            	                       }
				            	               })
				            	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				            	                   public void onClick(DialogInterface dialog, int id) {
				            	                       // Send the negative button event back to the host activity
				            	                      
				            	                   }
				            	               });
				            	        builder.show();
				             		}});
				             		*/
						stylist_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {
								// TODO Auto-generated method stub
								StylistSearch.stylistName = (String) ((TextView)arg1).getText();

								String[] name = StylistSearch.stylistName.split(" ");

								  StylistSearch.selected_stylist_fname=name[0];
								  StylistSearch.selected_stylist_lname= name[1];
								  
								spinner_selected_pos=arg2;
								HashMap<String, String> dialog_list = StylistDialogTask.stylistList.get(spinner_selected_pos);
								StylistSearch.stylistID = dialog_list.get(Stylist.KEY_STYLIST_ID);
							//	Toast.makeText(con, selected_stylist_id, Toast.LENGTH_SHORT).show();
								
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}
						});
						
						//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
						
						builder.setView(view);
						 new StylistDialogTask("DateSearchResult").execute();
						 
						
						builder.setTitle(SalonSearch.BranchName);
				        builder
				               .setPositiveButton("Book", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                       // Send the positive button event back to the host activity
				                	   String endTime = GetIncreasedTime(DateSearch.selected_time,Integer.parseInt(ServiceSearch.selected_service_duration));
				                	   selected_endTime= selected_date+" "+endTime+":00";
				                	   selected_startTime=selected_date+" "+DateSearch.selected_time+":00";
				                	   
				                	   
				                	   mDialog = new ProgressDialog(con);
				                	   mDialog.setCancelable(false);
				                	   new DateSearchResultCheckEmail(DateSearchResult.this).execute();
				                	  
				                	
				                       }
				               })
				               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                       // Send the negative button event back to the host activity
				                      
				                   }
				               });
				        builder.show();
					}
					
				

			});
	        
	      
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.service_category_menu, menu);
        return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	    switch (item.getItemId()) {
        case R.id.category_select:
        	
  			AlertDialog.Builder builder = new AlertDialog.Builder(con);
  			
  			 
			LayoutInflater inflater = getLayoutInflater();
			View view=inflater.inflate(R.layout.dialog_category, null);
			category_list =(ListView) view.findViewById(R.id.listView1);
			linlaHeaderProgress2 = (LinearLayout) view.findViewById(R.id.linlaHeaderProgress2);
		//	Spinner spinner1 =(Spinner) view.findViewById(R.id.spinner1);
			
			new CategoryDialogTask().execute();
			
			category_list.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					 String selected_category_name = (String) ((TextView)arg1).getText();
				
					 HashMap<String, String> dialog_list = CategoryDialogTask.categoryList.get(arg2);
						selected_category_id = dialog_list.get("CatogoryId");
						


						new ServiceAdapterTask().execute("Category_Selected");
					// Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
					 alert.cancel();
					
					
				}});
			
		//spinner1.setAdapter(dataAdapter);
			//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
			
			builder.setView(view);
			 
			
			builder.setTitle("Select Service Category");
	        builder
	               .setPositiveButton("All Services", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // Send the positive button event back to the host activity
	                	  // new StylistAdapterTask().execute("NotSet");
	                	  
	                	   new ServiceAdapterTask().execute("NotSet");
	                	   alert.cancel();
	                       }
	               });
	           
	        
	       
	        alert= builder.create();
	        alert.show();
		        
		        break;
		    
     
        }
		
		return true;
	}
	
	 public void show_toast(final String message){
			
		   runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
		            new DateSearchResultGetUserDetails(DateSearchResult.this, ActivityMain.selected_account_name, ActivityMain.SCOPE,
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
		       runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		              Dialog d = GooglePlayServicesUtil.getErrorDialog(
		                  code,
		                  DateSearchResult.this,
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

		  

		  
		    

		  SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.getDefault());

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
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = getLayoutInflater();
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
	                     
	                     new DateSearchResultUserCreateTask(DateSearchResult.this).execute();
	                   
	                   
	                   
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

		@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
			
			
			super.onRestart();
			
			if(isNetworkAvailable()){
			}else{
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			        builder.setMessage("SalonSpa needs an active data connection to continue")
			              .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
			                  public void onClick(DialogInterface dialog, int id) {
			               	   if(isNetworkAvailable()){
			                   
			               	   }else{
			               	   builder.show();
			               	   }
			                      // FIRE ZE MISSILES!
			                  }
			              })
			              .setNegativeButton("Close App", new DialogInterface.OnClickListener() {
			                  public void onClick(DialogInterface dialog, int id) {
			                      // User cancelled the dialog
			               	   finish();
			                  }
			              });
			        // Create the AlertDialog object and return it
			        builder.show();
			}
			
		}

		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			
			super.onResume();
		
			
		}

		 


}
