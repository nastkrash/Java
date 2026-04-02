import java.util.UUID;

public class TransactionsService {
    private UsersList usersList;
    public TransactionsService() {
        usersList = new UsersArrayList();
    }
    public void addUser(User user) {
        usersList.addUser(user);
    }
    public User getUserByID(Integer id) {
        return usersList.getUserByID(id);
    }
    public Integer getUserBalance(Integer id) {
        return usersList.getUserByID(id).getBalance();
    }
    public void transfer(Integer senderId, Integer recipientId, Integer amount) {
        User sender = usersList.getUserByID(senderId);
        User recipient = usersList.getUserByID(recipientId);
        if (sender.getBalance() < amount || amount <=0)
            throw new IllegalTransactionException("Illegal transaction");
        UUID id = UUID.randomUUID();
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        Transaction t1 = new Transaction(id, recipient, sender, Transaction.Category.DEBIT, -amount);
        Transaction t2 = new Transaction(id, recipient, sender, Transaction.Category.CREDIT, amount);
        sender.getTransactions().addTransaction(t1);
        recipient.getTransactions().addTransaction(t2);

    
    }
    public Transaction[] getTransactions(Integer userId) {
        return usersList.getUserByID(userId).getTransactions().toArray();
    }
    public void removeTransaction(Integer userId, UUID id) {
        usersList.getUserByID(userId).getTransactions().removeTransaction(id);
    }
    public Transaction[] checkValidity() {
        TransactionLinkedList allTransactions = new TransactionLinkedList();
        TransactionLinkedList notValid = new TransactionLinkedList();
        for (int i = 0; i < usersList.getNumOfUsers(); i++){
            Transaction[] transactions = usersList.getUserByIndex(i).getTransactions().toArray();
            for (Transaction t : transactions)
                allTransactions.addTransaction(t);
        }
        Transaction[] all = allTransactions.toArray();
        for (int i = 0; i < all.length; i++) {
            boolean pairFound = false;
            for (int j = 0; j < all.length; j++) {
                if (i == j)
                    continue;
                if (all[i].getId().equals(all[j].getId())) {
                    pairFound = true;
                    break;
                }
            }
            if(!pairFound)
                notValid.addTransaction(all[i]);
        }
        return notValid.toArray();

        }
    }
