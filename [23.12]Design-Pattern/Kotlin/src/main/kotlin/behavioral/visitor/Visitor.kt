package behavioral.visitor

interface Visitor {
    fun visitor(company: Company)
    fun visitor(school: School)

}