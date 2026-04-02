import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.Stream;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("wrong arguments");
            return;
        }       
        String arg = args[0];
        String[] parts = arg.split("=");
        if(!parts[0].equals("--current-folder") || parts.length < 2){
            System.out.println("wrong arguments");
            return;
        }
        Path currentPath = Paths.get(parts[1]);
        if(!Files.exists(currentPath)){
            System.out.println("wrong path");
            return;
        }
        System.out.println(currentPath.toAbsolutePath());
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            if (command.equals("exit")){
                return;
            }
            else if (command.equals("ls")){
                try (Stream<Path> paths = Files.list(currentPath)){
                    paths.forEach(path -> {
                        try {
                            System.out.println(path.getFileName() + " " + Files.size(path)/1024 + " KB");
                        } catch (IOException e){
                            System.out.println(e.getMessage());
                        }
                    });
                }
            }
            else if (command.startsWith("cd ")){
                String[] parts = command.split(" ");
                if(parts.length != 2) {
                    System.out.println("wrong command");
                    continue;
                }
                String folderName = parts[1];
                Files.

            }
        }
        
        
    }
}