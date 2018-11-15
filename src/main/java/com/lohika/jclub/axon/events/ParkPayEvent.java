package com.lohika.jclub.axon.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkPayEvent {

    private final String vrn;
    private final int price;

}
