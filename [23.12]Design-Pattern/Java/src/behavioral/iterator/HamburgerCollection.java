package behavioral.iterator;

public class HamburgerCollection implements Collection{
    Hamburger[] arr;
    private int index;

    public HamburgerCollection(int size) {
        this.arr = new Hamburger[size];
    }

    public void add(Hamburger hamburger){
        if(index<arr.length){
            arr[index++] = hamburger;
        }
    }

    @Override
    public Iterator iterator() {
        return new HamburgerIterator(this.arr);
    }

}
