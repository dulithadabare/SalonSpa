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

public class DialogListAdapter extends BaseAdapter{
	private Context activity;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>>  originaldata= new  ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public DialogListAdapter(Context c, ArrayList<HashMap<String, String>> d) {
        activity = c;
        data=d;
        originaldata.addAll(d);
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
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
    //    TextView Discription = (TextView)vi.findViewById(R.id.Discription); // artist name
   //     TextView Qualification = (TextView)vi.findViewById(R.id.Qualification); // duration
    //    ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        Name.setText(song.get(Salon.KEY_SALON_NAME));
       // Discription.setText(song.get(Salon.KEY_DISCRIPTION));
        //Qualification.setText("");
        //imageLoader.DisplayImage(song.get(Salon.KEY_THUMB_URL), thumb_image);
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
        notifyDataSetChanged();
    }
}
