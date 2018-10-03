package com.canerkorkmaz.monopoly.di.impl;

import com.canerkorkmaz.monopoly.di.interfaces.Logger;

import java.time.Instant;

public class DefaultLogger extends Logger {
    @Override
    protected void write(String level, String message) {
        System.out.println(String.format("[%s] [%s] %s", Instant.now(), level, message));
    }
}
