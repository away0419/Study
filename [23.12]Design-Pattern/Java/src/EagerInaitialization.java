public class EagerInaitialization {
    private static EagerInaitialization instance = new EagerInaitialization();

    private EagerInaitialization() {
    }

    public static EagerInaitialization getInstance() {
        return instance;
    }
}