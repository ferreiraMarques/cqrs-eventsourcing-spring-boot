package com.banking.cqrs.core.messages;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Message {

	private String id;

	public Message(String id) {
		this.id = id;
	}

}
