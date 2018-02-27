// Prerequisites : Java 8 features: lambdas, default methods
// Decorator pattern

// Build a promise-like library
// 1. Represent async computations

// 2. Provide a way to easily schedule these representations

// 3. Provide some combinators for these async computations.

import java.util.concurrent.Executor;

public class Main {

    public static final Executor bg = Util.createExecutor("bg");
    public static final Executor ui = Util.createExecutor("ui");

    // Main thread
    public static void main(String[] args) throws InterruptedException {
        SingleEventSource<String> query = new SuccessfulQuery()
                .triggeredOn(bg)
                .listenOn(ui);

        query.trigger(Main::updateUi);
        Thread.sleep(1000);
    }

    private static void updateUi(String s) {
        System.out.println(s + " listened on: " + Thread.currentThread().getName());
    }

    interface SingleEventSource<T> {

        void trigger(Listener<T> listener);

        default SingleEventSource<T> triggeredOn(Executor executor) {
            return new TriggerOn<>(this, executor);
        }

        default SingleEventSource<T> listenOn(Executor executor) {
            return new ListenOn<>(this, executor);
        }
    }

    static class TriggerOn<T> implements SingleEventSource<T> {

        private final SingleEventSource<T> decorated;

        private final Executor executor;

        TriggerOn(SingleEventSource<T> decorated, Executor executor) {
            this.decorated = decorated;
            this.executor = executor;
        }

        @Override
        public void trigger(Listener<T> listener) {
            executor.execute(() -> decorated.trigger(listener));
        }

    }

    static class SuccessfulQuery implements SingleEventSource<String> {

        @Override
        public void trigger(Listener<String> listener) {
            System.out.println("Query executing on: " + Thread.currentThread().getName());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                listener.onFailure(e);
                return;
            }
            listener.onSuccess("Success!");
        }

    }

    static class ListenOn<T> implements SingleEventSource<T> {

        private final SingleEventSource<T> decoratedEventSource;

        private final Executor executor;

        ListenOn(SingleEventSource<T> decoratedEventSource, Executor executor) {
            this.decoratedEventSource = decoratedEventSource;
            this.executor = executor;
        }

        @Override
        public void trigger(Listener<T> listener) {
            decoratedEventSource.trigger(new ListenerDecorator(listener));
        }

        class ListenerDecorator implements Listener<T> {

            private final Listener<T> decoratedListener;

            ListenerDecorator(Listener<T> decoratedListener) {
                this.decoratedListener = decoratedListener;
            }

            @Override
            public void onSuccess(T result) {
                executor.execute(() -> decoratedListener.onSuccess(result));
            }

            @Override
            public void onFailure(Throwable throwable) {
                executor.execute(() -> decoratedListener.onFailure(throwable));
            }

        }
    }
}
