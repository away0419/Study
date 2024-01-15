package behavioral.chainOfResponsibility;

public class Admin extends LoginHandler {

    @Override
    public void process(String authority) {
        if ("Admin".equals(authority)) {
            System.out.println("관리자 로그인 완료");
        } else {
            super.process(authority);
        }
    }
}
