package function;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public class SupplierTest {

	public static void main(String[] args) {
		String stringValue = "helloWorld";
        boolean booleanValue = true;
        double doubleValue = 13245.123;
        int intValue = 123;
        long longValue = 1234567;

        Supplier<String> supplier = () -> stringValue;
        BooleanSupplier booleanSup =  () -> booleanValue;
        DoubleSupplier doubleSup = () -> doubleValue;
        LongSupplier longSup = () -> longValue;
        IntSupplier intSup = () -> intValue;

        SupplierTest test = new SupplierTest();

        String s = supplier.get();
        System.out.println("String 값 : "+ s);

        boolean b = booleanSup.getAsBoolean();
        System.out.println("boolean 값 : "+ b);

        double d = doubleSup.getAsDouble();
        System.out.println("double 값 : "+ d);

        long l = longSup.getAsLong();
        System.out.println("long 값 : "+ l);

        int i = intSup.getAsInt();
        System.out.println("int 값 : "+ i);
	}

}
