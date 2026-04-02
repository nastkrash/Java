import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.Math;
import java.util.Collections;

public class Program {
    public static void main(String[] args) {
        if (args.length < 2)
            return;
        String fileA = args[0];
        String fileB = args[1];

        Set<String> dictionary = new HashSet<>();
        List<String> wordsA = new ArrayList<>();
        List<String> wordsB = new ArrayList<>();

        
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileA));
            String line;
            while((line = br.readLine())!= null) {
                line = line.toLowerCase().replaceAll("[^a-zA-Z ]", "");
                String[] parts = line.split("\\s+");
                for (String word : parts) {
                    if (word == null || word.isEmpty())
                        continue;
                    dictionary.add(word);
                    wordsA.add(word);
                }              
            }
            br.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        try{
            BufferedReader br = new BufferedReader(new FileReader(fileB));
            String line;
            while((line = br.readLine())!= null) {
                line = line.toLowerCase().replaceAll("[^a-zA-Z ]", "");
                String[] parts = line.split("\\s+");
                for (String word : parts) {
                    if (word == null || word.isEmpty())
                        continue;
                    dictionary.add(word);
                    wordsB.add(word);
                }              
            }
            br.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }


        Map<String,Integer> frequencesA = new HashMap<>();
        Map<String,Integer> frequencesB = new HashMap<>();

        for (String word : wordsA) {
            frequencesA.put(word, frequencesA.getOrDefault(word,0) + 1);
        }
        for (String word : wordsB) {
            frequencesB.put(word, frequencesB.getOrDefault(word,0) + 1);
        }
        

        int[] vecA = new int[dictionary.size()];
        int[] vecB = new int[dictionary.size()];

        List<String> dictionaryList = new ArrayList<>(dictionary);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for (String w : dictionaryList) {
                bw.write(w);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < vecA.length; i++) {
            String word = dictionaryList.get(i);
            vecA[i] = frequencesA.getOrDefault(word, 0);
            vecB[i] = frequencesB.getOrDefault(word, 0);
            
        }

        double numerator = 0;
        for (int i = 0; i < vecA.length; i++) {
            numerator += vecA[i] * vecB[i];
        }
        double sumOfSquaredA = 0;
        for (int i = 0; i < vecA.length; i++) {
            sumOfSquaredA += vecA[i] * vecA[i];
        }
        double lengthA = Math.sqrt(sumOfSquaredA);
        double sumOfSquaredB = 0;
        for (int i = 0; i < vecB.length; i++) {
            sumOfSquaredB += vecB[i] * vecB[i];
        }
        double lengthB = Math.sqrt(sumOfSquaredB);
        double similarity;
        if (lengthA == 0 || lengthB == 0)
            similarity = 0;
        else
            similarity = numerator / (lengthA * lengthB);
        System.out.printf("Similarity = %.2f%n", similarity);
    }
}