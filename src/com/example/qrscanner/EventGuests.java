package com.example.qrscanner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.api.client.util.DateTime;

public class EventGuests {
	Map<String, Guest> m_listOfGuests;
	
	public EventGuests()
	{
		m_listOfGuests = new HashMap<String, Guest>();
	}
	public void addGuest(String qrContent, Guest guest ) {
		if(m_listOfGuests.get(qrContent) == null) {
			m_listOfGuests.put(qrContent,  guest);
		}
	}
	public boolean isGuestEntryValid(String qrContent) {
		Guest guest = m_listOfGuests.get(qrContent);
		if(guest == null || guest.getEntryTimeStamp() != null) {
			return false;
		}
		else 
			return true;
		
	}
	public void setGuestEntered(String qrContent) {
		m_listOfGuests.get(qrContent).setEntryTimeStamp(new DateTime(ReadSpreadsheet.getCurrentDateTime()).toString());
	}
	/*
	 * If scan fails, search person by full name and set 
	 */
	public boolean setGuestEnteredByFullName(String fullName) {
		Iterator<Map.Entry<String, Guest>> it = m_listOfGuests.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + (Guest)pairs.getValue());
	        Guest guest = (Guest)pairs.getValue();
	        if( guest.getFullName().equals(fullName)) {
	        	setGuestEntered(guest.getQRContent());
	        	return true;
	        }
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    return false;
	}
	
}
