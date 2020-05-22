package Homework3;

public class YellowPages {
    public static void main(String[] args) {
        PhoneBook ph = new PhoneBook();
        ph.add("Abdullaev", "123");
        ph.add("Abdullaev", "555");
        ph.add("Ivanov", "444");
        ph.add("Sidorov", "123");

        ph.get("A");
        ph.get("Abdullaev");
        ph.get("Sidorov");
    }
}
