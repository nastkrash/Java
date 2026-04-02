import java.util.UUID;

public class Transaction{
    private UUID id;
    private User recipient;
    private User sender;
    private Category category;
    private Integer amount;
    public Transaction next;

    public enum Category {
        DEBIT,
        CREDIT
    }

    public Transaction(User recipient, User sender, Category category, Integer amount) {
        if (category == Category.DEBIT && amount >= 0)
            System.out.println("Invalid amount");
        else if (category == Category.CREDIT && amount <= 0)
            System.out.println("Invalid amount");
        else {
            this.id = UUID.randomUUID();
            this.recipient = recipient;
            this.sender = sender;
            this.category = category;
            this.amount = amount;
            recipient.setBalance(recipient.getBalance() + amount);
            sender.setBalance(sender.getBalance() - amount);
        }
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getAmount() {
        return amount;
    }

}