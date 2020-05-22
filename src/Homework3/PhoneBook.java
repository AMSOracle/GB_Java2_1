package Homework3;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class PhoneBook {
    private HashMap<String, LinkedHashSet<String>> book;

    public PhoneBook() {
        book = new HashMap<>();
    }

    public void add(String surname, String phone) {
        if (!book.containsKey(surname)) {
            book.put(surname, new LinkedHashSet<>());
        }
        book.get(surname).add(phone);
    }

    public void get(String surname) {
        if (book.containsKey(surname))
            System.out.println(surname + " " + book.get(surname));
        else
            System.out.println("No record found for " + surname);
    }
}
