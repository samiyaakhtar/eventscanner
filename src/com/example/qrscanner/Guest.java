package com.example.qrscanner;

import com.google.api.client.util.DateTime;
import com.google.gdata.data.spreadsheet.CustomElementCollection;

public class Guest {
	private DateTime m_timeStamp; //Maybe we don't need this
	private String m_qrContent;
	private String m_fullName;
	private String m_emailAddress;
	private String m_gender; //Maybe we don't need this
	private String m_school; //Maybe we don't need this
	private String m_emailStatus; //Maybe we don't need this
	private String m_entryTimeStamp;
	
	public Guest(String qrcontent, String fullname, String emailaddress, String entrytimestamp) {
		m_qrContent = qrcontent;
		m_fullName = fullname;
		m_emailAddress = emailaddress;
		m_entryTimeStamp = entrytimestamp;
		
	}
	public Guest(CustomElementCollection element) {
		m_qrContent = element.getValue("qrcontent");
		m_entryTimeStamp = element.getValue("entrytimestamp");
        m_fullName = element.getValue("fullname");
        m_emailAddress = element.getValue("emailaddress");
	}
	public String getQRContent() {
		return m_qrContent;
	}
	public String getFullName() {
		return m_fullName;
	}
	public String getEmailAddress() {
		return m_emailAddress;
	}
	public String getEntryTimeStamp() {
		return m_entryTimeStamp;
	}
	public void setEntryTimeStamp(String time) {
		m_entryTimeStamp = time;
	}
	
	@Override
	public String toString() {
		return "QRContent: " + m_qrContent +
				"Full Name: " + m_fullName +
				"Email Address: " + m_emailAddress +
				"Entry TimeStamp: " + m_entryTimeStamp;
				
	}
}
