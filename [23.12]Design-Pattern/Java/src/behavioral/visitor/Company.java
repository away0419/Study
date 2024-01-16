package behavioral.visitor;

public class Company implements Element{
    @Override
    public void accept(Visitor visitor) {
        System.out.println("회사에 방문자가 왔습니다.");
        visitor.visit(this);
    }
}
