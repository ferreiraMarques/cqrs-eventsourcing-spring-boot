package com.banking.account.query.api.queries;

import com.banking.account.query.api.queries.dto.EqualityType;
import com.banking.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

	private double balance;
	
	private EqualityType equalityType;
	
}
