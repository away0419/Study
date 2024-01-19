package behavioral.visitor;

public class Main {
    public static void main(String[] args) {
        Visitor person = new Person();
        School school = new School();
        Element compay = new Company();

        school.accept(person);
        System.out.println();
        compay.accept(person);
    }
}
