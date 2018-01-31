package com.github.bnvinay92;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

abstract class EventSource<Event> {

    abstract void run(Listener<Event> listener);

    public EventSource<Event> subscribeOn(ExecutorService executor) {
        return new SubscribeOn<>(this, executor);
    }

    public EventSource<Event> observeOn(ExecutorService executor) {
        return new ObserveOn<>(this, executor);
    }

    public <R> EventSource<R> map(Function<Event, R> mapper) {
        // TODO
        return null;
    }

    public <R> EventSource<R> flatMap(Function<Event, EventSource<R>> flatMapper) {
        // TODO
        return null;
    }

    interface Listener<Event> {

        void onSuccess(Event event);

        default void onFailure(Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
