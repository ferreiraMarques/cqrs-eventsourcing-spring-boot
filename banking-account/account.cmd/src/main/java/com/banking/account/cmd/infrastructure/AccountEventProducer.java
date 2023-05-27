package com.banking.account.cmd.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.producers.EventProducer;

@Service
public class AccountEventProducer implements EventProducer {
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void produce(String topic, BaseEvent event) {
		kafkaTemplate.send(topic, event);
		
	}

}
