package com.banking.account.cmd.api.command;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.commands.BaseCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OpenAccountCommand extends BaseCommand {
	private String accountHolder;
	private AccountType accountType;
	private double openingBalance;

	public OpenAccountCommand(String id, String accountHolder, AccountType accountType, double openingBalance) {
		super(id);
		this.accountHolder = accountHolder;
		this.accountType = accountType;
		this.openingBalance = openingBalance;
	}

}
