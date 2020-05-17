package Homework1;

public class Competition {
    public static void main(String[] args) {
        Compete[] cmp  = new Compete[3];
        cmp[0] = new Cat(100,3);
        cmp[1] = new Robot(1000,5);
        cmp[2] = new Human(30,1);

        Obstacle[] obstacles = new Obstacle[2];
        obstacles[0] = new Wall(2);
        obstacles[1] = new Track(500);

        for (Compete c: cmp) {
            for (Obstacle o : obstacles) {
                if (!o.trial(c)) break;
            }
        }
    }
}
