package com.banking.account.cmd.api.command.api.controllers;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.command.api.dto.OpenAccountResponse;
import com.banking.account.cmd.common.dto.BaseResponse;
import com.banking.cqrs.core.infrastructure.CommandDispacther;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {

	private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

	@Autowired
	private CommandDispacther dispacther;

	@PostMapping
	public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
		var id = UUID.randomUUID().toString();
		command.setId(id);

		try {
			dispacther.send(command);
			return new ResponseEntity<>(new OpenAccountResponse("Open account", id), HttpStatus.CREATED);
		} catch (IllegalStateException e) {
			logger.log(Level.WARNING, MessageFormat.format("Error create account {0}", e.getMessage().toString()));
			return new ResponseEntity<>(new BaseResponse(e.getMessage().toString()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			var safeErrorMessages = MessageFormat.format("Error generic {0}", id);
			logger.log(Level.SEVERE, MessageFormat.format("Error create account {0}", safeErrorMessages));
			return new ResponseEntity<>(new BaseResponse(safeErrorMessages), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
