package com.chalmers.game.td.initializers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Loader {
	
	// Instance variables
	protected BufferedReader	mReader;
	
	public Loader(String pPathToFile) {		
		
		try { // Try to read the file init.waves which contains the different MobTypes the wave should contain
			mReader = new BufferedReader(new FileReader(pPathToFile));
		} catch(FileNotFoundException fnfe) {
			System.err.println("File not found! \n Message: " + fnfe.getMessage() + " \n Cause: " + fnfe.getCause());
		}
	}
}
