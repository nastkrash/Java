public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "Nastya", 1000000);
        User user2 = new User(2, "Igor", 1000000);

        Transaction t1 = new  Transaction(
            user1,
            user2,
            Transaction.Category.CREDIT,
            200
        );

        System.out.println("User 1:");
        System.out.println("ID: " + user1.getId());
        System.out.println("Name: " + user1.getName());
        System.out.println("Balance: " + user1.getBalance());

        System.out.println();

        System.out.println("User 2:");
        System.out.println("ID: " + user2.getId());
        System.out.println("Name: " + user2.getName());
        System.out.println("Balance: " + user2.getBalance());

    }
}