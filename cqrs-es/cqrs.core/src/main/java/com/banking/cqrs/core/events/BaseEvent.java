package com.banking.cqrs.core.events;

import com.banking.cqrs.core.messages.Message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEvent extends Message {
	private int version;

	public BaseEvent(String id) {
		super(id);
	}

}
