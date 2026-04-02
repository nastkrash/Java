//1. input is string
//2.
import java.util.Scanner;

public class Program{
    public static void main(String[] args){
        // 1. Читаем входные данные
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

         // 2. Создаем массив для подсчета
        int[] frequency = new int[65536];

        // 3. ОДИН проход - подсчитываем все символы
        for (int i = 0; i < line.length(); i++){
            char c = line.charAt(i);
            int code = (int) c;
            frequency[code]++;
        }
        // 4. Теперь frequency[code] содержит количество
        // каждого символа в тексте
        
        // 5.  поиск топ-10
        char[] topChars = new char[10];
        int[] topCounts = new int[10];

        for (int i = 0; i < 10; i++) {
            topChars[i] = 0;
            topCounts[i] = 0;
        }

        for (int code = 0; code < frequency.length; code++) {
            if (frequency[code] > 0) {
                char currentChar = (char) code;
                int currentCount = frequency[code];
                insertIntoTop(currentChar, currentCount, topChars, topCounts);
            }
        }
        printResult(topChars, topCounts);
    }
    public static void insertIntoTop(char currentChar, int currentCount, char[] topChars, int[] topCounts)
    {
        // Находим позицию для вставки
        int pos = -1;
        // Сначала проверяем, есть ли уже такой символ в топе
        for (int i = 0; i < 10; i++)
            if(topChars[i] == currentChar)
                return;
        // Ищем место для вставки
        for (int i = 0; i < 10; i++){
            if(currentCount > topCounts[i]){
            // Текущий символ имеет БОЛЬШЕ вхождений
                pos = i;
                break;
            }
            else if (currentCount == topCounts[i] && currentCount > 0) {
            // Одинаковое количество - сравниваем лексикографически
                if (currentChar < topChars[i]) {
                    pos = i;
                    break;
                }
            }
        }
        // Если нашли место для вставки
        if (pos != -1) {
            // Сдвигаем все элементы вправо от pos
            for (int i = 9; i > pos; i--) {
                topChars[i] = topChars[i-1];
                topCounts[i] = topCounts[i-1];
            }
            // Вставляем новый символ
            topChars[pos] = currentChar;
            topCounts[pos] = currentCount;
        }
    }
    public static void printResult(char[] topChars, int[] topCounts){
        char [][] resultGrid = new char[12][10];
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 10; col++) {
                resultGrid[row][col] = ' ';
            }
        }
        for (int j = 0; j < 10; j++)
            resultGrid[11][j] = topChars[j];
        double scale = topCounts[0] / 10;
        for (int j = 0; j < 10; j++) {
            int height = (int)(topCounts[j] / scale);
            for (int i = 11 - height; i < 11; i++)
                resultGrid[i][j] = '#';
            resultGrid[11 - height - 1][j] = Integer.toString(topCounts[j]);
        }

        for (int i = 0; i < 12; i++) 
            for (int j = 0; j < 10; j++) 
                System.out.print(resultGrid[i][j]);
            
        
    }
    
}