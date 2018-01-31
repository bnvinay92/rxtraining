package com.github.bnvinay92;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

abstract class EventSource<Event> {

    static <T> EventSource<T> create(Consumer<Listener<T>> onListen) {
        // TODO
        return null;
    }

    abstract void run(Listener<Event> listener);

    public EventSource<Event> subscribeOn(ExecutorService executor) {
        return new SubscribeOn<>(this, executor);
    }

    public EventSource<Event> observeOn(ExecutorService executor) {
        return new ObserveOn<>(this, executor);
    }

    public <R> EventSource<R> map(Function<Event, R> mapper) {
        return null;
    }

    public <R> EventSource<R> flatMap(Function<Event, EventSource<R>> flatMapper) {
        return null;
    }

    interface Listener<Event> {

        void onSuccess(Event event);

        default void onFailure(Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
