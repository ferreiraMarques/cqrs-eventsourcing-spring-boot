package com.banking.account.query.infrastructure.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithDrawEvent;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;

@Service
public class AccountEventHandler implements EventHandler {

	@Autowired
	private AccountRepository repository;

	@Override
	public void on(AccountOpenedEvent event) {
		var bankAccount = new BankAccount(event.getId(), event.getAccountHolder(), event.getCreateDate(),
				event.getAccountType(), event.getOpeningBalance());
		
		repository.save(bankAccount);
	}

	@Override
	public void on(FundsDepositedEvent event) {
		var bankAccount = repository.findById(event.getId());
		
		if(bankAccount.isEmpty()) {
			return;
		}
		
		var currentBalance = bankAccount.get().getBalance();
		var lastestBalance = currentBalance + event.getAmount();
		
		bankAccount.get().setBalance(lastestBalance);
		
		repository.save(bankAccount.get());

	}

	@Override
	public void on(FundsWithDrawEvent event) {
		var bankAccount = repository.findById(event.getId());

		if(bankAccount.isEmpty()) {
			return;
		}
		
		var currentBalance = bankAccount.get().getBalance();
		var lastestBalance = currentBalance - event.getAmount();
		
		bankAccount.get().setBalance(lastestBalance);
		
		repository.save(bankAccount.get());
	}

	@Override
	public void on(AccountClosedEvent event) {
		repository.deleteById(event.getId());
	}

}
