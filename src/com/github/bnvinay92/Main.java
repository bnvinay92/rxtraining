package com.github.bnvinay92;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    // Implement ObserveOn and SubscribeOn so that the api call executes on bgThread,
    // and renderView() executes on the main thread.
    public static void main(String[] args) {
        ExecutorService bgThread = Executors.newSingleThreadExecutor();
        ExecutorService mainThread = Executors.newSingleThreadExecutor();
        EventSource.create(emitter -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e1) {
                emitter.onFailure(e1);
            }
            emitter.onSuccess(new PersonEntity("Vinay", 42));
        })
                .subscribeOn(bgThread)
                .observeOn(mainThread)
                .run(personEntity -> renderView());
    }

    private static void renderView() {
        System.out.println("renderView() called from: " + Thread.currentThread().getName());
    }
}
