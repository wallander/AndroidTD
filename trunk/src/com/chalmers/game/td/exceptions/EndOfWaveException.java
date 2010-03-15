package com.chalmers.game.td.exceptions;

public class EndOfWaveException extends WaveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3166072342685671221L;
	
	@Override
	public String getMessage() {
		return "End of wave is reached!";
	}
}
