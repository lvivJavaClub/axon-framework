package com.lohika.jclub.axon.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@AllArgsConstructor
@Data
public class ParkPayCommand {

    @TargetAggregateIdentifier
    private final String vrn;
    private final int price;

}
