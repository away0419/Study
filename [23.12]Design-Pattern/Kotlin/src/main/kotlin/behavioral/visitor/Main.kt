package behavioral.visitor

fun main() {
    val company = Company();
    val school = School();
    val visitor = Person();

    company.accept(visitor);
    println()
    school.accept(visitor);
}