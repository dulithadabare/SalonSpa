package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class BookNow extends Activity {

	

	static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML node keys
	static final String  FirstName = "FirstName"; // parent node
	static final String LastName = "LastName";
	static final String EmpImage = "EmpImage";
	static final String JoinDate = "JoinDate";
	static final String EmailAdress = "EmailAdress";
	static final String EmpID = "EmpID";
	static final String jobTitle = "jobTitle";
	static final String BranchId = "BranchId";
	static final String CompanyID = "CompanyID";
	static final String Logo = "Logo";
	static final String Bannner = "Bannner";
	ListView list;
    LazyAdapter adapter;
    EditText inputSearch;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_now);
		OpenModifyThredPolicy();
	/*	ArrayList<HashMap<String, String>> EmpList = new ArrayList<HashMap<String, String>>();
			jsonParser json = new jsonParser(this);
		String Jsondata= 	json.getJsonFromUrl("http://192.168.6.29/Aggregator/index.php/api/Search_api/search_by_sallon/format/json"); 
		JSONArray jsonArray=	json.ConvertToJsonobj(Jsondata);
		//////////////////////////////////////////////////////////////////////////////////
		
		for (int i = 0; i <jsonArray.length(); i++) {
			// creating new HashMap
			 JSONObject jsonObject;
			try {
				jsonObject = jsonArray.getJSONObject(i);
		
			HashMap<String, String> map = new HashMap<String, String>();
			//Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(FirstName, jsonObject.getString( FirstName));
			map.put(LastName, jsonObject.getString( LastName));
			map.put(JoinDate, jsonObject.getString( JoinDate));
			map.put(EmailAdress, jsonObject.getString( EmailAdress));
			map.put(EmpID,jsonObject.getString( EmpID));
			map.put(jobTitle, jsonObject.getString( jobTitle));
			map.put(BranchId, jsonObject.getString( jobTitle));
			map.put(CompanyID, jsonObject.getString( jobTitle));
			map.put(Logo, jsonObject.getString( jobTitle));
			map.put(Bannner, jsonObject.getString( Bannner));
			EmpList.add(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		
			
		}
		*/
		/////////////////////////////////////////////////////////////////////////////////
		////////////////test block ///////////////////////////////////////////////
	final	RelativeLayout 	LayoutRight=(RelativeLayout)this.findViewById(R.id.LayoutRight);
		
	 final	RelativeLayout r1 = (RelativeLayout)this.findViewById(R.id.FullLayout);
		final Button v= (Button)this.findViewById(R.id.Animtest) ;
		v.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			r1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			r1.animate().translationXBy(100).setDuration(200);
			LayoutRight.animate().translationXBy(-300).setDuration(200); 
			//v.animate().translationYBy(100).setDuration(200);	
			}
			
		});
		
		
	}
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void OpenModifyThredPolicy(){
    	  if (android.os.Build.VERSION.SDK_INT > 9) {
	        	StrictMode.ThreadPolicy policy = 
	        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        	StrictMode.setThreadPolicy(policy);
	        	}

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_now, menu);
		return true;
	}

}
