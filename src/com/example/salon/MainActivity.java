package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.salon.BookNow;
import com.example.salon.LazyAdapter;
import com.example.salon.jsonParser;


import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	int var=1;
	static final String  KEY_SALON_NAME = "ComanyName"; // parent node
	static final String KEY_ID = "CompanyID";
	static final String KEY_QULIFICATION = "CompanyEmail";
	static final String KEY_DISCRIPTION = "ComanyName";
	static final String KEY_CATOGORY = "ComanyName";
	static final String KEY_THUMB_URL = "Logo";
	
	Context con;
	
	float dpHeight;
	float dpWidth;
	ListView list;
    LazyAdapter adapter;
    UnifiedServiceFragmentAdapter ServiceAdapter;
    StylistFragmentAdapter StylistAdapter;
    int selected_b = 0;
    
    public void SalonList(){
    	
    }
    

	 public void set_animation(View v,float x,String axis){
		 if(axis=="x"){
		 v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
	     v.animate().translationXBy(x).setDuration(200);
		 }else{
			 v.setLayerType(View.LAYER_TYPE_HARDWARE, null); 
			 v.animate().translationYBy(x).setDuration(200);
		 }
     	
     }
	 public void get_free_times(int salon_id,int service_id, int stylist_id){
		    
		 // send details to server
		 // get details
		 // put into array
		 //return lisrtview
		   
	   }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=this;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    	SearchView searchView = (SearchView) findViewById(R.id.searchView);
    	SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
    	searchView.setSearchableInfo(searchableInfo);
    	
  
        
   //     ActionBar actionBar = getActionBar();
     //   actionBar.setDisplayHomeAsUpEnabled(true);

        float density  = getResources().getDisplayMetrics().density;
         dpHeight = outMetrics.heightPixels / density;
         dpWidth  = outMetrics.widthPixels / density;
        
        
        
        final Button date = (Button)this.findViewById(R.id.date)  ;
        final Button salon = (Button)this.findViewById(R.id.salon)  ;
        final Button service = (Button)this.findViewById(R.id.service)  ;
        final Button stylist = (Button)this.findViewById(R.id.stylist)  ;
        final Button book = (Button)this.findViewById(R.id.book)  ;
        final ImageView back = (ImageView)this.findViewById(R.id.back_b)  ;
        final ImageView back_left = (ImageView)this.findViewById(R.id.back_left)  ;
        final TextView notice = (TextView)this.findViewById(R.id.notice)  ;
        list=(ListView)findViewById(R.id.listall);
		set_animation(list,1000,"x");
        
     
        
        
        back.setVisibility(View.INVISIBLE);
        
        
        
        final EditText  search = (EditText) this.findViewById(R.id.inputSearch);
        
        
        search.setVisibility(View.INVISIBLE);
       
        final CalendarView calView = (CalendarView) findViewById(R.id.cal);
        //calView.setVisibility(View.INVISIBLE);
        set_animation(calView,-1000,"x");
        
       

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
          
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				switch(selected_b){
				case 3:
					Intent tute_intent3= new Intent("com.example.salon.ACTIVITYMAIN");
					startActivity(tute_intent3);
					  
					  break;
				case 1:
					Intent tute_intent= new Intent("com.example.salon.SALONSEARCH");
					startActivity(tute_intent);
					  break;
				    
				case 2:

					Intent tute_intent2= new Intent("com.example.salon.SERVICESEARCH");
					startActivity(tute_intent2);
					  break;
				
				}
				
			}

		});
book.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){
				
			
			
			}
			
		});
        
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
				if(selected_b==3){
				adapter.filter(arg0.toString());}
				if(selected_b==2){
					StylistAdapter.filter(arg0.toString());}
				if(selected_b==1){
					ServiceAdapter.filter(arg0.toString());}
				
				// TODO Auto-generated method stub
			
			} 
        	
        
            
                    });
        
        
      //fromXposition- x coordinate from  where animation should start
      //toXPosition- x coordinate at which animation would end
      //fromYPosition- y coordinate from where animation should start.
      //toYPosition- y coordinate at which animation would end.
       
      //You can also set duration for the animation that means you can set for how long the animation should last:
     
      //You can now apply the animation to a view
        
       calView.setOnDateChangeListener(new OnDateChangeListener() {

           public void onSelectedDayChange(CalendarView view, int year, int month,
                   int dayOfMonth) {
                //Toast.makeText(getApplicationContext(), ""+dayOfMonth, 0).show();// TODO Auto-generated method stub
        	   
        	   AlertDialog.Builder builder = new AlertDialog.Builder(con);
   			
				
				
   			
				//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
				
				
				 
				String[] words = {"Select Salon", "Select Service", "Select Stylist","Back"};
				builder.setTitle("Hair Treatment");
		        builder
		        .setItems(words, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   switch(which){
		            	   
		            	   
		            	   case 0:
		            		   Intent tute_intent= new Intent("com.example.salon.SALONSEARCH");
		   					startActivity(tute_intent);
		            		   break;
		            	   case 1:
		            		   Intent tute_intent2= new Intent("com.example.salon.SERVICESEARCH");
		   					startActivity(tute_intent2);
		            		   break;
		            	   case 2:
		            		   Intent tute_intent3= new Intent("com.example.salon.STYLISTSEARCH");
		   					startActivity(tute_intent3);
		            		   break;
		            	   
		            	   }
		            	   
		               }
		        });
		        builder.show();
           }
       });
       
        
   
    date.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			
			
		//	invalidateOptionsMenu();
			
			
		  
		  set_animation(date,1000,"x");
		  set_animation(stylist,1000,"x");
		  set_animation(book,1000,"x"); 
		  
		  set_animation(salon,1000,"x");
		  set_animation(service,1000,"x");
		  
		  
		  
		  notice.setVisibility(View.INVISIBLE);
		  set_animation(calView,1000,"x");
		  back_left.setVisibility(View.VISIBLE);
		  selected_b=4;
		  
		
			 
		}
	});
    stylist.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			
			 
	        if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}
	     		ArrayList<HashMap<String, String>> StylistList = new ArrayList<HashMap<String, String>>();
	     			jsonParser json = new jsonParser(con);
	     		String Jsondata= 	json.getJsonFromUrl("http://192.168.6.7/Aggrigator/index.php/api/Search_api/search_by_styler/format/json"); 
	     		JSONArray jsonArray=	json.ConvertToJsonobj(Jsondata);
	     		
			//XMLParser parser = new XMLParser(this);
			//String xml = parser.getXmlFromUrl(URL); // getting XML from URL
			//Document doc = parser.getDomElement(xml); // getting DOM element
			
			//NodeList nl = doc.getElementsByTagName("DoctorInfo");
			// looping through all song nodes <song>
			for (int i = 0; i <jsonArray.length(); i++) {
				// creating new HashMap
				 JSONObject jsonObject;
				try {
					jsonObject = jsonArray.getJSONObject(i);
			
				HashMap<String, String> map = new HashMap<String, String>();
				//Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
		
                //Element e = (Element) nl.item(i);
                // adding each child node to HashMap key => value
                map.put(Stylist.KEY_FIRST_NAME, jsonObject.getString(Stylist.KEY_FIRST_NAME));
                map.put( Stylist.KEY_LAST_NAME, jsonObject.getString( Stylist.KEY_LAST_NAME));
                map.put(Stylist.KEY_STYLIST_ID, jsonObject.getString( Stylist.KEY_STYLIST_ID));
                map.put(Stylist.KEY_STYLIST_JOBTITLE, jsonObject.getString( Stylist.KEY_STYLIST_JOBTITLE));
                map.put(Stylist.KEY_STYLIST_THUMB_URL,jsonObject.getString( Stylist.KEY_STYLIST_THUMB_URL));
                map.put("companyDetails", jsonObject.getString("0"));
           
           
				StylistList.add(map);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// adding HashList to ArrayList
				
			}
			
			
			// Getting adapter by passing xml data ArrayList
	        StylistAdapter=new StylistFragmentAdapter(con, StylistList);        
	        list.setAdapter(StylistAdapter);
	        
			
		  set_animation(date,-1000,"x");
		  set_animation(stylist,-1000,"x");
		  set_animation(book,-1000,"x"); 
		  search.setHint("Search Stylist..");
		  set_animation(salon,-1000,"x");
		  set_animation(service,-1000,"x");
		  search.setVisibility(View.VISIBLE);
		 
		  back.setVisibility(View.VISIBLE);
		  notice.setVisibility(View.INVISIBLE);
		  set_animation(list,-1000,"x");
		  selected_b=2;
		  
			
			 
		}
	});
    back_left.setOnClickListener(new View.OnClickListener() {
		
 		@Override
 		public void onClick(final View v) {
 			// TODO Auto-generated method stub
 			
 			set_animation(date,-1000,"x");
			  set_animation(stylist,-1000,"x");
			  set_animation(book,-1000,"x"); 
			  
			  set_animation(salon,-1000,"x");
			  set_animation(service,-1000,"x");
			  set_animation(calView,-1000,"x");
			  back_left.setVisibility(View.INVISIBLE);
			  notice.setVisibility(View.VISIBLE);
 		  
 			
 			 
 		}
 	});
    back.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			
			switch(selected_b){
			case 3:
				set_animation(date,1000,"y");
				  set_animation(stylist,1000,"y");
				  set_animation(book,1000,"y");  
				  
				  set_animation(salon,1000,"y");
				  set_animation(service,1000,"y");
				  search.setVisibility(View.INVISIBLE);
				  set_animation(list,1000,"x");
				  back.setVisibility(View.INVISIBLE);
				  notice.setVisibility(View.VISIBLE);
				  search.setText("");
				  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				  imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
				  
				  break;
			case 1:
				set_animation(date,-1000,"y");
				  set_animation(stylist,-1000,"y");
					 
				  set_animation(book,-1000,"y"); 
				  set_animation(salon,-1000,"y");
				  set_animation(service,-1000,"y");
				  search.setVisibility(View.INVISIBLE);
				  set_animation(list,1000,"x");
				  back.setVisibility(View.INVISIBLE);
				  notice.setVisibility(View.VISIBLE);
				  search.setText("");
				  InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				  imm1.hideSoftInputFromWindow(search.getWindowToken(), 0);
				  
			      break;
			case 2:
				set_animation(date,1000,"x");
				  set_animation(stylist,1000,"x");
				  set_animation(book,1000,"x"); 
				  
				  set_animation(salon,1000,"x");
				  set_animation(service,1000,"x");
				  search.setVisibility(View.INVISIBLE);
				 
				  back.setVisibility(View.INVISIBLE);
				  notice.setVisibility(View.VISIBLE);
				  set_animation(list,1000,"x");
				  search.setText("");
				  InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				  imm2.hideSoftInputFromWindow(search.getWindowToken(), 0);
				  
				  break;
			
			}
			
			/*if(var==1){
		  set_animation(date,-450,"x");
		  set_animation(stylist,-450,"x");
		  set_animation(book,-450,"x"); 
		  search.setHint("Search Stylist..");
		  set_animation(salon,-450,"x");
		  set_animation(service,-450,"x");
		  search.setVisibility(View.VISIBLE);
		  list.setVisibility(View.VISIBLE);
		  back.setVisibility(View.VISIBLE);
		  
			var=2; 
			} else if(var==2){
				set_animation(date,450,"x");
				  set_animation(stylist,450,"x");
				  set_animation(book,450,"x"); 
				  
				  set_animation(salon,450,"x");
				  set_animation(service,450,"x");
				  search.setVisibility(View.INVISIBLE);
				  list.setVisibility(View.INVISIBLE);
				  back.setVisibility(View.INVISIBLE);
				  var=1;
			}*/
			 
		}
	});
  
service.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			
			 if (android.os.Build.VERSION.SDK_INT > 9) {
		        	StrictMode.ThreadPolicy policy = 
		        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        	StrictMode.setThreadPolicy(policy);
		        	}
		     		ArrayList<HashMap<String, String>> ServiceList = new ArrayList<HashMap<String, String>>();
		     			jsonParser json = new jsonParser(con);
		     		String Jsondata= 	json.getJsonFromUrl("http://192.168.6.7/Aggrigator/index.php/api/Search_api/search_by_services/format/json"); 
		     		JSONArray jsonArray=	json.ConvertToJsonobj(Jsondata);
		     		
				//XMLParser parser = new XMLParser(this);
				//String xml = parser.getXmlFromUrl(URL); // getting XML from URL
				//Document doc = parser.getDomElement(xml); // getting DOM element
				
				//NodeList nl = doc.getElementsByTagName("DoctorInfo");
				// looping through all song nodes <song>
				for (int i = 0; i <jsonArray.length(); i++) {
					// creating new HashMap
					 JSONObject jsonObject;
					try {
						jsonObject = jsonArray.getJSONObject(i);
				
					HashMap<String, String> map = new HashMap<String, String>();
					//Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					map.put(Service.KEY_SERVICE_ID, jsonObject.getString(Service.KEY_SERVICE_ID));
					map.put( Service.KEY_SERVICE_NAME, jsonObject.getString( Service.KEY_SERVICE_NAME));
					map.put( Service.KEY_SERVICE_DISCRIPTION, jsonObject.getString( Service.KEY_SERVICE_DISCRIPTION));
					map.put( Service.KEY_SERVICE_DURATION, jsonObject.getString( Service.KEY_SERVICE_DURATION));
					map.put( Service.KEY_SERVICE_PRICE,jsonObject.getString( Service.KEY_SERVICE_PRICE));
					map.put(Service.KEY_SERVICE_THUMB_URL, jsonObject.getString(Service.KEY_SERVICE_THUMB_URL));
					map.put( Service.KEY_SERVICE_PADTIME, jsonObject.getString( Service.KEY_SERVICE_PADTIME));
					map.put(Service.KEY_SERVICE_COLOUR, jsonObject.getString(Service.KEY_SERVICE_COLOUR));
					map.put(Service.KEY_SERVICE_CATEGORYID, jsonObject.getString(Service.KEY_SERVICE_CATEGORYID));
					map.put(Service.KEY_SERVICE_CATEGORY_NAME, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_NAME));
					map.put(Service.KEY_SERVICE_CATEGORY_DES, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_DES));
				//	map.put(Service.KEY_SERVICE_CATEGORY_ITEM, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_ITEM));
					map.put(Service.KEY_SERVICE_CATEGORY_URL, jsonObject.getString(Service.KEY_SERVICE_CATEGORY_URL));
					ServiceList.add(map);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// adding HashList to ArrayList
					
				}
				
				
				// Getting adapter by passing xml data ArrayList
		        ServiceAdapter=new UnifiedServiceFragmentAdapter(con, ServiceList);        
		        list.setAdapter(ServiceAdapter);
			
		  set_animation(date,1000,"y");
		  set_animation(stylist,1000,"y");
		  set_animation(book,1000,"y"); 
		  
		  set_animation(salon,1000,"y");
		  set_animation(service,1000,"y");
		  search.setHint("Search Salon..");
		  search.setVisibility(View.VISIBLE);
		  set_animation(list,-1000,"x");
		  back.setVisibility(View.VISIBLE);
		  notice.setVisibility(View.INVISIBLE);
		  selected_b=1;
		  		 
		}
	});
   
salon.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		
		 
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}
     		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
     			jsonParser json = new jsonParser(con);
     		String Jsondata= 	json.getJsonFromUrl("http://192.168.6.7/Aggrigator/index.php/api/Search_api/search_by_sallon/format/json"); 
     		JSONArray jsonArray=	json.ConvertToJsonobj(Jsondata);
     		
		//XMLParser parser = new XMLParser(this);
		//String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		//Document doc = parser.getDomElement(xml); // getting DOM element
		
		//NodeList nl = doc.getElementsByTagName("DoctorInfo");
		// looping through all song nodes <song>
		for (int i = 0; i <jsonArray.length(); i++) {
			// creating new HashMap
			 JSONObject jsonObject;
			try {
				jsonObject = jsonArray.getJSONObject(i);
		
			HashMap<String, String> map = new HashMap<String, String>();
			//Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(Salon.KEY_ID, jsonObject.getString( Salon.KEY_ID));
			map.put(Salon.KEY_SALON_NAME, jsonObject.getString( Salon.KEY_SALON_NAME));
			map.put(Salon.KEY_BRANCH_EMAIL, jsonObject.getString( Salon.KEY_BRANCH_EMAIL));
			map.put(Salon.KEY_DISCRIPTION, jsonObject.getString( Salon.KEY_DISCRIPTION));
			map.put(Salon.KEY_THUMB_URL,jsonObject.getString( Salon.KEY_THUMB_URL));
			map.put(Salon.KEY_CATOGORY, jsonObject.getString( Salon.KEY_CATOGORY));
			songsList.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// adding HashList to ArrayList
			
		}
		
		
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(con, songsList);        
        list.setAdapter(adapter);
	
	  set_animation(date,-1000,"y");
	  set_animation(stylist,-1000,"y");
	  set_animation(book,-1000,"y"); 	 
	  
	  set_animation(salon,-1000,"y");
	  set_animation(service,-1000,"y");
	  search.setHint("Search Service..");
	  search.setVisibility(View.VISIBLE);
	  set_animation(list,-1000,"x");
	  back.setVisibility(View.VISIBLE);
	  notice.setVisibility(View.INVISIBLE);
	  selected_b=3;
	
		
		 
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
    
 /*  @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
	
	   if(selected_b==4){
	   menu.removeItem(R.id.menu_profile);
	   menu.add(R.id.back);
	   
	   }
	   return true;
	
	
	   
	   
	   
   }*/
}
