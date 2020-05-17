package Homework1;

public class Wall implements Obstacle {

    private int height;

    public Wall(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean trial(Compete cmp) {
        return cmp.jump(getHeight());
    }
}
