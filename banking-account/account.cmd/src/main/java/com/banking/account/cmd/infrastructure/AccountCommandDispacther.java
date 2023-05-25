package com.banking.account.cmd.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.banking.cqrs.core.commands.BaseCommand;
import com.banking.cqrs.core.commands.CommandHandlerMethod;
import com.banking.cqrs.core.infrastructure.CommandDispacther;

@Service
public class AccountCommandDispacther implements CommandDispacther {

	private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

	@Override
	public <T extends BaseCommand> void registerHandle(Class<T> type, CommandHandlerMethod<T> handler) {
		var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
		handlers.add(handler);
	}

	@Override
	public void send(BaseCommand command) {
		var handlers = routes.get(command.getClass());

		if (handlers == null || handlers.size() == 0) {
			throw new RuntimeException("the command handler not register");
		}

		if (handlers.size() > 1) {
			throw new RuntimeException("not send command with must handler");
		}
		
		handlers.get(0).handle(command);

	}

}
