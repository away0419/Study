package behavioral.chainOfResponsibility

abstract class LoginHandler(): Handler {
    lateinit var handler: Handler
    override fun setNextHandler(handler: Handler) {
        this.handler=handler
    }

    override fun process(authority: String) {
        try{
            this.handler.process(authority)
        }catch (exception: Exception){
            println("로그인 실패")
        }
    }
}