package observer;

public class Main {
	public static void main(String[] args) {
		Notice notice = new Notice();
		User1 user1 = new User1("유저1");
		User2 user2 = new User2("유저2");
		
		notice.attach(user1);
		notice.attach(user2);
		
		String msg ="공지!!!";
		notice.notifyObservers(msg);
		
		notice.detach(user1);
		msg = "안녕하세요~";
		notice.notifyObservers(msg);
	}
}
