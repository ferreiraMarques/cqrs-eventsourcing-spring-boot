package com.banking.account.cmd.api.command.api.controllers;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.cmd.api.command.DepositFoundCommand;
import com.banking.account.cmd.common.dto.BaseResponse;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.infrastructure.CommandDispacther;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundController {

	private final Logger logger = Logger.getLogger(DepositFundController.class.getName());

	@Autowired
	private CommandDispacther dispacther;

	@PutMapping(path = "/{id}")
	public ResponseEntity<BaseResponse> depositFunds(@PathVariable("id") String id,
			@RequestBody DepositFoundCommand command) {
		try {
			command.setId(id);
			dispacther.send(command);
			return new ResponseEntity<>(new BaseResponse("Correct deposit into account"), HttpStatus.OK);
		} catch (IllegalStateException | AggregateNotFoundException e) {
			logger.log(Level.WARNING,
					MessageFormat.format("Error deposit into account {0}", e.getMessage().toString()));
			return new ResponseEntity<>(new BaseResponse(e.getMessage().toString()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			var safeErrorMessages = MessageFormat.format("Error generic {0}", id);
			logger.log(Level.SEVERE, MessageFormat.format("Error {0}", safeErrorMessages));
			return new ResponseEntity<>(new BaseResponse(safeErrorMessages), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
