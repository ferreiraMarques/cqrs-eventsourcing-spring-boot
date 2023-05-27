package com.banking.account.query.api.queries.dto;

import java.util.List;

import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountLookupResponse  extends BaseResponse {
	
	private List<BaseEntity> accounts;
	
	public AccountLookupResponse(String message, List<BaseEntity> accounts) {
		super(message);
		this.accounts = accounts;
	}
}
