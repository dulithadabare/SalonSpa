package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Salon extends Activity {

	//static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML node keys
	static final String  KEY_SALON_NAME = "BranchName"; // parent node
	static final String KEY_ID = "BranchId";
	static final String KEY_MAP_ID = "MapId";
	static final String KEY_BRANCH_EMAIL = "BranchEmail";
	static final String KEY_DISCRIPTION = "Branchinfo";
	static final String KEY_CITY = "City";
	static final String KEY_COUNTRY = "Country";
	static final String KEY_ZIPCODE = "Zipcode";
	static final String KEY_PROVINCE = "Province";
	static final String KEY_STREET1 = "BranchStreet1";
	static final String KEY_STREET2 = "BranchStreet2";
	static final String KEY_LONGITUDE = "Longitude";
	static final String KEY_LATITUDE = "Latitude";
	static final String KEY_CATOGORY = "CompanyName";
	static final String KEY_THUMB_URL = "Logo";
	
	ListView list;
    LazyAdapter adapter;
    EditText inputSearch;
   
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_app);
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}
     		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
     			jsonParser json = new jsonParser(this);
     		String Jsondata= 	json.getJsonFromUrl("http://192.168.6.29/Aggregator/index.php/api/Search_api/search_by_sallon/format/json"); 
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
			map.put(KEY_ID, jsonObject.getString( KEY_ID));
			map.put(KEY_SALON_NAME, jsonObject.getString( KEY_SALON_NAME));
			map.put(KEY_BRANCH_EMAIL, jsonObject.getString( KEY_BRANCH_EMAIL));
			map.put(KEY_DISCRIPTION, jsonObject.getString( KEY_DISCRIPTION));
			map.put(KEY_THUMB_URL,jsonObject.getString( KEY_THUMB_URL));
			map.put(KEY_CATOGORY, jsonObject.getString( KEY_CATOGORY));
			songsList.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// adding HashList to ArrayList
			
		}
		list=(ListView)findViewById(R.id.listall);
		
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);        
        list.setAdapter(adapter);
       // list.setOnScrollListener(l)
        final Intent intent = new Intent(this, BookNow.class);

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				  overridePendingTransition(R.animator.slide_out,R.animator.slide_up);
				
				
			}

		});		
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				adapter.filter(arg0.toString());
				// TODO Auto-generated method stub
			
			} 
        	
        
            
                    });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_medi_app, menu);
        return true;
    }
}
