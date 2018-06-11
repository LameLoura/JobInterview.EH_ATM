package com.pongp.jobinterview.atm.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.pongp.jobinterview.atm.model.BankNote;
import com.pongp.jobinterview.atm.model.BankNoteBatch;
import com.pongp.jobinterview.atm.service.repository.IRepository;
import com.pongp.jobinterview.atm.util.exception.InvalidWithdrawException;
import com.pongp.jobinterview.atm.util.exception.WithdrawErrorMessage;

@RunWith(MockitoJUnitRunner.class)
public class ATMServiceTest {
    
	ATMService fixture;

	@Mock
	IRepository<BankNote> bankNoteRepository;

	@Before
        public void setUp()
        {
    		fixture = new ATMService(bankNoteRepository);
        }
	
	@Test
	public void test_ATMServiceShouldThrowsCorrespondingExceptionWhenInputIsInvalid() 
	{
		expectInvalidInputErrorWithGivenInput( -12, WithdrawErrorMessage.INVALID_INPUT );
		expectInvalidInputErrorWithGivenInput( 0, WithdrawErrorMessage.INVALID_INPUT );
	}
	
	@Test
	public void test_ATMServiceShouldThrowsCorrespondingExceptionWhenWithdrawIsNotPossible() 
	{
		expectInvalidInputErrorWithGivenInput( 15, WithdrawErrorMessage.INSUFFCIENT_NOTE );
	}
	
	@Test
	public void test_ATMServiceShouldReturnCorrespondingNotesForStriaghtForwardCombination() throws InvalidWithdrawException
	{
		verifyStraightForwardWithDraw( 1000 );
		verifyStraightForwardWithDraw( 500 );
		verifyStraightForwardWithDraw( 100 );
		verifyStraightForwardWithDraw( 50 );
		verifyStraightForwardWithDraw( 20 );
	}
	
	@Test
	public void test_ATMServiceShouldReturnAlternativeCombinationWhenLargerNoteUnavailable() throws InvalidWithdrawException
	{
	    // mock unlimited bank
	    ArrayList<BankNote> availableNotes = new ArrayList<BankNote>() ;
	    availableNotes.add(new BankNote(500	, 100));
	    when(bankNoteRepository.FindAll()).thenReturn(availableNotes);
	    
	    BankNoteBatch output = fixture.withdrawMoney(1000);
	    
	    // bank are unlimited. the return note should be exactly the bank that match its value
	    assertEquals(2, output.getNoteData().get(500).getNoteCount());
	    assertEquals(1, output.getNoteData().keySet().size());
	}
	
	@Test
	public void test_verifyRepositoryChanges_caseWithdraw1700Successful() throws InvalidWithdrawException
	{
	    int initialNote = 100;
	    mockBankNoteAvailable( initialNote );
	    fixture.withdrawMoney(1700);
	    
	    //verify that the repository acknowledge the change to database layer
	    verifyUpdatedBankDataOnRepository( 	new BankNote( 1000, initialNote -1 )
						,new BankNote( 500, initialNote -1 )
						,new BankNote( 100, initialNote -2 ) );
	    
	}
	
	@Test
	public void test_verifyRepositoryChanges_caseWithdraw1700Unsuccessful()
	{
	    InvalidWithdrawException expectedException = null;
	    //mock initial note very few to test insufficient bank case
	    int initialNote = 1;
	    mockBankNoteAvailable( initialNote );
    	    try
            {
    		fixture.withdrawMoney(1700);
            } 
    	    catch (InvalidWithdrawException e)
    	    {
		expectedException = e;
    	    }
	    
	    //verify that the repository acknowledge the change to database layer
	    verifyUpdatedBankDataOnRepository( ); //empty array : not expecting a change on data access layer
	    assertNotNull( expectedException );
	    
	}
	
	///////////////////////////////  private stuffs -- utility for test within this class ///////////////////////////
	private void verifyUpdatedBankDataOnRepository( BankNote... expectedBankUpdateOnRepository )
	{
	    //get the list of bank note that the mocked repository has actually acknowledge the changes
	    ArgumentCaptor<BankNote> actualUpdateBankNotesOnREpository = ArgumentCaptor.forClass(BankNote.class);
	    
	    //verify # of times repository acknowledge changes
	    verify( bankNoteRepository, times( expectedBankUpdateOnRepository.length )).update( actualUpdateBankNotesOnREpository.capture() );
	    BankNoteBatch actualUpdatedBankNote = new BankNoteBatch( actualUpdateBankNotesOnREpository.getAllValues() );
	    
	    for( BankNote expectedChanges : expectedBankUpdateOnRepository )
	    {
		BankNote actualChange = actualUpdatedBankNote.getNoteData().get( expectedChanges.getValue() );
		assertEquals( expectedChanges.getNoteCount() , actualChange.getNoteCount() );
	    }

	}
	
        private void verifyStraightForwardWithDraw( int bankNoteValue ) throws InvalidWithdrawException
        {

		mockUnlimitedBank();

        	BankNoteBatch output = fixture.withdrawMoney(bankNoteValue);
        	//bank are unlimited. the return note should be exactly the bank that match its value
        	assertEquals(1, output.getNoteData().get(bankNoteValue).getNoteCount());
        	assertEquals(1, output.getNoteData().keySet().size());
        }
	
	private void expectInvalidInputErrorWithGivenInput( int withdrawAmount, WithdrawErrorMessage expectedError ) 
	{
		InvalidWithdrawException expectedException = null;
		try
		{
			mockUnlimitedBank();
			fixture.withdrawMoney( withdrawAmount );
		}
		catch (InvalidWithdrawException e) 
		{
			expectedException = e;
		}
		
		assertNotNull( expectedException );
		assertTrue( expectedError.getErrorMessage().equals( expectedException.getMessage() ) );
	}

	/**
	 * mock a lot of bank to ensure that insufficient bank WILL NOT be an issue on that test case
	 */
	private void mockUnlimitedBank()
	{
	    mockBankNoteAvailable( 1_000_000 );
	}
	
	/**
	 * 
	 * @param bankPerType # of bank note available for each note type
	 */
	@SuppressWarnings("unchecked")
	private void mockBankNoteAvailable( int bankPerType )
	{
	    reset( bankNoteRepository );
	    
	    ArrayList<BankNote> availableNotes = new ArrayList<BankNote>() ;
	    availableNotes.add(new BankNote(1000	, bankPerType));
	    availableNotes.add(new BankNote(500	, bankPerType));
	    availableNotes.add(new BankNote(100	, bankPerType));
	    availableNotes.add(new BankNote(50		, bankPerType));
	    availableNotes.add(new BankNote(20		, bankPerType));
	    
	    when(bankNoteRepository.FindAll()).thenReturn(availableNotes);
	}
}
