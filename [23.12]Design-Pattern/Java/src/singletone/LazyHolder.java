package singletone;

public class LazyHolder {

    private static class LazyHolderInner {
        private final static LazyHolder INSTANCE = new LazyHolder();
    }

    public static LazyHolder getInstance() {
        return LazyHolderInner.INSTANCE;
    }
}