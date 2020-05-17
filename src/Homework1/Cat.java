package Homework1;

public class Cat implements Compete {
    private int xLimit, yLimit;

    public int getxLimit() {
        return xLimit;
    }

    public int getyLimit() {
        return yLimit;
    }

    public Cat(int xLimit, int yLimit) {
        this.xLimit = xLimit;
        this.yLimit = yLimit;
    }

    @Override
    public boolean jump(int height) {
        if ((height < 0) || (height > getyLimit())) {
            System.out.println("Cat failed to jump");
            return false;
        }
        System.out.println("Cat jumped " + height + "meters");
        return true;
    }

    @Override
    public boolean run(int distance) {
        if ((distance < 0) || (distance > getxLimit())) {
            System.out.println("Cat failed to run");
            return false;
        }
        System.out.println("Cat run " + distance + "meters");
        return true;
    }
}
