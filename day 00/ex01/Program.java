import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        int num = sc.nextInt();
        sc.close();
        if (num < 2) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }

        int steps = 0;
        for (int divider = 2; (long) divider * divider <= num; divider++) {
            steps++;
            if (num % divider == 0) {
                System.out.println("false " + steps);
                return;
            }
        }
        steps++;
        System.out.println("true " + steps);
    }
}