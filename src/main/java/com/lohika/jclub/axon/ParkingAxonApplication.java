package com.lohika.jclub.axon;

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
}
