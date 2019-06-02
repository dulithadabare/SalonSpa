package com.example.salon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class BookingDialog extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_dialog);
		List<String> list1 = new ArrayList<String>();
		list1.add("Akila");
		list1.add("Chamath");
		list1.add("Dimithra");
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner1 =(Spinner) findViewById(R.id.stylist_spinner);
		spinner1.setAdapter(dataAdapter);
		
		
		
	}
	
	
	
    }

	
	

