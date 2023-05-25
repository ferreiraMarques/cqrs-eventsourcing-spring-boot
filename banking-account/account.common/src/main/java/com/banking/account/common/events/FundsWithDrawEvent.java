package com.banking.account.common.events;

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
public class FundsWithDrawEvent extends BaseEvent {

	private double amount;

	public FundsWithDrawEvent(String id, double amount) {
		super(id);
		this.amount = amount;
	}

}
