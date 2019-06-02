package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class UnifiedServiceFragment extends Fragment{
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	static UnifiedServiceFragmentAdapter ServiceAdapter;
	Context con;
	
	Boolean first_check=true;
	static String QUERY;

	static LinearLayout linlaHeaderProgress;
	static RelativeLayout no_result_layout;
	static ListView list;
	static Context CON;
	static Toast toast;
	
	public static final UnifiedServiceFragment newInstance(String message)

	{
		UnifiedServiceFragment f = new UnifiedServiceFragment();
	Bundle bdl = new Bundle(1);
	bdl.putString(EXTRA_MESSAGE, message);
	QUERY=message;
	f.setArguments(bdl);
	return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		String message = getArguments().getString(EXTRA_MESSAGE);
		View v = inflater.inflate(R.layout.my_fragment, container, false);
	//	TextView messageTextView = (TextView)v.findViewById(R.id.textView);
		list=(ListView)v.findViewById(R.id.listall);
		linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);
		no_result_layout    = (RelativeLayout) v.findViewById(R.id.no_result_layout);
		
        final EditText  search = (EditText) v.findViewById(R.id.inputSearch);
        search.setHint("Search Services..");
        TextView no_result_message =(TextView) v.findViewById(R.id.no_result_message);
        no_result_message.setText("Sorry, couldn't find any Services");
		con = getActivity();
		CON = getActivity();
		
        ((Activity) con).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		
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
		
				ServiceAdapter.filter(arg0.toString());
				
				
				// TODO Auto-generated method stub
			
			} 
        	
        
            
                    });
   

			  
				new UnifiedServiceTask().execute();
				
			

	      
list.setOnItemClickListener(new OnItemClickListener() {
	            
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					ServiceSearch.serviceID = (String) ((TextView)arg1.findViewById(R.id.ServiceID)).getText();
					ServiceSearch.serviceName = (String) ((TextView)arg1.findViewById(R.id.Name)).getText();
					
					ActivityMain.Flow="ServiceFlow";
					Intent tute_intent= new Intent("com.example.salon.SALONSEARCH");
					tute_intent.putExtra("LAST_ACTIVITY", "ServiceSearch");
					//tute_intent.putExtra("LAST_ACTIVITY", "Main");
				//	Bundle translateBundle = ActivityOptions.makeCustomAnimation(ActivityMain.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
					startActivity(tute_intent);
				
				}

			});
		//messageTextView.setText(message);
		return v;
	}


}
