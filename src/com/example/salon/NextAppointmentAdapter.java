package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NextAppointmentAdapter extends BaseAdapter {
    
    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>>  originaldata= new  ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    private String flow;
    
    public NextAppointmentAdapter(Context a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        originaldata.addAll(d);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.row_upcoming_appointments, null);

        TextView Name = (TextView)vi.findViewById(R.id.Name); 
        TextView appointmentDes = (TextView)vi.findViewById(R.id.AppointmentDes); 
        TextView appointmentID = (TextView)vi.findViewById(R.id.AppointmentID);
        TextView appointmentDate = (TextView)vi.findViewById(R.id.DateTime);
        
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> appointment = new HashMap<String, String>();
        appointment = data.get(position);
        
        // Setting all values in listview
        Name.setText(appointment.get(Appointment.KEY_SERVICE_NAME));
      
        appointmentDes.setText("at "+appointment.get(Appointment.KEY_BRANCH_NAME)+" with "+appointment.get(Appointment.KEY_EMPLOYEE_FNAME));
        appointmentDate.setText("on "+appointment.get(Appointment.KEY_START_TIME));
        appointmentID.setText(appointment.get(Appointment.KEY_APPOINTMENT_ID));
        appointmentID.setVisibility(View.INVISIBLE);
        imageLoader.DisplayImage(appointment.get(Appointment.KEY_SERVICE_THUMB_URL), thumb_image);
        return vi;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase();
        data.clear();
        if (charText.length() == 0) {
        	data.addAll(originaldata);
        } else {
            for (HashMap<String, String> dataraw : originaldata) {
            	
                if (dataraw.get(Appointment.KEY_SERVICE_NAME).toLowerCase().contains(charText)) {
                	data.add(dataraw);
                }
            }
        }
        notifyDataSetChanged();
    }

}
