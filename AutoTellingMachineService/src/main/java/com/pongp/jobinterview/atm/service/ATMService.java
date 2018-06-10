package com.pongp.jobinterview.atm.service;

import java.util.List;
import java.math.*;

import org.apache.el.lang.ELArithmetic.BigDecimalDelegate;

import java.util.ArrayList;
import com.pongp.jobinterview.atm.model.BankNote;
import com.pongp.jobinterview.atm.model.BankNoteBatch;
import com.pongp.jobinterview.atm.service.repository.IRepository;
import com.pongp.jobinterview.atm.util.exception.InvalidWithdrawException;
import com.pongp.jobinterview.atm.util.exception.WithdrawErrorMessage;

public class ATMService 
{
	
	private IRepository<BankNote> _bankNoteRepositary;
	
	public ATMService( IRepository<BankNote> bankNoteRepository ) 
	{
		_bankNoteRepositary = bankNoteRepository;
	}
	   
	public List<BankNote> getAvailableBankNote( )
	{
		//TEMPORARILY mock simple to make sure web service is up and working
		return _bankNoteRepositary.FindAll();
	}
	
	/**
	 * 
	 * @param amount
	 * @return {@link BankNoteBatch} represent how many of each note withdrawer get
	 * @throws InvalidWithdrawException
	 */
	public BankNoteBatch withdrawMoney( int amount )  throws InvalidWithdrawException
	{
		validateWithdrawAmount( amount );
		
		//TODO implement withdraw logic
		return new BankNoteBatch();
	}
	
	private void validateWithdrawAmount( int amount ) throws InvalidWithdrawException
	{
		if( amount <= 0 )
		{
			throw new InvalidWithdrawException( WithdrawErrorMessage.INVALID_INPUT.getErrorMessage() );
		}
	}

}
