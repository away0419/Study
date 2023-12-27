package creational.staticFactoryMethod

class Drink {
    companion object{
        fun from():Drink{
            return Drink()
        }

        fun of(): Drink{
            return Drink()
        }

        fun valueOf():Drink{
            return Drink()
        }

        fun getInstance(): Drink{
            return Drink()
        }

        fun newInstance(): Drink{
            return Drink()
        }

        fun getString():String{
            return "Drink"
        }

        fun newString():String{
            return "Drink"
        }




    }
}