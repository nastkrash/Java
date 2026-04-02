public class User 
{
    private final Integer id;//id нельзя изменить
    private String name;
    private Integer balance;
    private TransactionList transactions;
    
    public User(String name, Integer balance){
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance;
        this.transactions = new TransactionLinkedList();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Balance cannot be negative");
        this.balance = balance;
    }

    public TransactionList getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction t) {
        if (t != null)
            this.transactions.addTransaction(t);
    }
}