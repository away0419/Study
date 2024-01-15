package structural.decorator

fun main() {
    var hamburger: Hamburger = BasicHamburger()
    println(hamburger.getName())
    
    println()
    println("치즈 추가")
    hamburger = CheeseDecorator(hamburger)
    println(hamburger.getName())
    
    println()
    println("불고기 추가")
    hamburger = BulgogiDecorator(hamburger)
    println(hamburger.getName())
}
