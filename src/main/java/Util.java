import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Util {
    static ExecutorService createExecutor(String name) {
        return Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, name);
            thread.setDaemon(true);
            return thread;
        });
    }
}
