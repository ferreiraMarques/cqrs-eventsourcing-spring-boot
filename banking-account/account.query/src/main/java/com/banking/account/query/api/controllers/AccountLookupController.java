package com.banking.account.query.api.controllers;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.banking.account.query.api.queries.FindAccountByIdQuery;
import com.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.banking.account.query.api.queries.FindAllAccountsQuery;
import com.banking.account.query.api.queries.dto.AccountLookupResponse;
import com.banking.account.query.api.queries.dto.EqualityType;
import com.banking.cqrs.core.domain.BaseEntity;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;

@RestController
@RequestMapping(params = "/api/vs/accountLookup")
public class AccountLookupController {

	private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

	@Autowired
	private QueryDispatcher dispatcher;

	@GetMapping("/")
	public ResponseEntity<AccountLookupResponse> getAll() {
		try {
			List<BaseEntity> accounts = dispatcher.send(new FindAllAccountsQuery());

			if (accounts == null || accounts.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}

			var response = new AccountLookupResponse(MessageFormat.format("query completed", null), accounts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			var safeErrorMessages = MessageFormat.format("Error generic", null);
			logger.log(Level.SEVERE, MessageFormat.format("Error {0}", safeErrorMessages));
			return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessages, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/withBalance/{equalityType}/{balance}")
	public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("equalityType") EqualityType type,
			@PathVariable("balance") double balance) {
		try {
			List<BaseEntity> accounts = dispatcher.send(new FindAccountWithBalanceQuery(balance, type));

			if (accounts == null || accounts.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}

			var response = new AccountLookupResponse(MessageFormat.format("query completed", null), accounts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			var safeErrorMessages = MessageFormat.format("Error generic", null);
			logger.log(Level.SEVERE, MessageFormat.format("Error {0}", safeErrorMessages));
			return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessages, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
