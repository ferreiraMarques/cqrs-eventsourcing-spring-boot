package com.banking.account.query.domain;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class BankAccount extends BaseEntity {

	@Id
	private String id;
	
	private String accountHolder;
	
	private Date createdDate;
	
	private AccountType accountType;
	
	private double balance;
	
}
