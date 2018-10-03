package com.canerkorkmaz.monopoly.di.impl;

import com.canerkorkmaz.monopoly.di.interfaces.Logger;

import java.time.Instant;

public class ClassNameLogger extends Logger {
    private String name;

    public ClassNameLogger(Class<?> clazz) {
        this.name = clazz.getSimpleName();
    }

    @Override
    protected void write(String level, String message) {
        System.out.println(String.format("[%s] [%s] [%s] %s", Instant.now(), level, name, message));
    }
}
