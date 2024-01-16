package behavioral.visitor

class Person:Visitor {
    override fun visitor(company: Company) {
        println("사람 : 일을 합니다.")
    }

    override fun visitor(school: School) {
        println("사람 : 공부를 합니다.")
    }
}