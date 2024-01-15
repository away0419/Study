package behavioral.iterator

fun main() {
    val hamburgerCollection = HamburgerCollection(5)
    hamburgerCollection.add(Hamburger(5000, "햄버거"))
    hamburgerCollection.add(Hamburger(10000, "치즈 햄버거"))
    hamburgerCollection.add(Hamburger(10000, "불고기 햄버거"))
    hamburgerCollection.add(Hamburger(15000, "치즈 불고기 햄버거"))
    hamburgerCollection.add(Hamburger(10000, "치킨 햄버거"))

    val iter = hamburgerCollection.iterator()

    while (iter.hasNext()) {
        println(iter.next())
    }

}