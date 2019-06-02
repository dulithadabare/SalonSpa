package com.example.salon;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 

public class StylistFragmentAdapter extends BaseAdapter {
    
    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>>  originaldata= new  ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public StylistFragmentAdapter(Context a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.row_stylist_fragment, null);

        TextView Name = (TextView)vi.findViewById(R.id.Name); 
        TextView Discription = (TextView)vi.findViewById(R.id.Discription); 
        TextView stylistID = (TextView)vi.findViewById(R.id.StylistID); 
        TextView salonID = (TextView)vi.findViewById(R.id.salonID); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        
        HashMap<String, String> stylist = new HashMap<String, String>();
        stylist = data.get(position);
        
        // Setting all values in listview
        String name = stylist.get(Stylist.KEY_FIRST_NAME)+" "+ stylist.get(Stylist.KEY_LAST_NAME);
        Name.setText(name);
        Discription.setText(stylist.get(Stylist.KEY_STYLIST_DES));
        stylistID.setText(stylist.get(Stylist.KEY_STYLIST_ID));
        salonID.setText(stylist.get(Stylist.KEY_MAP_ID));
        stylistID.setVisibility(View.INVISIBLE);
        imageLoader.DisplayImage(stylist.get(Stylist.KEY_STYLIST_THUMB_URL), thumb_image);
        return vi;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase();
        data.clear();
        if (charText.length() == 0) {
        	data.addAll(originaldata);
        } else {
            for (HashMap<String, String> dataraw : originaldata) {
            	
                if (dataraw.get(Stylist.KEY_FIRST_NAME).toLowerCase().contains(charText)) {
                	data.add(dataraw);
                }
            }
        }
        if(data.isEmpty()){
        	StylistSearchFragment.no_result_layout.setVisibility(View.VISIBLE);
        }else{
        	StylistSearchFragment.no_result_layout.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }
}