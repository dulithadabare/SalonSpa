package com.example.salon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;

public class DateSearchResultGetUserDetails extends AsyncTask<Void, Void, Void>{
    private static final String TAG = "TokenInfoTask";
    private static final String GIVEN_NAME_KEY = "given_name";
    private static final String NAME_KEY = "name";
    private static final String FAMILY_NAME_KEY = "family_name";
    private static final String PICTURE = "picture";
    
    private static final String ID = "id"; 
    private static final String GENDER = "gender";
    
    protected DateSearchResult mActivity;

    protected String mScope;
    protected String mEmail;
    protected int mRequestCode;

    DateSearchResultGetUserDetails(DateSearchResult activity, String email, String scope, int requestCode) {
        this.mActivity = activity;
        this.mScope = scope;
        this.mEmail = email;
        this.mRequestCode = requestCode;
    }

    @Override
    protected Void doInBackground(Void... params) {
      try {
        fetchNameFromProfileServer();
      } catch (IOException ex) {
        onError("Following Error occured, please try again. " + ex.getMessage(), ex);
      } catch (JSONException e) {
        onError("Bad response: " + e.getMessage(), e);
      }
      return null;
    }

    protected void onError(String msg, Exception e) {
        if (e != null) {
          Log.e(TAG, "Exception: ", e);
        }
        mActivity.show_toast(msg);  // will be run in UI thread
    }

    /**
     * Get a authentication token if one is not available. If the error is not recoverable then
     * it displays the error message on parent activity.
     */
    protected  String fetchToken() throws IOException{
    	
    	
    	 try {
             return GoogleAuthUtil.getToken(DateSearchResult.CON, mEmail, mScope);
         } catch (GooglePlayServicesAvailabilityException playEx) {
             // GooglePlayServices.apk is either old, disabled, or not present.
             mActivity.showErrorDialog(playEx.getConnectionStatusCode());
         } catch (UserRecoverableAuthException userRecoverableException) {
             // Unable to authenticate, but the user can fix this.
             // Forward the user to the appropriate activity.
             mActivity.startActivityForResult(userRecoverableException.getIntent(), mRequestCode);
         } catch (GoogleAuthException fatalException) {
             onError("Unrecoverable error " + fatalException.getMessage(), fatalException);
         }
         return null;
    	
    }

    /**
     * Contacts the user info server to get the profile of the user and extracts the first name
     * of the user from the profile. In order to authenticate with the user info server the method
     * first fetches an access token from Google Play services.
     * @throws IOException if communication with user info server failed.
     * @throws JSONException if the response from the server could not be parsed.
     */
    private void fetchNameFromProfileServer() throws IOException, JSONException {
        String token = fetchToken();
        if (token == null) {
          // error has already been handled in fetchToken()
          return;
        }
        URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int sc = con.getResponseCode();
        if (sc == 200) {
          InputStream is = con.getInputStream();
          String name = getFirstName(readResponse(is));
          mActivity.show_toast("Hello " + name + "!");
          is.close();
          return;
        } else if (sc == 401) {
            GoogleAuthUtil.invalidateToken(DateSearchResult.CON, token);
            onError("Server auth error, please try again.", null);
            Log.i(TAG, "Server auth error: " + readResponse(con.getErrorStream()));
            return;
        } else {
          onError("Server returned the following error code: " + sc, null);
          return;
        }
    }

    /**
     * Reads the response from the input stream and returns it as a string.
     */
    private static String readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = is.read(data, 0, data.length)) >= 0) {
            bos.write(data, 0, len);
        }
        return new String(bos.toByteArray(), "UTF-8");
    }

    /**
     * Parses the response and returns the first name of the user.
     * @throws JSONException if the response is not JSON or if first name does not exist in response
     */
    private String getFirstName(String jsonResponse) throws JSONException {
      JSONObject profile = new JSONObject(jsonResponse);
      
      if(profile.has(NAME_KEY))
      {
    	  ActivityMain.salonspa_user_name= profile.getString(NAME_KEY);
      }
      if(profile.has(ID))
      {
    	  ActivityMain.salonspa_user_googleid= profile.getString(ID);  
      }
      if(profile.has(GIVEN_NAME_KEY))
      {
    	  ActivityMain.salonspa_user_givenname= profile.getString(GIVEN_NAME_KEY);  
      }
      if(profile.has(FAMILY_NAME_KEY))
      {
    	  ActivityMain.salonspa_user_familyname= profile.getString(FAMILY_NAME_KEY);  
      }
      if(profile.has(PICTURE))
      {
    	  ActivityMain.salonspa_user_picture= profile.getString(PICTURE);  
      }
      if(profile.has(GENDER))
      {
    	  ActivityMain.salonspa_user_gender= profile.getString(GENDER);  
      }
      
     
      String test= "test";
      return test;
    }

	@Override
	protected void onPostExecute(Void result) {
		
		// TODO Auto-generated method stub
		if(ActivityMain.salonspa_user_googleid.equals("NA")){
			ServiceSearch.mDialog.dismiss();
		}
		else if((ActivityMain.salonspa_user_givenname.equals("NA") || ActivityMain.salonspa_user_familyname.equals("NA"))){
			mActivity.show_dialog();
			
		}
		else{
		new DateSearchResultUserCreateTask(mActivity).execute();
		}
		super.onPostExecute(result);
	}
    


}
