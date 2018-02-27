interface Listener<T> {
    void onSuccess(T result);

    default void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }
}
