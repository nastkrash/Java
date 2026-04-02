
import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User u1 = new User("Nastya", 1000);
        User u2 = new User("Igor", 1000);
        TransactionsService TS = new TransactionsService();
        TS.addUser(u1);
        TS.addUser(u2);
        TS.transfer(u1.getId(), u2.getId(), 500);
        TS.transfer(u1.getId(), u2.getId(), 500);
        try {
            TS.transfer(u1.getId(), u2.getId(), 500);
        } catch (IllegalTransactionException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Balance u1: " + TS.getUserBalance(u1.getId()));
        System.out.println("Balance u2: " + TS.getUserBalance(u2.getId()));

        System.out.println("\nTransactions of u1:");
        Transaction[] t1 = TS.getTransactions(u1.getId());
        for (Transaction t : t1) {
            System.out.println(t.getId() + " " + t.getCategory() + " " + t.getAmount() + " sender:" + t.getSender().getName() + " recipient:" + t.getRecipient().getName());
        }

        System.out.println("\nTransactions of u2:");
        Transaction[] t2 = TS.getTransactions(u2.getId());
        for (Transaction t : t2) {
            System.out.println(t.getId() + " " + t.getCategory() + " " + t.getAmount() + " sender:" + t.getSender().getName() + " recipient:" + t.getRecipient().getName());
        }

        if (t2.length > 0) {
            UUID removeId = t2[0].getId();
            TS.removeTransaction(u2.getId(), removeId);
            System.out.println("\nRemoved transaction " + removeId + " from u2");
        }

        System.out.println("\nInvalid (unpaired) transactions after removal:");
        Transaction[] invalid = TS.checkValidity();
        for (Transaction t : invalid) {
            System.out.println(t.getId() + " " + t.getCategory() + " " + t.getAmount() + " user:" + (t.getSender() != null ? t.getSender().getName() : "?") );
        }

    }
}