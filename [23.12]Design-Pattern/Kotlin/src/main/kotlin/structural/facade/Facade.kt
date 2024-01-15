package structural.facade

class Facade {
    fun action(){
        val person = Person()
        val tv = Tv()
        val pizza = Pizza()

        person.move()
        pizza.addTopping()
        person.move()
        tv.on()
        person.watch()

    }
}