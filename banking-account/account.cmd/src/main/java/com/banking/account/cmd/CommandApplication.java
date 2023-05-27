package com.banking.account.cmd;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.banking.account.cmd.api.command.CloseAccountCommand;
import com.banking.account.cmd.api.command.CommandHandler;
import com.banking.account.cmd.api.command.DepositFoundCommand;
import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.cmd.api.command.WithdrawFoundCommand;
import com.banking.cqrs.core.infrastructure.CommandDispacther;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispacther commandDispacther;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispacther.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		commandDispacther.registerHandler(DepositFoundCommand.class, commandHandler::handle);
		commandDispacther.registerHandler(WithdrawFoundCommand.class, commandHandler::handle);
		commandDispacther.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}

}
