package behavioral.memento;

public class Adventurer {
    String job;
    int level;

    public Adventurer(String job, int level) {
        this.job = job;
        this.level = level;
    }

    public Memento createMemento(){
        return new Memento(job, level);
    }

    public void setInfo(Memento memento){
        this.job = memento.job;
        this.level = memento.level;
    }

    @Override
    public String toString() {
        return "Adventurer{" +
                "job='" + job + '\'' +
                ", level=" + level +
                '}';
    }
}
