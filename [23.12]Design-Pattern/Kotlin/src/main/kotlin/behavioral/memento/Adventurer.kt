package behavioral.memento

class Adventurer(
    var job: String,
    var level: Int
) {

    fun setData(memento: Memento) {
        this.job = memento.job
        this.level = memento.level
    }

    fun createMemento(): Memento{
        return Memento(job,level)
    }

    override fun toString(): String {
        return "Adventurer(job='$job', level=$level)"
    }


}