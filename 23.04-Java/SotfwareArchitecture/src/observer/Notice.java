package observer;

import java.util.ArrayList;
import java.util.List;

public class Notice {
	private List<Observer> list = new ArrayList<>();

	public void attach(Observer observer) {
		list.add(observer);
	}

	public void detach(Observer observer) {
		list.remove(observer);
	}

	public void notifyObservers(String msg) {
		for (Observer o : list) {
			o.reeive(msg);
		}
	}
}
