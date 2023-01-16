package ramdas;

import java.util.function.BiFunction;

public class Basic {
	public static void main(String[] args) {
		BiFunction<Integer, String, Student> fn = Student::new;
		Student st = fn.apply(3, "홍길동");
		System.out.println(st);

		EmployeeMake em = Employee::new;
		Employee e = em.employeeMake(1, 0, "홍사원");
		System.out.println(e);
	}
	
	public static class Student {
		int no;
		String name;
		
		public Student(int no, String name) {
			super();
			this.no = no;
			this.name = name;
		}
		
		public String toString() {
			return no+" : "+name;
		}
		
	}
	
	public static class Employee {
		int no;
		int num;
		String name;
		
		
		
		public Employee(int no, int num, String name) {
			super();
			this.no = no;
			this.num = num;
			this.name = name;
		}



		@Override
		public String toString() {
			return "Employee [no=" + no + ", num=" + num + ", name=" + name + "]";
		}
	}
	
	@FunctionalInterface
	public static interface EmployeeMake{
		public Employee employeeMake(int no, int num, String name);
	}
}
