package behavioral.chainOfResponsibility

class Admin(): LoginHandler() {
    override fun process(authority: String) {
        if("Admin" == authority){
            println("관리자 로그인 성공")
        }else{
            super.process(authority)
        }
    }
}