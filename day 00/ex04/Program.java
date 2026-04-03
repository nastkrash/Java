
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        sc.close();

        int[] frequency = new int[65536];

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            int code = (int) c;
            frequency[code]++;
        }

        char[] topChars = new char[10];
        int[] topCounts = new int[10];


        for (int code = 0; code < frequency.length; code++) {
            if (frequency[code] > 0) {
                char currentChar = (char) code;
                int currentCount = frequency[code];
                insertIntoTop(currentChar, currentCount, topChars, topCounts);
            }
        }
        printResult(topChars, topCounts);
    }

    public static void insertIntoTop(char currentChar, int currentCount, char[] topChars, int[] topCounts) {
        int pos = -1;
        for (int i = 0; i < 10; i++)
            if (topChars[i] == currentChar)
                return;
        for (int i = 0; i < 10; i++) {
            if (currentCount > topCounts[i]) {
                pos = i;
                break;
            } else if (currentCount == topCounts[i] && currentCount > 0) {
                if (currentChar < topChars[i]) {
                    pos = i;
                    break;
                }
            }
        }
        if (pos != -1) {
            for (int i = 9; i > pos; i--) {
                topChars[i] = topChars[i - 1];
                topCounts[i] = topCounts[i - 1];
            }
            topChars[pos] = currentChar;
            topCounts[pos] = currentCount;
        }
    }

    public static void printResult(char[] topChars, int[] topCounts) {
        int letterCount = 0;
        for (int i = 0; i < topCounts.length; i++) {
            if (topCounts[i] == 0)
                break;
            letterCount++;
        }
        if (letterCount == 0)
            return;

        int columns = letterCount * 3;
        char[][] resultGrid = new char[12][columns];
        
        int k = 0;
        for (int j = 0; j < columns; j+=3){
            resultGrid[11][j] = ' ';
            resultGrid[11][j+1] = ' ';
            resultGrid[11][j+2] = topChars[k];
            k++;
        }
        double scale = (double) topCounts[0] / 10;
        
        k = 0;
        for (int j = 0; j < columns; j+= 3) {
            int height = (int) (topCounts[k] / scale);
            for (int i = 11 - height; i < 11; i++)
                resultGrid[i][j] = ' ';
            for (int i = 11 - height; i < 11; i++)
                resultGrid[i][j+1] = ' ';
            for (int i = 11 - height; i < 11; i++)
                resultGrid[i][j+2] = '#';
            String num = Integer.toString(topCounts[k]);
            if (topCounts[k] < 10){
                resultGrid[10-height][j] = ' ';
                resultGrid[10-height][j+1] = ' ';
                resultGrid[10-height][j+2] = num.charAt(0);

            }
            else if (topCounts[k] < 100){
                resultGrid[10-height][j] = ' ';
                resultGrid[10-height][j+1] = num.charAt(0);
                resultGrid[10-height][j+2] = num.charAt(1);
            }
            else if (topCounts[k] < 999){
                resultGrid[10-height][j] = num.charAt(0);
                resultGrid[10-height][j+1] = num.charAt(1);
                resultGrid[10-height][j+2] = num.charAt(2);
            }
            k++;
        }

        for (int i = 0; i < 12; i++){
            for (int j = 0; j < columns; j++)
                System.out.print(resultGrid[i][j]);
            System.out.print("\n");
        }
    }

}
