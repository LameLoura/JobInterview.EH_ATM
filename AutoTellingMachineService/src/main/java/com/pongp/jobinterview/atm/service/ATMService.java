package com.pongp.jobinterview.atm.service;

import java.util.ArrayList;
import com.pongp.jobinterview.atm.model.BankNote;

public class ATMService 
{
	public ArrayList<BankNote> getAvailableBankNote()
	{
		//TEMPORARILY mock simple to make sure web service is up and working
		return  new ArrayList<BankNote>() {{
		    add( new BankNote(50, 15));
		    add( new BankNote(100, 15));
		    add( new BankNote(500, 15));
		    add( new BankNote(1000, 15));
		}};
	}
	
	public void withdrawMoney()
	{
		//will be implemented
	}

}
