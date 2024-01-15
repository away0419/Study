package behavioral.chainOfResponsibility;

public class User extends LoginHandler{
    @Override
    public void process(String authority) {
        if("User".equals(authority)){
            System.out.println("사용자 로그인 완료");
        }else {
            super.process(authority);
        }
    }
}
