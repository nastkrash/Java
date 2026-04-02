import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("IllegalArgument");
            System.exit(-1);
        }
        int num = sc.nextInt();
        if (num < 2)
        {
            System.out.println("IllegalArgument");
            System.exit(-1);
        }
        int steps = 1;
        if (num % 2 == 0)
        {
            if (num == 2)
                System.out.println("true " + steps);
            else
                System.out.println("false " + steps);
            System.exit(0);
        }
        for (int divider = 3; (long)divider * divider <= num; divider+=2) {
            steps++;           
            if (num % divider == 0)
            {
                System.out.println("false " + steps);
                return;
            }
        }
        System.out.println("true " + steps);
    }
}