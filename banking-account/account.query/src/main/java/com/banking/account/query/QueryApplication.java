package com.banking.account.query;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.banking.account.query.api.queries.FindAccountByHolderQuery;
import com.banking.account.query.api.queries.FindAccountByIdQuery;
import com.banking.account.query.api.queries.FindAccountWithBalanceQuery;
import com.banking.account.query.api.queries.FindAllAccountsQuery;
import com.banking.account.query.api.queries.QueryHandler;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;

@SpringBootApplication
public class QueryApplication {
	
	@Autowired
	private QueryDispatcher dispatcher;
	
	@Autowired 
	private QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}
	
	@PostConstruct
	public void registerHandlers() {
		dispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handler);
		dispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handler);
		dispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handler);
		dispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler::handler);
	}

}
