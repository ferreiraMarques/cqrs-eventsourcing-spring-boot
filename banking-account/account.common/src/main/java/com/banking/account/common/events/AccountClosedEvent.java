package com.banking.account.common.events;

import com.banking.cqrs.core.events.BaseEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AccountClosedEvent extends BaseEvent {

	public AccountClosedEvent(String id) {
		super(id);
	}
}
