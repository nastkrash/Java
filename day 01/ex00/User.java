public class User 
{
    private Integer id;
    private String name;
    private Integer balance;
    
    public User(Integer id, String name, Integer balance){
        if (balance < 0) {
            System.out.println("Balance can not be negative");
            balance = 0;
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
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