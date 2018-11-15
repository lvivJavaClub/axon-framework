package com.lohika.jclub.axon;

import com.lohika.jclub.axon.aggregates.ParkAggregate;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class ParkingAxonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingAxonApplication.class, args);
    }

    @Bean
    public PromptProvider promptProvider() {
        return () -> new AttributedString("parking-lot:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

    @Bean
    public CommandBus commandBus() {
        return new SimpleCommandBus();
    }

    @Bean
    public CommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }

    @Bean
    public EventStore eventStore() {
        InMemoryEventStorageEngine storageEngine = new InMemoryEventStorageEngine();
        return new EmbeddedEventStore(storageEngine);
    }

    @Bean
    public EventSourcingRepository<ParkAggregate> repository() {
        return new EventSourcingRepository<>(ParkAggregate.class, eventStore());
    }

    @Bean
    public AggregateAnnotationCommandHandler<ParkAggregate> commandHandler() {
        AggregateAnnotationCommandHandler<ParkAggregate> commandHandler
                = new AggregateAnnotationCommandHandler<>(ParkAggregate.class, repository());
        commandHandler.subscribe(commandBus());
        return commandHandler;
    }

}
