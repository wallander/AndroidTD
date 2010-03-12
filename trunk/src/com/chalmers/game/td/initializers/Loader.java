package com.chalmers.game.td.initializers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Loader {

	protected BufferedReader	mReader;
	
	public Loader(String pFileName) {
		
		try {
			mReader = new BufferedReader(new FileReader(pFileName));
		} catch(FileNotFoundException fnfe) {
			System.err.println("FILE NOT FOUND!"); // TODO Give more specific cause...
		}
	}
	
}