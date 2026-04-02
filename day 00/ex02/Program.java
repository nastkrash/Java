import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("IllegalArgument");
            System.exit(-1);
        }
        int num = sc.nextInt();
        int coffee_queries = 0;
        while (num != 42)
        {
            if (num < 2)
            {
                System.out.println("IllegalArgument");
                System.exit(-1);
            }
            int digit_sum = digit_sum_counter(num);
            if (is_prime(digit_sum))
                coffee_queries++;
            num = sc.nextInt();
        }
        System.out.println("Count of coffee - request - " + coffee_queries);
        
    }
    public static int digit_sum_counter(int num){
        int sum = 0;
        while(num!=0)
        {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    
    }
    public static boolean is_prime(int num){
        if (num % 2 == 0)
        {
            if (num == 2)
                return true;
            else
                return false;
        }
        for (int divider = 3; (long)divider * divider <= num; divider+=2) {
            if (num % divider == 0)
                return false;
        }
        return true;
    }
}