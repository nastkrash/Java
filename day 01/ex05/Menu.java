import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionsService TS;
    private Scanner sc;
    public Menu() {
        TS = new TransactionsService();
        sc = new Scanner(System.in);
    }
    public void addUser() {
        System.out.println("Enter a user name and a balance");
        String name = sc.next();
        Integer balance;
        try {
            balance = Integer.parseInt(sc.next());
        } catch( Exception e) {
            System.out.println("Invalid input");
            return;
        }
        if (balance < 0)
        {
            System.out.println("Invalid balance");
            return;
        }
        User u = new User(name, balance);
        TS.addUser(u);
        System.out.println("User with id = " + u.getId() +" is added");
    }
    public void userBalance() {
        System.out.println("Enter a user ID");
        Integer id;
        try {
            id = Integer.parseInt(sc.next());
        } catch( Exception e) {
            System.out.println("Invalid input");
            return;
        }
        try {
            TS.getUserByID(id);
        } catch(UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(TS.getUserByID(id).getName() +  " - " + TS.getUserBalance(id));
    }
    public void transfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        int senderId, recipientId, amount;
        try {
            senderId = Integer.parseInt(sc.next());
            recipientId = Integer.parseInt(sc.next());
            amount = Integer.parseInt(sc.next());
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }
        try {
            TS.getUserByID(senderId);
            TS.getUserByID(recipientId);
        } catch(UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (amount <= 0) {
            System.out.println("Invalid transfer amount");
            return;
        }
        try {
            TS.transfer(senderId, recipientId, amount);
        } catch (IllegalTransactionException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("The transfer is completed");
    }
    public void getUserTransactions() {
        System.out.println("Enter a user ID");
        Integer id;
        try {
            id = Integer.parseInt(sc.next());
        } catch( Exception e) {
            System.out.println("Invalid input");
            return;
        }
        try {
            TS.getUserByID(id);
        } catch(UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        Transaction[] transactions = TS.getTransactions(id);
        if (transactions.length == 0)
            System.out.println("User doesn't have transactions");
        for (Transaction t : transactions) {
            User recipient = t.getRecipient();
            User sender = t.getSender();

            if (t.getCategory() == Transaction.Category.DEBIT)
                System.out.println("To " + recipient.getName() + "(id = " + recipient.getId() + ") -" + t.getAmount() + " with id = " + t.getId());
            else
                System.out.println("From " + sender.getName() + "(id = " + sender.getId() + ") " + t.getAmount() + " with id = " + t.getId());
        }
    }
    public void removeTransfer() {
        System.out.println("Enter a user ID and a transfer ID");
        Integer userId;
        try {
            userId = Integer.parseInt(sc.next());
        } catch( Exception e) {
            System.out.println("Invalid input");
            return;
        }
        try {
            TS.getUserByID(userId);
        } catch(UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        String uuid = sc.next();
        UUID transactionId;
        try {
            transactionId = UUID.fromString(uuid);
        } catch(Exception e) {
            System.out.println("Invalid UUID");
            return;
        }
        Transaction found = null;

        Transaction[] transactions = TS.getTransactions(userId);
        for (Transaction t : transactions) {
            if (t.getId().equals(transactionId))
                found = t;
        }
        if (found == null) {
            System.out.println("Transfer not found");
            return;
        }
        
        TS.removeTransaction(userId, transactionId);
        if (found.getCategory() == Transaction.Category.DEBIT)
            System.out.println("Transfer To " + found.getRecipient().getName() + "(id = " + found.getRecipient().getId() + ") " + found.getAmount() + " removed");
        else
            System.out.println("Transfer From " + found.getSender().getName() + "(id = " + found.getSender().getId() + ") " + found.getAmount() + " removed");
    }

    public void checkValidity() {
        System.out.println("Check results:");
        Transaction[] notValid = TS.checkValidity();
        if (notValid.length == 0) {
            System.out.println("All transfers are valid");
            return;
        }
        for (Transaction t : notValid) {
            if (t.getCategory() == Transaction.Category.DEBIT) {
                System.out.println(t.getSender().getName() + "(id = " + t.getSender().getId() +") has an unacknowledged transfer id = " + t.getId() + " to " + t.getRecipient().getName() + "(id = " + t.getRecipient().getId() + ") for " + t.getAmount());
            }
            else {
                System.out.println(t.getRecipient().getName() + "(id = " + t.getRecipient().getId() +") has an unacknowledged transfer id = " + t.getId() + " from "+ t.getSender().getName() +"(id = " + t.getSender().getId() + ") for " + t.getAmount());
            }
        }
    }

}