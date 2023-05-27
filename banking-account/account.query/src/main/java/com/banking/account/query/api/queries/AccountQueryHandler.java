package com.banking.account.query.api.queries;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.query.api.queries.dto.EqualityType;
import com.banking.account.query.domain.AccountRepository;
import com.banking.account.query.domain.BankAccount;
import com.banking.cqrs.core.domain.BaseEntity;

@Service
public class AccountQueryHandler implements QueryHandler {
	
	@Autowired
	private AccountRepository repository;

	@Override
	public List<BaseEntity> handler(FindAllAccountsQuery query) {
		Iterable<BankAccount> bankAccounts = repository.findAll();
		List<BaseEntity> bankAccountsList = new ArrayList<>();
		
		bankAccounts.forEach(bankAccountsList::add);
		
		return bankAccountsList;
	}

	@Override
	public List<BaseEntity> handler(FindAccountByIdQuery query) {
		var bankAccount = repository.findById(query.getId());
		
		if(bankAccount.isEmpty()) {
			return null;
		}
		
		List<BaseEntity> bankAccountsList = new ArrayList<>();
		bankAccountsList.add(bankAccount.get());
		
		return bankAccountsList;
	}

	@Override
	public List<BaseEntity> handler(FindAccountByHolderQuery query) {
		var bankAccount = repository.findAccountHolder(query.getAccountHolder());
		
		if(bankAccount.isEmpty()) {
			return null;			
		}
		
		List<BaseEntity> bankAccountsList = new ArrayList<>();
		bankAccountsList.add(bankAccount.get());
		
		return bankAccountsList;
	}

	@Override
	public List<BaseEntity> handler(FindAccountWithBalanceQuery query) {
		List<BaseEntity> bankAccountsList = query.getEqualityType() == EqualityType.GREATER_THAN 
				? repository.findByBalanceGreaterThan(query.getBalance()) 
				: repository.findByBalanceLessThan(query.getBalance());
		
		return bankAccountsList;
	}

}
