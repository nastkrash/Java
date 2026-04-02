import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;



public class Program {
    public static void main(String[] args) {
        Map<String, byte[]> signatures = new HashMap<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader("signatures.txt"));

            String line;

            while((line = br.readLine()) != null) {
                String[] parts = line.split(", ");

                String type = parts[0];
                String hex = parts[1];

                byte[] bytes = hexToBytes(hex);

                signatures.put(type, bytes);
            }
            br.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        while(true) {
            String path = sc.nextLine();
            if(path.equals("42"))
                break;
            try{
                FileInputStream fis = new FileInputStream(path);
                byte[] buffer = new byte[20];
                fis.read(buffer);
                fis.close();
            
            

                String result = null;
                for (String type : signatures.keySet()) {
                    byte[] value = signatures.get(type);
                    boolean equal = true;
                    for (int i = 0; i < value.length; i++) {
                        if (buffer[i] != value[i]) {
                            equal = false;
                            break;
                        }
                    }
                    if (equal == true){
                        result = type;
                        break;
                    }
                    
                }

                if (result != null) {
                    FileOutputStream fos = new FileOutputStream("result.txt",true);
                    fos.write((result + "\n").getBytes());
                    fos.close();
                    System.out.println("PROCESSED");
                }
                else
                    System.out.println("UNDEFINED");
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }

        

    }
    public static byte[] hexToBytes(String hex) {
        String[] parts = hex.split(" ");
        byte[] result = new byte[parts.length];
        for (int i = 0; i < parts.length; i++)
            result[i] = (byte) Integer.parseInt(parts[i], 16);
        return result;
    }
}