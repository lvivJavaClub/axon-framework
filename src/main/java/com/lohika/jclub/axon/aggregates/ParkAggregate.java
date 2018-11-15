package com.lohika.jclub.axon.aggregates;

import com.lohika.jclub.axon.commands.ParkPayCommand;
import com.lohika.jclub.axon.commands.RegisterCarCommand;
import com.lohika.jclub.axon.events.ParkPayEvent;
import com.lohika.jclub.axon.events.RegisterCarEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventhandling.EventHandler;

@NoArgsConstructor
public class ParkAggregate {

    @AggregateIdentifier
    private String vrn;

    private int balance;

    @CommandHandler
    public ParkAggregate(RegisterCarCommand command) {
        AggregateLifecycle.apply(new RegisterCarEvent(command.getVrn(), command.getBalance()));
    }

    @CommandHandler
    public void parkPay(ParkPayCommand payCommand) {
        AggregateLifecycle.apply(new ParkPayEvent(payCommand.getVrn(), payCommand.getPrice()));
    }

    @EventHandler
    public void onPay(ParkPayEvent payEvent) {
        if (this.balance >= payEvent.getPrice()) {
            this.balance -= payEvent.getPrice();
        } else {
            throw new RuntimeException("Not enough money!");
        }
    }

    @EventHandler
    public void on(RegisterCarEvent event) {
        vrn = event.getVrn();
        balance = event.getBalance();
    }

}
