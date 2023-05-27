package com.banking.account.cmd.infrastructure;

import java.util.Date;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.events.EventModel;
import com.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.banking.cqrs.core.exceptions.ConcurrencyException;
import com.banking.cqrs.core.infrastructure.EventStore;

@Service
public class AccountEventStore implements EventStore {
	
	@Autowired
	private EventStoreRepository repository;
	
	@Autowired
	private AccountEventProducer producer;

	@Override
	public void saveEvent(String aggregateId, Iterable<BaseEvent> events, int expectVersion) {
		var eventStream = repository.findAggregateIndentifier(aggregateId);
		
		if(expectVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectVersion) {
			throw new ConcurrencyException();
		}
		
		var version = expectVersion;
		for(var event: events) {
			version++;
			
			event.setVersion(version);
			
			var eventModel = EventModel.builder()
					.timeStamp(new Date())
					.aggregateIndentifier(aggregateId)
					.aggregateType(AccountAggregate.class.getTypeName())
					.version(version)
					.eventType(event.getClass().getTypeName())
					.eventData(event)
					.build();
			
			var persistedEvent = repository.save(eventModel);
			
			if(!persistedEvent.getId() .isEmpty()) {
				producer.produce(event.getClass().getSimpleName(), event);
			}
		}
	}

	@Override
	public List<BaseEvent> getEvents(String aggregateId) {
		var eventStream = repository.findAggregateIndentifier(aggregateId);
		
		if(eventStream == null || eventStream.isEmpty()) {
			throw new AggregateNotFoundException("Account incorrect");
		}
		
		return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
	}

}
