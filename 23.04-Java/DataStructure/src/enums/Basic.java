package enums;

public class Basic {
	public static void main(String[] args) {
		for (Fruits fruit : Fruits.values()) {
			System.out.println(fruit);
			System.out.println(fruit.getKrName());
		}
		System.out.println(Fruits.valueOf("APPLE"));
		System.out.println();
		System.out.println(Fruits.APPLE.getKrName());
		
		System.out.println();
		switch (Fruits.findByKrName("사과")) {
		case APPLE: {
			System.out.println("사과 찾았다");
			break;
		}
		default:
		}
		
		System.out.println();
		switch (Fruits.findByFruit("APPLE")) {
		case APPLE: {
			System.out.println("APPLE 찾았다");
			break;
		}
		default:
		}
	}

	public enum Fruits {
		APPLE("F1", "사과"), GRAPE("F2", "포도"), ORANGE("F3", "오렌지");

		private String number;
		private String krName;

		Fruits(String number, String krName) {
			this.number = number;
			this.krName = krName;
		}

		public String getNumber() {
			return number;
		}

		public String getKrName() {
			return krName;
		}

		public static Fruits findByKrName(String krName) {
			for (Fruits fruit : Fruits.values()) {
				if (fruit.getKrName().equals(krName)) {
					return fruit;
				}
			}

			throw new RuntimeException();
		}
		
		public static Fruits findByFruit(String fruits) {
			for (Fruits fruit : Fruits.values()) {
				if (fruit.name().equals(fruits)) {
					return fruit;
				}
			}
			
			throw new RuntimeException();
		}

	}
}
