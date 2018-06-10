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

		List<BankNote> availableNote = getAvailableBankNote();

        	BankNoteBatch notesProvidedToWithdrawer = new BankNoteBatch();
        	BankNoteBatch availableAtmNotes = new BankNoteBatch( availableNote );
        	
        	// loop from the largest available note -> try to fill request with largest note possible first
        	List<BankNote> descendingAvailableNotes = availableNote.stream()
        		.sorted((item1, item2) -> item2.getValue() - item1.getValue()).collect(Collectors.toList());
        	for (BankNote note : descendingAvailableNotes)
        	{
        	    // keep it simple: keep subtracting from withdrawal amount as long as that note  is available and can fill-in the remaining amount
        	    // TODO performance improvement on TDD refactor
        	    while (remainingAmount > 0 && note.getNoteCount() > 0 && note.getValue() <= remainingAmount )
        	    {
        		remainingAmount -= note.getValue();
        		availableAtmNotes.removeNote(note.getValue());
        		notesProvidedToWithdrawer.addNote(note.getValue());
        	    }
        	}
        	
        	if( remainingAmount!= 0 )
        	{
        	    throw new InvalidWithdrawException( WithdrawErrorMessage.INSUFFCIENT_NOTE.getErrorMessage() );
        	}
        	else // the withdraw was successful
        	{
        	    //reflect changes to the repository
        	    for( BankNote changedNote : notesProvidedToWithdrawer.getNoteData().values() )
        	    {
        		BankNote updatedNoteValue = availableAtmNotes.getNoteData().get( changedNote.getValue() );
    		    	_bankNoteRepositary.update( updatedNoteValue );
        	    }
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
