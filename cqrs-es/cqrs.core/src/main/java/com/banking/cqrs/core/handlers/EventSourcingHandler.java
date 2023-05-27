package com.banking.cqrs.core.handlers;

import com.banking.cqrs.core.domain.AggreateRoot;

public interface EventSourcingHandler<T> {
	
	void save(AggreateRoot aggreate);
	
	T getById(String id);
}
