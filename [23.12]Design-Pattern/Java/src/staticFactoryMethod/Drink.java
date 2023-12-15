package staticFactoryMethod;

public class Drink {
    private Drink(){}

    public static Drink from(String msg){
        System.out.println("make Drink" + msg);
        return new Drink();
    }

    public static Drink of(String... msg){
        System.out.println("make Drink");
        for (String str :
                msg) {
            System.out.println(str);
        }
        return new Drink();
    }

    public static Drink getInstance(){
        return new Drink();
    }

    public static Drink newInstance(){
        return new Drink();
    }

    public static String getString(){
        return "Drink";
    }

    public static String newString(){
        return "Drink";
    }

}
