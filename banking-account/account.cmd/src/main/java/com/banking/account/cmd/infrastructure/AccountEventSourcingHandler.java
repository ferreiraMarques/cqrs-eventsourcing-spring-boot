package com.banking.account.cmd.infrastructure;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.cqrs.core.domain.AggreateRoot;
import com.banking.cqrs.core.handlers.EventSourcingHandler;
import com.banking.cqrs.core.infrastructure.EventStore;

public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

	@Autowired
	private EventStore eventStore;

	@Override
	public void save(AggreateRoot aggreate) {
		eventStore.saveEvent(aggreate.getId(), aggreate.getUncommitedChanges(), aggreate.getVersion());
		aggreate.markChangesAsCommited();
	}

	@Override
	public AccountAggregate getById(String id) {
		var aggregate = new AccountAggregate();
		var events = eventStore.getEvents(id);
	
		if(events != null && !events.isEmpty()) {
			aggregate.replayEvent(events);
			var lastVersions = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
			aggregate.setVersion(lastVersions.get());
		}
		
		return null;
	}

}
