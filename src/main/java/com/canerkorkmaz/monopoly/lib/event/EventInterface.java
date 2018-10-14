package com.canerkorkmaz.monopoly.lib.event;

public interface EventInterface<T> {
    void handle(T data);
}
