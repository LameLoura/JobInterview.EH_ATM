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
}
