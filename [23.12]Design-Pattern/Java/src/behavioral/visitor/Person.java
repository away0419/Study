package behavioral.visitor;

public class Person implements Visitor {
    @Override
    public void visit(School school) {
        System.out.println("학교에 도착하니 게임이 하고 싶어집니다.");
    }

    @Override
    public void visit(Company company) {
        System.out.println("회사에 도착하니 퇴사가 하고 싶어집니다.");
    }
}
