package com.chalmers.game.td.exceptions;

public class EndOfGameException extends WaveException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4320300309032285782L;
	
	@Override
	public String getMessage() {
		return "Congratulations! You are now a proud acheiver of teh most 3p1c Tower Defense game available on Android phones!";
	}

}
