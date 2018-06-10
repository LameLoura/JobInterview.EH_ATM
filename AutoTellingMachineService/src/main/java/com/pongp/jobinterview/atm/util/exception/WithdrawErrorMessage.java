package com.pongp.jobinterview.atm.util.exception;

public enum WithdrawErrorMessage 
{
	INVALID_INPUT("Invalid withdraw amount"),
	INSUFFCIENT_NOTE("Insufficient note for the withdraw");
	
	private String errorMessage;
	
	WithdrawErrorMessage( String errorMessage ) 
	{
        this.errorMessage = errorMessage;
    }
	
	public String getErrorMessage()
	{
		return this.errorMessage;
	}
}
