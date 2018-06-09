package com.pongp.jobinterview.atm.service;

import java.util.List;
import java.math.*;

import org.apache.el.lang.ELArithmetic.BigDecimalDelegate;

import java.util.ArrayList;
import com.pongp.jobinterview.atm.model.BankNote;
import com.pongp.jobinterview.atm.service.repository.IRepository;
import com.pongp.jobinterview.atm.util.exception.InvalidWithdrawException;

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
	
	public void withdrawMoney( int amount )  throws InvalidWithdrawException
	{
		//will be implemented
	}

}
