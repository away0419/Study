package behavioral.visitor;

public class School implements Element{
    @Override
    public void accept(Visitor visitor) {
        System.out.println("학교에 방문자가 왔습니다.");
        visitor.visit(this);
    }
}
