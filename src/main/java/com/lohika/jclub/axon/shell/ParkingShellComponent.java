package com.lohika.jclub.axon.shell;

import com.lohika.jclub.axon.aggregates.ParkAggregate;
import com.lohika.jclub.axon.commands.ParkPayCommand;
import com.lohika.jclub.axon.commands.RegisterCarCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ShellComponent
@Slf4j
public class ParkingShellComponent {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventSourcingRepository<ParkAggregate> eventSourcingRepository;

    @Autowired
    private CommandGateway commandGateway;

    @ShellMethod("register car")
    public Object registerCar(String vrn, int balance) throws ExecutionException, InterruptedException {
        CompletableFuture<Object> send = commandGateway.send(new RegisterCarCommand(vrn, balance));
        Object o = send.get();
        log.info("Sent event result {}", o);
        return o;
    }

    @ShellMethod("events")
    public List<? extends DomainEventMessage<?>> events(String vrn) throws ExecutionException, InterruptedException {
        DomainEventStream domainEventStream = eventStore.readEvents(vrn);
        List<? extends DomainEventMessage<?>> events = domainEventStream.asStream().collect(Collectors.toList());
        log.info("events {}", events);
        return events;
    }

    @ShellMethod("state")
    public Aggregate state(String vrn) throws ExecutionException, InterruptedException {
        Aggregate load = eventSourcingRepository.load(vrn);
        log.info("Vrn: {}", load.identifier());
        return load;
    }

    @ShellMethod("park pay")
    public void pay(String vrn, int price) throws ExecutionException, InterruptedException {
        CompletableFuture<Object> send = commandGateway.send(new ParkPayCommand(vrn, price));
        log.info("Vrn: {}", send);
    }

}
