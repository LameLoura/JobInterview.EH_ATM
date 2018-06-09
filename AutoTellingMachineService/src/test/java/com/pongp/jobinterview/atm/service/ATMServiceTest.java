package com.pongp.jobinterview.atm.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.pongp.jobinterview.atm.model.BankNote;
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
		expectInvalidInputErrorWithGivenInput( -12 );
		expectInvalidInputErrorWithGivenInput( 0 );
	}
	
	private void expectInvalidInputErrorWithGivenInput( int withdrawAmount ) 
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
		assertTrue(WithdrawErrorMessage.INVALID_INPUT.getErrorMessage().equals( expectedException.getMessage() ) );
	} 

	/**
	 * mock a lot of bank to ensure that insufficient bank is not an issue on that test case
	 */
	private void mockUnlimitedBank()
	{
		
		// mock unlimited bank
		when(bankNoteRepository.FindAll()).thenReturn(new ArrayList<BankNote>() 
		{
			{
				add(new BankNote(1000	, 100));
				add(new BankNote(500	, 100));
				add(new BankNote(100	, 100));
				add(new BankNote(50		, 100));
				add(new BankNote(20		, 100));
			}
		});
	}
}
