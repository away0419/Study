package structural.flyweight

class Model private constructor(
    val type:String
) {
    companion object Factory{
        private val cache = mutableMapOf<String,Model>()
        fun getInstance(type: String): Model{

            if(cache.containsKey(type)){
                print("[기존 나무] ")
            }else{
                print("[새로운 나무] ")
                cache[type] = Model(type)
            }

            return cache[type]!!
        }
    }
}