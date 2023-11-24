package singleton;

public class DCL {
	private volatile static DCL instance;

	private DCL() {
	};

	public static DCL getInstance() {
		if (instance == null) {
			synchronized (DCL.class) {
				if (instance == null) {
					instance = new DCL();
				}
			}
		}
		return instance;
	}
}
