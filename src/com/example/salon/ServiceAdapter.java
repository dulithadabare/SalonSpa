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

public class ServiceAdapter extends BaseAdapter {
    
    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>>  originaldata= new  ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    private String flow;
    
    public ServiceAdapter(Context a, ArrayList<HashMap<String, String>> d,String app_flow) {
        activity = a;
        data=d;
        flow=app_flow;
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
            vi = inflater.inflate(R.layout.row_service, null);

        TextView Name = (TextView)vi.findViewById(R.id.Name); 
        TextView serviceDes = (TextView)vi.findViewById(R.id.ServiceDes); // artist name
        TextView serviceID = (TextView)vi.findViewById(R.id.ServiceID); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> service = new HashMap<String, String>();
        service = data.get(position);
        
        // Setting all values in listview
        Name.setText(service.get(Service.KEY_SERVICE_NAME));
        if((flow.equals("SalonFlow_StylistFragment")) || (flow.equals("SalonFlow")) || (flow.equals("StylistFlow")) ){
        	serviceDes.setText("Cost: "+service.get(Service.KEY_SERVICE_PRICE));	
        }else{
        serviceDes.setText(service.get(Service.KEY_SERVICE_DISCRIPTION));
        }serviceID.setText(service.get(Service.KEY_SERVICE_ID));
        serviceID.setVisibility(View.INVISIBLE);
        imageLoader.DisplayImage(service.get(Service.KEY_SERVICE_THUMB_URL), thumb_image);
        return vi;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase();
        data.clear();
        if (charText.length() == 0) {
        	data.addAll(originaldata);
        } else {
            for (HashMap<String, String> dataraw : originaldata) {
            	
                if (dataraw.get(Service.KEY_SERVICE_NAME).toLowerCase().contains(charText)) {
                	data.add(dataraw);
                }
            }
        }
        notifyDataSetChanged();
    }

}
