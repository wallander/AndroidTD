package com.chalmers.game.td.exceptions;

public class EndOfTrackException extends WaveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4423494209672592914L;
	
	@Override
	public String getMessage() {
		return "The end of this level is reached!";
	}

}
