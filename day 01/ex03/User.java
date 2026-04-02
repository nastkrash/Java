public class User 
{
    private final Integer id;//id нельзя изменить
    private String name;
    private Integer balance;
    private TransactionList transactions;
    
    public User(String name, Integer balance){
        if (balance < 0) {
            System.out.println("Balance can not be negative");
            balance = 0;
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
            System.out.println("Balance can not be negative");
        else
            this.balance = balance;
    }

}