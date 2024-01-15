package behavioral.chainOfResponsibility;

public abstract class LoginHandler implements Handler{

    Handler handler;

    @Override
    public void setNextHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void process(String authority) {
        try{
            this.handler.process(authority);
        }catch (Exception e){
            System.out.println("로그인 실패");
        }
    }
}
