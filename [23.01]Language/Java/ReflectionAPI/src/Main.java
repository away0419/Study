public class Main {
    public static void main(String[] args) {
        // 스트링 클래스 인스턴스화
        String str = new String("Class클래스 테스트");

        // getClass() 메서드로 얻기
        Class<? extends String> cls = str.getClass();
        System.out.println(cls); // class java.lang.String

        // 클래스 리터럴(*.class)로 얻기
        Class<? extends String> cls2 = String.class;
        System.out.println(cls2); // class java.lang.String

    }
}