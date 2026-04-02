import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        int num = sc.nextInt();
        int coffee_queries = 0;
        while (num != 42) {
            if (num < 2) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int digit_sum = digit_sum_counter(num);
            if (is_prime(digit_sum))
                coffee_queries++;
            num = sc.nextInt();
        }
        System.out.println("Count of coffee-request - " + coffee_queries);
        sc.close();

    }

    public static int digit_sum_counter(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;

    }

    public static boolean is_prime(int num) {
        for (int divider = 2; (long) divider * divider <= num; divider++) {
            if (num % divider == 0)
                return false;
        }
        return true;
    }
}