package behavioral.chainOfResponsibility

class User(): LoginHandler() {
    override fun process(authority: String) {
        if("User" == authority){
            println("User 로그인 성공")
        }else{
            super.process(authority)
        }
    }
}