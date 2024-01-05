package behavioral.chainOfResponsibility;

public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin();
        User user = new User();

        admin.setNextHandler(user);
        admin.process("Admin");

        System.out.println();
        admin.process("User");

        System.out.println();
        admin.process("Test");

    }
}
