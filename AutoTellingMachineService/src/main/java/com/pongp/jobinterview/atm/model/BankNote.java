package com.pongp.jobinterview.atm.model;

/**
 * Represent a collection of bank note of the same value
 * @author PongP
 *
 */
public class BankNote 
{
	private final int _noteValue;
	private int _noteCount;
	
	public BankNote( int value, int noteCount )
	{
		_noteValue = value;
		_noteCount = noteCount;
	}
	
	public int getValue()
	{
		return _noteValue;
	}

	public int getNoteCount()
	{
		return _noteCount;
	}

	public void increaseNoteCount()
	{
		_noteCount++;
	}
	
	public void descreaseNoteCount()
	{
		_noteCount--;
	}
	
	//for debugging within eclipse
	@Override
	public String toString()
	{
	    String beautifulValue = String.format("%1$-" + 5 + "s",  this._noteValue );
	    return String.format( "\tBankNote : %s \t( count = %d)", beautifulValue, this._noteCount );
	}
}
