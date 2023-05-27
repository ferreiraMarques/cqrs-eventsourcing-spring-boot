package com.banking.account.query.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.banking.cqrs.core.domain.BaseEntity;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;
import com.banking.cqrs.core.queries.BaseQuery;
import com.banking.cqrs.core.queries.QueryHandlerMethod;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

	private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

	@Override
	public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> method) {
		var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
		handlers.add(method);
	}

	@Override
	public <T extends BaseEntity> List<BaseEntity> send(BaseQuery query) {
		var handlers = routes.get(query.getClass());

		if (handlers == null || handlers.size() <= 0) {
			throw new RuntimeException("the query handler not register");
		}

		if (handlers.size() > 1) {
			throw new RuntimeException("not send query with must handler");
		}

		return handlers.get(0).handler(query);
	}

}
