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

public class UnifiedSalonFragmentAdapter extends BaseAdapter {
    
    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>>  originaldata= new  ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public UnifiedSalonFragmentAdapter(Context c, ArrayList<HashMap<String, String>> d) {
        activity = c;
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
            vi = inflater.inflate(R.layout.row_salon, null);

        TextView Name = (TextView)vi.findViewById(R.id.Name); 
        TextView Address = (TextView)vi.findViewById(R.id.Address); 
        TextView BranchID = (TextView)vi.findViewById(R.id.BranchID); 
        TextView MapID = (TextView)vi.findViewById(R.id.MapID); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        
        HashMap<String, String> salon = new HashMap<String, String>();
        salon = data.get(position);
        
        // Setting all values in listview
        Name.setText(salon.get(Salon.KEY_SALON_NAME));
        Address.setText("at "+salon.get(Salon.KEY_CITY));
        BranchID.setText(salon.get(Salon.KEY_ID));
        MapID.setText(salon.get(Salon.KEY_MAP_ID));
        BranchID.setVisibility(View.INVISIBLE);
        imageLoader.DisplayImage(salon.get(Salon.KEY_THUMB_URL), thumb_image);
        return vi;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase();
        data.clear();
        if (charText.length() == 0) {
        	data.addAll(originaldata);
        } else {
            for (HashMap<String, String> dataraw : originaldata) {
            	
                if (dataraw.get(Salon.KEY_SALON_NAME).toLowerCase().contains(charText)) {
                	data.add(dataraw);
                }
            }
        }
        
        if(data.isEmpty()){
        	UnifiedSalonFragment.no_result_layout.setVisibility(View.VISIBLE);
        }else{
        	UnifiedSalonFragment.no_result_layout.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }

}
