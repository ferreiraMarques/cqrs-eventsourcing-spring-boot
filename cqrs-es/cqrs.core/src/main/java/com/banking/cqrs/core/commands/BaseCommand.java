package com.banking.cqrs.core.commands;

import com.banking.cqrs.core.messages.Message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseCommand extends Message {
	
	public BaseCommand(String id) {
		super(id);
	}

}
