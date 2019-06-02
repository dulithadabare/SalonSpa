package com.example.salon;


import java.io.IOException;

import java.io.StringReader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.data.rest.RequestMethod;
import com.data.rest.RestClient;

import android.content.Context;
import android.util.Log;

public class jsonParser {
	RestClient  rest;
	Context con;
    // constructor
    public jsonParser(Context con ) {
 this.con=con;
    }
 
    /**
     * Getting XML from URL making HTTP request
     * @param url string
     * */
    public String getJsonFromUrl(String url) {
   
    	String Strjson = null;
    	  rest = new RestClient(url);
    	try {
			rest.Execute(RequestMethod.POST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Strjson=	rest.getResponse();    	
    	         return Strjson ;
      
    }
    public void SetUrl(String url){
    	  rest = new RestClient(url);
    	
    }
    
    public void SetParams(String params,String value){
    	rest.AddParam(params, value);
    	
    	
    }
    
    public String ExecuteRequest(){
    	String Strjson = null;
    	try {
    	
    		rest.AddParam("XKEY", ActivityMain.ApiKey);
			rest.Execute(RequestMethod.POST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Strjson=	rest.getResponse();    	
    	         return Strjson ;
    	
    	
    }
    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
 
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is); 
 
            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            }
 
            return doc;
    }
 
    /** Getting node value
      * @param elem element
      */
    
    public JSONArray  ConvertToJsonobj(String  JsonStr){
    	
    	JSONArray jsonArray=new JSONArray();
        try {
            JSONObject jsonObj = new JSONObject(JsonStr);
                  String valuve=  jsonObj.get("data").toString();
            if (!valuve.equals("0")){
                jsonArray = new JSONArray(valuve);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return jsonArray;
    	
    }
    
     public final String getElementValue( Node elem ) {
         Node child;
         if( elem != null){
             if (elem.hasChildNodes()){
                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                     if( child.getNodeType() == Node.TEXT_NODE  ){
                         return child.getNodeValue();
                     }
                 }
             }
         }
         return "";
     }
 
     /**
      * Getting node value
      * @param Element node
      * @param key string
      * */
     public String getValue(Element item, String str) {
            NodeList n = item.getElementsByTagName(str);
            return this.getElementValue(n.item(0));
        }
}

