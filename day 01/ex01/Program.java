public class Program {
    public static void main(String[] args) {
        User user1 = new User("Nastya", 1000);
        User user2 = new User("Igor", 500);

        System.out.println(user1.getId() + " " + user1.getName());
        System.out.println(user2.getId() + " " + user2.getName());

    }
}