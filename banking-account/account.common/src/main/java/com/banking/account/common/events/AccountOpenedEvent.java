package com.banking.account.common.events;

import java.util.Date;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AccountOpenedEvent extends BaseEvent {
	private String accountHolder;
	private AccountType accountType;
	private Date createDate;
	private double openingBalance;
	
	public AccountOpenedEvent(String id, String accountHolder, AccountType accountType, Date createDate, double openingBalance) {
		super(id);
		this.accountHolder = accountHolder;
		this.accountType = accountType;
		this.createDate = createDate;
		this.openingBalance = openingBalance;
	}
	
	
}
