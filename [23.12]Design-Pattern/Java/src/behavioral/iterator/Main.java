package behavioral.iterator;

public class Main {
    public static void main(String[] args) {
        HamburgerCollection hamburgerCollection = new HamburgerCollection(5);
        hamburgerCollection.add(new Hamburger("기본 햄버거", 500));
        hamburgerCollection.add(new Hamburger("치즈 햄버거", 1000));
        hamburgerCollection.add(new Hamburger("불고기 햄버거", 1000));
        hamburgerCollection.add(new Hamburger("석쇠 불고기 햄버거", 1500));
        hamburgerCollection.add(new Hamburger("치즈 불고기 햄버거", 1500));

        Iterator iter = hamburgerCollection.iterator();

        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

    }
}
