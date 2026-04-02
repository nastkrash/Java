import java.util.UUID;
public interface TransactionList {
    public void addTransaction(Transaction t);
    public void removeTransaction(UUID id);
    public Transaction[] toArray();

}