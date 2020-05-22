package Homework3;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayListExample {
    public static void main(String[] args) {
        ArrayList<String> c = new ArrayList();
        c.add("First");
        c.add("Second");
        c.add("Third");
        c.add("Fourth");
        c.add("First");
        c.add("Second");
        c.add("Third");
        c.add("Fourth");
        c.add("Fifth");
        c.add("Sixth");
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : c) {
            if (map.containsKey(s)) map.put(s, map.get(s) + 1);
            else map.put(s, 1);
        }
        System.out.println(map);
    }

}
