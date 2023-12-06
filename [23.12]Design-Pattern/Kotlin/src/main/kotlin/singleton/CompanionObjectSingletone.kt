package singleton

class CompanionObjectSingletone private constructor() {

//    Lazy Initialization
//    companion object {
//        private var instance: CompanionObjectSingletone? = null;
//
//        fun getInstance(): CompanionObjectSingletone {
//            return instance ?: CompanionObjectSingletone().also {
//                    instance = it
//            }
//        }
//    }

//    Eager Initialization
//    companion object {
//        private var instance: CompanionObjectSingletone =  CompanionObjectSingletone();
//
//        fun getInstance(): CompanionObjectSingletone {
//            return instance
//        }
//    }

//    double checked locking
//    companion object {
//        @Volatile private var instance: CompanionObjectSingletone? = null;
//
//        fun getInstance(): CompanionObjectSingletone {
//            return instance ?: synchronized(this) {
//                instance ?: CompanionObjectSingletone().also {
//                    instance = it
//                }
//            }
//        }
//    }

//    Lazy Holder
//    inner 키워드를 사용하지 않으면 static 내부 클래스(Inner 클래스) 로 되고
//    inner 키워드를 사용해야 non-static 내부 클래스(Nested 클래스) 가 된다.
//    private class LazyHolderInner{
//        companion object{
//            val companionObjectSingletone : CompanionObjectSingletone = CompanionObjectSingletone()
//        }
//    }
//
//    companion object {
//        fun getInstance(): CompanionObjectSingletone {
//            return LazyHolderInner.companionObjectSingletone
//        }
//    }

//    kotlin singleton 완벽한 방법.
//    lazy 이용하여 스레드 안전 보장.
//    생성자도 만들 수 있어 파라미터도 받을 수 있음.
    companion object {
        private val instance: CompanionObjectSingletone by lazy { CompanionObjectSingletone() }

        fun getInstance(): CompanionObjectSingletone {
            return instance
        }
    }

}