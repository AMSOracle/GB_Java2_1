package Homework1;

public class Robot implements Compete {
    private int xLimit, yLimit;

    public int getxLimit() {
        return xLimit;
    }

    public int getyLimit() {
        return yLimit;
    }

    public Robot(int xLimit, int yLimit) {
        this.xLimit = xLimit;
        this.yLimit = yLimit;
    }

    @Override
    public boolean jump(int height) {
        if ((height < 0) || (height > getyLimit())) {
            System.out.println("Robot failed to jump");
            return false;
        }
        System.out.println("Robot jumped " + height + "meters");
        return true;
    }

    @Override
    public boolean run(int distance) {
        if ((distance < 0) || (distance > getxLimit())) {
            System.out.println("Robot failed to run");
            return false;
        }
        System.out.println("Robot run " + distance + "meters");
        return true;
    }
}

