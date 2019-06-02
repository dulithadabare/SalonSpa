package com.example.salon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.example.salon.ActivityMain;
import com.google.android.gms.common.GooglePlayServicesUtil;




import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends Activity{
	
	 static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
	 static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
	 static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;
	
	Context con;
	String token;
	static String selected_account_name="";
	static String user_phone_number="";
	static String salonspa_user_id="";
	static String salonspa_user_googleid="NA";
	static String salonspa_user_name="";
	static String salonspa_user_familyname="NA";
	static String salonspa_user_givenname="NA";
	static String salonspa_user_picture="";
	static String salonspa_user_gender="";
	static String Today;
	static String ApiKey="15726";;
	
	static Account selected_account;
	static String Flow="not set";
	static Account[] accounts;
	AccountManager accountManager;
	
	 boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salonspa);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
       int day = c.get(Calendar.DAY_OF_MONTH);
        
        Today=  year+"-"+ month+"-"+ day;
		
		
		con=this;
		if(isNetworkAvailable()){
		}else{
		final AlertDialog.Builder builder = new AlertDialog.Builder(con);
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
		
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
		String number = tm.getLine1Number();
		if(number!=null){
			user_phone_number=number;
		}
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		  final SharedPreferences.Editor editor = preferences.edit();
		  if (preferences.contains("account_email")) {
			  selected_account_name = preferences.getString("account_email", "n/a");
			}else{
		 
		  accountManager = AccountManager.get(this);
			final Account[] accounts = accountManager.getAccountsByType("com.google");
			
			if(accounts.length>0){
				AlertDialog.Builder builder = new AlertDialog.Builder(con);
			      builder.setTitle("Select a Google account");
			     builder.setCancelable(false);
			      final int size = accounts.length;
			      String[] names = new String[size];
			      for (int i = 0; i < size; i++) {
			        names[i] = accounts[i].name;
			      }
			      builder.setItems(names, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			          // Stuff to do when the account is selected by the user
			        	selected_account_name= accounts[which].name;
			        	selected_account=accounts[which];
			        	 editor.putString("account_email",selected_account_name);
			   		  editor.apply();
			        	}
			      });
			        
			     
			      builder.show();
			}else{
			selected_account_name= accounts[0].name;
			 editor.putString("account_email",selected_account_name);
	   		  editor.apply();
			}
		  
			}
		
//	new GetUserDetailsTask(ActivityMain.this, selected_account_name, SCOPE,
  //              REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
		
		/*if(accounts.length>0){
			AlertDialog.Builder builder = new AlertDialog.Builder(con);
		      builder.setTitle("Select a Google account");
		    
		      final int size = accounts.length;
		      String[] names = new String[size];
		      for (int i = 0; i < size; i++) {
		        names[i] = accounts[i].name;
		      }
		      builder.setItems(names, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		          // Stuff to do when the account is selected by the user
		        	selected_account_name= accounts[which].name;
		        	selected_account=accounts[which];
		        	
		        	try {
		        	    token = GoogleAuthUtil.getToken(ActivityMain.this, selected_account_name, "oauth2:https://www.googleapis.com/auth/userinfo.profile");
		        	    URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="
			        	        + token);
			        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
			        	int serverCode = con.getResponseCode();
			        	//successful query
			        	if (serverCode == 200) {
			        	    InputStream is = con.getInputStream();
			        	  readResponse(is);
			        	   
			        	    is.close();
			        	    return;
			        	//bad token, invalidate and get a new one
			        	} else if (serverCode == 401) {
			        		 GoogleAuthUtil.invalidateToken(ActivityMain.this, token);
			        	    
			        	    Log.e("Tag", "Server auth error: " + readResponse(con.getErrorStream()));
			        	    return;
			        	//unknown error, do something else
			        	} else {
			        	    Log.e("Server returned the following error code: " + serverCode, null);
			        	    return;
			        	}
			          
			        // Now you can use the Tasks API...
			        //useTasksAPI(token);
			    
		        	} catch(UserRecoverableAuthException e) {
		        		
		        		Intent launch = (Intent) e.getIntent();
		        		startActivityForResult(launch, 0);
			              return;
		        		
		        	} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (GoogleAuthException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	
		        }
		      });
		      builder.show();
		}
		*/
		
		/*
		if(selected_account!=null){
			accountManager.getAuthToken(selected_account,"oauth2:https://www.googleapis.com/auth/userinfo.profile", null, this, new AccountManagerCallback<Bundle>() {
			    public void run(AccountManagerFuture<Bundle> result) {
			      try {
			        // If the user has authorized your application to use the tasks API
			        // a token is available.
			    	  Intent launch = (Intent) result.getResult().get(AccountManager.KEY_INTENT);
			          if (launch != null) {
			              startActivityForResult(launch, 0);
			              return;
			          }else{
			         token = result.getResult().getString(AccountManager.KEY_AUTHTOKEN);
			         URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="
			        	        + token);
			        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
			        	int serverCode = con.getResponseCode();
			        	//successful query
			        	if (serverCode == 200) {
			        	    InputStream is = con.getInputStream();
			        	  readResponse(is);
			        	   
			        	    is.close();
			        	    return;
			        	//bad token, invalidate and get a new one
			        	} else if (serverCode == 401) {
			        	    
			        	    
			        	    Log.e("Tag", "Server auth error: " + readResponse(con.getErrorStream()));
			        	    return;
			        	//unknown error, do something else
			        	} else {
			        	    Log.e("Server returned the following error code: " + serverCode, null);
			        	    return;
			        	}
			          }
			        // Now you can use the Tasks API...
			        //useTasksAPI(token);
			      } catch (OperationCanceledException e) {
			        // TODO: The user has denied you access to the API, you should handle that
			      } catch (Exception e) {
			       // handleException(e);
			      }
			    }
			  }, null);
		}
		*/
		  
		
		
		RelativeLayout view =(RelativeLayout) findViewById(R.id.view);
		TextView salon_text =(TextView) findViewById(R.id.salon);
		TextView service_text =(TextView) findViewById(R.id.service);
		TextView stylist_text =(TextView) findViewById(R.id.stylist);
		TextView date_text =(TextView) findViewById(R.id.date);

		//ImageView test =(ImageView) findViewById(R.id.test);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    	SearchView searchView = (SearchView) findViewById(R.id.searchView);
    	SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
    	searchView.setSearchableInfo(searchableInfo);
    	searchView.setFocusable(false);
    	searchView.setQueryHint("Search Salons,Services and Stylists");
    	
    	String formattedText = getString(R.string.pick_salon);
    	Spanned result = Html.fromHtml(formattedText);
    	salon_text.setText(result);
    	
    	String formattedText2 = getString(R.string.pick_service);
    	Spanned result2 = Html.fromHtml(formattedText2);
    	service_text.setText(result2);

    	String formattedText3 = getString(R.string.pick_stylist);
    	Spanned result3 = Html.fromHtml(formattedText3);
    	stylist_text.setText(result3);

    	String formattedText4 = getString(R.string.pick_date);
    	Spanned result4 = Html.fromHtml(formattedText4);
    	date_text.setText(result4);
    	
    	RelativeLayout salon = (RelativeLayout) findViewById(R.id.salon_layout);
    	RelativeLayout service = (RelativeLayout) findViewById(R.id.service_layout);
    	RelativeLayout stylist = (RelativeLayout) findViewById(R.id.stylist_layout);
    	RelativeLayout date = (RelativeLayout) findViewById(R.id.date_layout);
	
		Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.app_background);
		Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.app_background_two);
		Bitmap b4 = BitmapFactory.decodeResource(getResources(), R.drawable.app_background_three);

		Drawable d1 = new BitmapDrawable(getResources(),b1); 
		Drawable d2 = new BitmapDrawable(getResources(),b2); 
		Drawable d3 = new BitmapDrawable(getResources(),b3); 
		Drawable d4 = new BitmapDrawable(getResources(),b4); 
		//TranslateAnimation transAnimation= new TranslateAnimation(100, 200, 100, 200);
		//transAnimation.setDuration(2000);
		//view.startAnimation(transAnimation);
		final CyclicTransitionDrawable ctd = new CyclicTransitionDrawable(new Drawable[] { 
				//  "@drawable/background", 
				  //R.drawable.app_background,            
				  //R.drawable. background2, 
				 d1,d2,d3,d4
				});
		view.setBackground(ctd);
		ctd.startTransition(1000, 800);
				
		// Here we pass our transition resource, see previous step
		//final AnimationDrawable frameAnimation = (AnimationDrawable) view.getBackground();
		
		salon.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//frameAnimation.start(); 
				Flow="SalonFlow";
				Intent tute_intent= new Intent(ActivityMain.this,SalonSearch.class);
				tute_intent.putExtra("LAST_ACTIVITY", "Main");
				Bundle translateBundle = ActivityOptions.makeCustomAnimation(ActivityMain.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
				startActivity(tute_intent,translateBundle);
			}});
		
		service.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//frameAnimation.start(); 
				Flow="ServiceFlow";
				Intent tute_intent= new Intent(ActivityMain.this,ServiceSearch.class);
				tute_intent.putExtra("LAST_ACTIVITY", "Main");
				Bundle translateBundle = ActivityOptions.makeCustomAnimation(ActivityMain.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
				startActivity(tute_intent,translateBundle);
			}});
		
		stylist.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//frameAnimation.start(); 
				Flow="StylistFlow";
				Intent tute_intent= new Intent(ActivityMain.this,StylistSearch.class);
				Bundle translateBundle = ActivityOptions.makeCustomAnimation(ActivityMain.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
				startActivity(tute_intent,translateBundle);
			}});

		date.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Flow="DateFlow";
				Intent tute_intent= new Intent(ActivityMain.this,DateSearch.class);
			//	tute_intent.putExtra("LAST_ACTIVITY", "Main");
			//	Bundle translateBundle = ActivityOptions.makeCustomAnimation(ActivityMain.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
				startActivity(tute_intent);

				// TODO Auto-generated method stub
				//frameAnimation.start(); 
				// DialogFragment newFragment = new DatePickerFragment();
				  //  newFragment.show(getFragmentManager(), "datePicker");
			       // newFragment.setCancelable(true);
			      
				

			/*
				
				AlertDialog.Builder builder = new AlertDialog.Builder(con);
				LayoutInflater inflater = getLayoutInflater();
				View view=inflater.inflate(R.layout.calendar_dialog, null);
			    CalendarView cal = (CalendarView)view.findViewById(R.id.calendar);
			
			
				
				builder.setView(view);
				 
				
				builder.setTitle("Pick a date");
		        builder
		               .setPositiveButton("Select", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                       // Send the positive button event back to the host activity
		                	   final Calendar c = Calendar.getInstance();
		                       DateSearch.selected_year = c.get(Calendar.YEAR);
		                       DateSearch.selected_month = c.get(Calendar.MONTH)+1;
		                       DateSearch.selected_day = c.get(Calendar.DAY_OF_MONTH);

		                       DateSearch.date =  DateSearch.selected_day+"/"+ DateSearch.selected_month+"/"+ DateSearch.selected_year;
		                       DateSearch.booking_date=  DateSearch.selected_year+"-"+ DateSearch.selected_month+"-"+ DateSearch.selected_day;
								 AlertDialog.Builder builder = new AlertDialog.Builder(con);
						   			
									
									
						   			
									//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
									
									
									 
									String[] words = {"Select a Salon", "Select a Service", "Select a Stylist","Back"};
									builder.setTitle("Date: "+ DateSearch.date);
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
		                   
		                   
		                   
		                   }
		               })
		               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                       // Send the negative button event back to the host activity
		                      
		                   }
		               });
		        builder.show();
		       
		        cal.setOnDateChangeListener(new OnDateChangeListener(){

					@Override
					public void onSelectedDayChange(CalendarView arg0,
							int year, int month, int day) {
						// TODO Auto-generated method stub
						
						DateSearch.selected_day=day;
						DateSearch.selected_month=month;
						DateSearch.selected_year=year;
						month=month+1;
						DateSearch. date = day+"/"+month+"/"+year;
						DateSearch.booking_date= year+"-"+month+"-"+day;
						
						 AlertDialog.Builder builder = new AlertDialog.Builder(con);
				   			
							
							
				   			
							//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
							
							
							 
							String[] words = {"Select a Salon", "Select a Service","Back"};
							builder.setTitle("Date: "+DateSearch.date);
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
			
		});
	
	}

	@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.activity_medi_app, menu);
	        menu.removeItem(R.id.back);
	        return true;
	    }
	   public boolean onOptionsItemSelected(MenuItem item) {
		   
		   
	        switch (item.getItemId()) {
	        case R.id.menu_profile:
	        	
	        	//DialogFragment dialog = new BookingDialog();
	        	
	        	Intent tute_intent= new Intent("com.example.salon.PROFILE");
			    startActivity(tute_intent);
			    break;
			    
	     
	        }
	        return true;}
	   
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
	        if (resultCode == RESULT_OK) {
	            Log.i("TAG", "Retrying");
	            new GetUserDetailsTask(ActivityMain.this, selected_account_name, SCOPE,
	                    REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
	          //  getTask(this, mEmail, SCOPE, REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
	            return;
	        }
	        if (resultCode == RESULT_CANCELED) {
	        	show_toast("User rejected authorization.");
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
	                  ActivityMain.this,
	                  REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
	              d.show();
	            }
	        });
	    }
	   
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	
	        	
	        	
		if (requestCode == REQUEST_CODE_RECOVER_FROM_AUTH_ERROR) {
            handleAuthorizeResult(resultCode, data);
            return;
        }
	    
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
