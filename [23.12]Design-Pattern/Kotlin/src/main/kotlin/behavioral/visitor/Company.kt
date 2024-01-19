package behavioral.visitor

class Company: Element{
    override fun accept(visitor: Visitor) {
        println("회사 : 사람이 방문했습니다.")
        visitor.visitor(this)
    }
}