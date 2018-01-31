package com.github.bnvinay92;

public class ApiCall extends EventSource<PersonEntity> {

    @Override
    public void run(Listener<PersonEntity> listener) {
        try {
            System.out.println("Api call made on:" + Thread.currentThread().getName());
            Thread.sleep(200);
        } catch (InterruptedException e) {
            listener.onFailure(e);
            return;
        }
        listener.onSuccess(new PersonEntity("Vinay", 42));
    }
}
