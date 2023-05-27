package com.banking.account.query.api.queries;

import com.banking.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {

	private String id;
}
