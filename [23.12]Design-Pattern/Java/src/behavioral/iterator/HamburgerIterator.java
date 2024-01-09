package behavioral.iterator;

public class HamburgerIterator implements Iterator{
    Hamburger[] arr;
    private int index = 0;

    public HamburgerIterator(Hamburger[] arr) {
        this.arr = arr;
    }

    @Override
    public boolean hasNext() {
        return index < arr.length;
    }

    @Override
    public Hamburger next() {
        return arr[index++];
    }
}
