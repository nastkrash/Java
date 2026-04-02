import java.util.Scanner;

public class Program{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        long storage = 0;
        int weekNumber = 0;

        while(weekNumber != 18){
            String weekLabel = sc.next();
            if (weekLabel.equals("42"))
                break;
            if (!weekLabel.equals("Week"))
                illegalExit();

            if (!sc.hasNextInt())
                illegalExit();
            int nextWeekNumber = sc.nextInt();
            if (nextWeekNumber != weekNumber + 1)
                illegalExit();
            weekNumber++;

            sc.nextLine();// \n

            storage = storage * 10  + minGrade(sc);
            sc.nextLine();
        }
        printGraph(reverseNum(storage), weekNumber);
    }

    public static void illegalExit(){
        System.err.println("IllegalArgument");
        System.exit(-1);
    }

    public static int minGrade(Scanner sc){
        int min = 9;
        int count = 0;

        while (count < 5)
        {
            if (!sc.hasNextInt())
                illegalExit();
            if (sc.hasNext("42") && count < 5)
                illegalExit();

            int grade = sc.nextInt();
            count++;

            if (grade < 1 || grade >9)
                illegalExit();
            if (grade < min)
                min = grade;
        }
        if (sc.hasNextInt() && !sc.hasNext("42"))
            illegalExit();

        return min;
    }
    public static long reverseNum(long num){
        long result = 0;
        while (num != 0){
            result = result * 10 + (num % 10);
            num /= 10;
        }
        return result;
    }
    public static void printGraph(long storage, int weeks){
        for (int i = 1; i <= weeks; i++)
        {
            long grade = storage % 10;
            storage /= 10;

            System.out.print("Week "+ i + " ");
            System.out.print("=".repeat((int)grade));
            System.out.println(">");
        }
    }
}