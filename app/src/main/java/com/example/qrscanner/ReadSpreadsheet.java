package com.example.qrscanner;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.format.Time;
import android.view.View;

import com.google.gdata.client.authn.oauth.*;
import com.google.gdata.client.authn.oauth.OAuthParameters.OAuthType;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.*;
import com.google.gdata.data.batch.*;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters; 
//import com.google.gdata.client.docs.*;
import com.google.gdata.client.authn.oauth.*;

import java.io.IOException;
import java.net.*;
import java.util.*;


public class ReadSpreadsheet {
	
	
	private MainActivity m_activity;
	private EventGuests m_eventGuests = new EventGuests(); //For now, just one event
	
	public static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
	
	public class checkSpreadsheet extends AsyncTask<String, Void, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			
		    try {
				
		        GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		        oauthParameters.setOAuthConsumerKey (Config.CLIENT_ID);
		        oauthParameters.setOAuthConsumerSecret (Config.CLIENT_SECRET);
		        oauthParameters.setOAuthType (OAuthType.TWO_LEGGED_OAUTH);
		        oauthParameters.setScope ("https://spreadsheets.google.com/feeds/");
		        
				SpreadsheetService service = new SpreadsheetService("QRScanner");
				service.useSsl();
				service.setOAuthCredentials (oauthParameters, new OAuthHmacSha1Signer ());
			
		      // Notice that the url ends
		      // with default/public/values.
		      // That wasn't obvious (at least to me)
		      // from the documentation.
		      String urlString = Config.SPREADSHEET_PUBLIC_URL;

		      // turn the string into a URL
		      URL url = new URL(urlString);

		      
		      // You could substitute a cell feed here in place of
		      // the list feed (TODO: Might be useful for updating)
		      ListFeed feed = service.getFeed(url, ListFeed.class);
		      //CellFeed cellfeed = service.getFeed(url, CellFeed.class);

		      for (ListEntry entry : feed.getEntries()) {
		        CustomElementCollection elements = entry.getCustomElements();
		        String qrcontent = elements.getValue("qrcontent");
		        String entrytimestamp = elements.getValue("entrytimestamp");
		        
		        Guest guest = new Guest(elements);
		        
		        m_eventGuests.addGuest(qrcontent, guest);
		        
		        if(qrcontent.equals(params[0]) && (entrytimestamp == null || entrytimestamp.equals("")) ) {
			        //TODO: Need to update Entry Time stamp column (preferable move this somewhere else)
		        	//elements.setValueLocal("entrytimestamp", getCurrentDateTime());
		        	return true;
		        }
		        else if(qrcontent.equals(params[0])) {
		        	return false;
		        }
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return false;
		}
		
		@Override
	    protected void onPostExecute(Boolean result) {
			//Dialog.dismiss();
	    }
	}
	public static String getCurrentDateTime()
	{
		Time t = new Time(Time.getCurrentTimezone());
		t.setToNow();
		String date = t.format("%Y/%m/%d%H%M%S");
		return date;
		
	}
	public ReadSpreadsheet(MainActivity activity)
	{
		m_activity = activity;
	}
	public boolean verifyScan(String scannedContent) {
		return m_eventGuests.isGuestEntryValid(scannedContent);
		//TODO: Still need to update column after this
	}
	
	public boolean verifyScanFromSpreadsheet(String scannedContent) {
		
		try
		{
			checkSpreadsheet checking = new checkSpreadsheet();
			checking.execute(scannedContent);
	        boolean answer = checking.get();
	        return answer;
		}
		catch(Exception e){
			return false;
		}
	}
	
	
}
