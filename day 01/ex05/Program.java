import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();
        boolean isDev = args.length > 0 && args[0].equals("--profile=dev");
        
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (isDev){
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. DEV - remove a transfer by ID");
                System.out.println("6. DEV - check transfer validity");
                System.out.println("7. Finish execution");
            }
            else {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. Finish execution");
            }
            System.out.print("-> ");
            int menuNumber;
            try {
                menuNumber = Integer.parseInt(sc.next());
            } catch(Exception e) {
                System.out.println("Invalid input");
                continue;
            }

            switch (menuNumber) {
                case 1:
                    menu.addUser();
                    break;
                case 2:
                    menu.userBalance();
                    break;
                case 3:
                    menu.transfer();
                    break;
                case 4:
                    menu.getUserTransactions();
                    break;
                case 5:
                    if (isDev)
                        menu.removeTransfer();
                    else
                        return;
                    break;
                case 6:
                    if (isDev)
                        menu.checkValidity();
                    else 
                        System.out.println("Invalid input");
                    break;
                case 7:
                    if (isDev)
                        return;
                    else
                        System.out.println("Invalid input");
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }
}