package com.github.bnvinay92;

abstract class EventSource<Event> {

    abstract void run(Listener<Event> listener);

    interface Listener<Event> {

        void onSuccess(Event event);

        default void onFailure(Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
