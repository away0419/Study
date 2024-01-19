package behavioral.visitor

class School:Element {
    override fun accept(visitor: Visitor) {
        println("회사 : 방문자가 왔습니다.")
        visitor.visitor(this)
    }
}