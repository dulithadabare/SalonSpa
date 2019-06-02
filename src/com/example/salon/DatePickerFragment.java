package com.example.salon;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

	
	
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current date as the default date in the picker
	        final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);

	        // Create a new instance of DatePickerDialog and return it
	        return new DatePickerDialog(getActivity(), this, year, month, day);
	    }

	    public void onDateSet(DatePicker view, int year, int month, int day) {
	        // Do something with the date chosen by the user
	    	//Toast.makeText(getActivity(), "msg msg", Toast.LENGTH_SHORT).show();
	    	 
	    	  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	   			
				
				
	   			
				//TextView dlgText = (TextView) view.findViewById(R.id.textView1);
				
				
				 
				String[] words = {"Select Salon", "Select Service", "Select Stylist","Back"};
				builder.setTitle("Hair Treatment");
		        builder
		        .setItems(words, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   switch(which){
		            	   
		            	   
		            	   case 0:
		            		   Intent tute_intent= new Intent(getActivity(),SalonSearch.class);
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
	    
	    
	    
}
