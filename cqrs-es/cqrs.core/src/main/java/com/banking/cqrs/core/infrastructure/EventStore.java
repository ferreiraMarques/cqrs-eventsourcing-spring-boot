package com.banking.cqrs.core.infrastructure;

import java.util.List;

import com.banking.cqrs.core.events.BaseEvent;

public interface EventStore {

	void saveEvent(String aggregateId, Iterable<BaseEvent> events, int expectVersion);
	
	List<BaseEvent> getEvents(String aggregateId);
}
