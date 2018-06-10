package com.pongp.jobinterview.atm.util.exception;

public class InvalidWithdrawException extends Exception 
{
	private static final long serialVersionUID = -8823130772385795212L;

	public InvalidWithdrawException(String message) 
    {
        super(message);
    }
}
