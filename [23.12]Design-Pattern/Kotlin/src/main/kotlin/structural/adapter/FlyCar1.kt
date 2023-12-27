package structural.adapter

class FlyCar1(private val car: Car): Wing {
    override fun fly() {
            println("날기")
    }

    fun start(){
        car.start()
    }

    fun end(){
        car.end()
    }
}