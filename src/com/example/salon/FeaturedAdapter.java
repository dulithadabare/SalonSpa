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

public class FeaturedAdapter extends BaseAdapter{

    private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>>  originaldata= new  ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public FeaturedAdapter(Context a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        originaldata.addAll(d);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return 3;
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
        TextView Discription = (TextView)vi.findViewById(R.id.Discription); // artist name
        TextView Qualification = (TextView)vi.findViewById(R.id.Qualification); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        Name.setText(song.get(Service.KEY_SERVICE_NAME));
        Discription.setText(song.get(Service.KEY_SERVICE_DISCRIPTION));
        Qualification.setText("");
        imageLoader.DisplayImage(song.get(Service.KEY_SERVICE_THUMB_URL), thumb_image);
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
