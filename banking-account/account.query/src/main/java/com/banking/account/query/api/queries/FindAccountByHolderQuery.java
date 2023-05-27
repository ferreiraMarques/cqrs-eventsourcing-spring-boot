package com.banking.account.query.api.queries;

import com.banking.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {
	
	private String accountHolder;
}
