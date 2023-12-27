package creational.singletone;

public class LazyInitialization {

    private static LazyInitialization instance;

    private LazyInitialization() {
    }

    // 동기화 문제 해결을 위한 synchronized
    public static synchronized LazyInitialization getInstance() {
        if (instance == null) {
            instance = new LazyInitialization();
        }

        return instance;
    }
}