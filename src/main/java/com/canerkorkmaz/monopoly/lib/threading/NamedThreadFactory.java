package com.canerkorkmaz.monopoly.lib.threading;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {
    private String name;
    private int count = 0;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + "-" + (count++));
    }
}
