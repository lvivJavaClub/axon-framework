package com.lohika.jclub.axon.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ParkingShellComponent {

    @ShellMethod("add two ints")
    public int add(int a, int b) {
        return a + b;
    }

}
