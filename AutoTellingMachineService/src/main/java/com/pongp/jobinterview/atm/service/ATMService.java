package com.pongp.jobinterview.atm.service;

import java.util.List;
import java.util.stream.Collectors;

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
	public BankNoteBatch withdrawMoney( int remainingAmount )  throws InvalidWithdrawException
	{
		validateWithdrawAmount( remainingAmount );
		
        	BankNoteBatch notesProvidedToWithdrawer = new BankNoteBatch();
        
        	// loop from the largest available note -> try to fill request with largest note possible first
        	List<BankNote> descendingAvailableNotes = getAvailableBankNote().stream()
        		.sorted((item1, item2) -> item2.getValue() - item1.getValue()).collect(Collectors.toList());
        	for (BankNote note : descendingAvailableNotes)
        	{
        	    // keep it simple: keep subtracting from withdrawal amount as long as that note  is available and can fill-in the remaining amount
        	    // TODO performance improvement on TDD refactor
        	    while (remainingAmount > 0 && note.getNoteCount() > 0 && note.getValue() <= remainingAmount )
        	    {
        		remainingAmount -= note.getValue();
        		note.descreaseNoteCount();
        		notesProvidedToWithdrawer.addNote(note.getValue());
        	    }
        	}
        	
        	if( remainingAmount!= 0 )
        	{
        	    throw new InvalidWithdrawException( WithdrawErrorMessage.INSUFFCIENT_NOTE.getErrorMessage() );
        	}
        	
		return notesProvidedToWithdrawer;
	}
	
	private void validateWithdrawAmount( int amount ) throws InvalidWithdrawException
	{
		if( amount <= 0 )
		{
			throw new InvalidWithdrawException( WithdrawErrorMessage.INVALID_INPUT.getErrorMessage() );
		}
	}

}
