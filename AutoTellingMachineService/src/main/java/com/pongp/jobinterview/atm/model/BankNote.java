package com.pongp.jobinterview.atm.model;

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
}
