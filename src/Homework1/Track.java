package Homework1;

public class Track implements Obstacle {

    private int length;

    public Track(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean trial(Compete cmp) {
        return cmp.run(getLength());
    }
}
