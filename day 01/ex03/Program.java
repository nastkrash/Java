import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User u1 = new User("Nastya", 1000);
        User u2 = new User("Igor", 1000);
        Transaction t1 = new Transaction(u2, u1, Transaction.Category.CREDIT, 200);
        Transaction t2 = new Transaction(u2, u1, Transaction.Category.CREDIT, 300);
        TransactionLinkedList list = new TransactionLinkedList();
        list.addTransaction(t1);
        list.addTransaction(t2);
        System.out.println("Transactions list: ");
        Transaction[] array = list.toArray();
        for (Transaction t : array) {
            System.out.println("ID :" + t.getId());
            System.out.println("Recipient :" + t.getRecipient().getName());
            System.out.println("Sender :" + t.getSender().getName());
            System.out.println("Category :" + t.getCategory());
            System.out.println("Amount :" + t.getAmount());
        }

        list.removeTransaction(t1.getId());
        System.out.println("Transactions list after removing transaction: ");
        array = list.toArray();
        for (Transaction t : array) {
            System.out.println("ID :" + t.getId());
            System.out.println("Recipient :" + t.getRecipient().getName());
            System.out.println("Sender :" + t.getSender().getName());
            System.out.println("Category :" + t.getCategory());
            System.out.println("Amount :" + t.getAmount());
        }

        System.out.println("Trying to remove not existing transaction: ");
        try {
            list.removeTransaction(t1.getId());
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }
        



    }
}