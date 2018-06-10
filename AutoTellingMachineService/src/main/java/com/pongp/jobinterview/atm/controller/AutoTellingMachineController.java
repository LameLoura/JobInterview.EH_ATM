package com.pongp.jobinterview.atm.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pongp.jobinterview.atm.model.BankNote;
import com.pongp.jobinterview.atm.service.ATMService;
import com.pongp.jobinterview.atm.service.repository.BankNotesRepository;
import com.pongp.jobinterview.atm.util.exception.InvalidWithdrawException;

@RestController
@RequestMapping("/atm")
public class AutoTellingMachineController
{

    public static final Logger logger = LoggerFactory.getLogger(AutoTellingMachineController.class);

    ATMService atmService = new ATMService(new BankNotesRepository());

    // -------------------Retrieve Available List of Bank
    // Notes---------------------------------------------
    @RequestMapping(value = "/position/", method = RequestMethod.GET)
    public ResponseEntity<List<BankNote>> getAvailableBankNote()
    {
	List<BankNote> bankNotes = atmService.getAvailableBankNote();
	return new ResponseEntity<List<BankNote>>(bankNotes, HttpStatus.OK);
    }

    // -------------------Withdraw Money ------------------------------------------
    @RequestMapping(value = "/withdraw/{amount}", method = RequestMethod.POST)
    public ResponseEntity<?> withDraw(@PathVariable("amount") int amount) throws InvalidWithdrawException
    {
	ArrayList<BankNote> withdrawnBanks = new ArrayList<BankNote>( atmService.withdrawMoney(amount).getNoteData().values() );
	return new ResponseEntity<List<BankNote>>(withdrawnBanks, HttpStatus.OK);
    }

}