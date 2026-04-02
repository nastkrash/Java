import java.util.UUID;

public class TransactionLinkedList implements TransactionList {
    private Transaction head = null;
    private int count = 0;
    public void addTransaction(Transaction t) {
        t.next = head;
        head = t;
        count++;
    }
    public void removeTransaction(UUID id) {
        Transaction current = head;
        Transaction previous = null;
        while (current != null) {
            if (current.getId().equals(id))
                break;
            previous = current;
            current = current.next;
        }
        if (current == null)
            throw new TransactionNotFoundException("Transaction not found");
        if (current == head)
            head = head.next;
        else
            previous.next = current.next;
        count--;

    }
    public Transaction[] toArray() {
        Transaction current = head;
        Transaction[] array = new Transaction[count];
        for (int i = 0; i < count; i++){
            array[i] = current;
            current = current.next;
        }
        return array;
    }
}