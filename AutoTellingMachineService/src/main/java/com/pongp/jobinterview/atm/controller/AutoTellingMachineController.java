package com.pongp.jobinterview.atm.controller;

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

@RestController
@RequestMapping("/atm")
public class AutoTellingMachineController {

	public static final Logger logger = LoggerFactory.getLogger(AutoTellingMachineController.class);

	ATMService atmService = new ATMService();

	// -------------------Retrieve Available List of Bank Notes---------------------------------------------
	@RequestMapping(value = "/position/", method = RequestMethod.GET)
	public ResponseEntity<List<BankNote>> listAllUsers() {
		List<BankNote> bankNotes = atmService.getAvailableBankNote();
		if (bankNotes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BankNote>>(bankNotes, HttpStatus.OK);
	}

	// -------------------Withdraw Money ------------------------------------------
	@RequestMapping(value = "/withdraw/{amount}", method = RequestMethod.POST)
	public ResponseEntity<?> getUser(@PathVariable("amount") long id) {

		//return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
		
		List<BankNote> bankNotes = atmService.getAvailableBankNote();
		if (bankNotes.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BankNote>>(bankNotes, HttpStatus.OK);
	}


}