package com.example.qrscanner;

import java.util.ArrayList;
import java.util.List;

public class ArraySolution {
	private static List<String> myList = new ArrayList<String>();

	public static void insertIntoList(String scannedString) {
		myList.add(scannedString);
	}
	
	public static boolean isPersonAuthorizedToEnter(String scannedString) {
		
		if( myList.contains(scannedString)) {
			return false;
		}
		else {
			insertIntoList(scannedString);
			return true;
		}
	}
}
