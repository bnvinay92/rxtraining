package com.github.bnvinay92;

import java.util.concurrent.ExecutorService;

public class SubscribeOn<T> extends EventSource<T> {

    private final EventSource<T> original;
    private final ExecutorService executor;

    SubscribeOn(EventSource<T> original, ExecutorService executor) {
        this.original = original;
        this.executor = executor;
    }

    @Override
    public void run(Listener<T> listener) {
        //TODO
    }
}
