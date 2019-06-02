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

public class ServiceFragmentAdapter extends BaseAdapter {
    
    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>>  originaldata= new  ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public ServiceFragmentAdapter(Context a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.row_service_fragment, null);

        TextView Name = (TextView)vi.findViewById(R.id.Name); 
        TextView servicePrice = (TextView)vi.findViewById(R.id.ServicePrice); // artist name
        TextView serviceID = (TextView)vi.findViewById(R.id.ServiceID); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> service = new HashMap<String, String>();
        service = data.get(position);
        
        // Setting all values in listview
        Name.setText(service.get(Service.KEY_SERVICE_NAME));
        servicePrice.setText("Cost : "+service.get(Service.KEY_SERVICE_PRICE));
        serviceID.setText(service.get(Service.KEY_SERVICE_ID));
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
        if(data.isEmpty()){
        	ServiceSearchFragment.no_result_layout.setVisibility(View.VISIBLE);
        }else{
        	ServiceSearchFragment.no_result_layout.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }

}
