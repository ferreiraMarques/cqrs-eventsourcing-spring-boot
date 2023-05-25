package com.banking.account.cmd.domain;

import java.util.Date;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithDrawEvent;
import com.banking.cqrs.core.domain.AggreateRoot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountAggregate extends AggreateRoot {

	private Boolean active;

	private double balance;

	public AccountAggregate(OpenAccountCommand command) {
		raiseEvent(new AccountOpenedEvent(command.getId(), command.getAccountHolder(), command.getAccountType(),
				new Date(), command.getOpeningBalance()));

	}

	public void apply(AccountOpenedEvent event) {
		this.id = event.getId();
		this.active = true;
		this.balance = event.getOpeningBalance();
	}

	public void depositFunds(double amount) {
		if (!this.active) {
			throw new IllegalStateException("not permit deposit into account");
		}

		if (amount <= 0) {
			throw new IllegalStateException("amount is zero");
		}

		raiseEvent(new FundsDepositedEvent(this.id, amount));
	}

	public void apply(FundsDepositedEvent event) {
		this.id = event.getId();
		this.balance += event.getAmount();
		this.active = true;
	}

	public void withDrawFunds(double amount) {
		if (!this.active) {
			throw new IllegalStateException("not permit withdraw of account");
		}

		raiseEvent(new FundsWithDrawEvent(this.id, amount));
	}

	public void apply(FundsWithDrawEvent event) {
		this.id = event.getId();
		this.balance -= event.getAmount();
		this.active = true;
	}

	public void closeAccount() {
		if (!this.active) {
			throw new IllegalStateException("is closed account");
		}

		raiseEvent(new AccountClosedEvent(this.id));
	}

	public void apply(AccountClosedEvent event) {
		this.id = event.getId();
		this.active = false;
	}
}
