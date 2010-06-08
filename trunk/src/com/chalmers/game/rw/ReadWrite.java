package com.chalmers.game.rw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Environment;
import android.util.Log;

public class ReadWrite {

	private BufferedReader	mReader;
	private BufferedWriter	mWriter;
	
	public ReadWrite() {
		
		mReader = null;
		mWriter = null;
	}
	
	public BufferedReader getReader(String pFileName) throws FileNotFoundException {
		
		File file = new File(Environment.getExternalStorageState() + "/pFileName");
		mReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		return mReader;
	}
	
	public BufferedWriter getWriter(String pFileName) {
		
		File root = Environment.getExternalStorageDirectory();
		
		if(root.canWrite()) {
			
			Log.v("RW.getWriter", "Can write to file " + pFileName);
			
			File file = new File(root, pFileName);
			
			try {
				
				mWriter = new BufferedWriter(new FileWriter(file));
				
			}catch(IOException ioe) {
				
				ioe.printStackTrace();
				Log.v("RW.getWriter", ioe.getMessage());
			}
			
		} else {
			
			Log.v("RW.getWriter", "Cannot write to file " + pFileName);
		}
		
		return mWriter;
	}
}
