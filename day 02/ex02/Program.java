import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.Stream;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("wrong arguments");
            }
            String arg = args[0];
            String[] parts = arg.split("=");
            if (!parts[0].equals("--current-folder") || parts.length < 2) {
                throw new IllegalArgumentException("wrong arguments");
            }
            Path currentPath = Paths.get(parts[1]).toAbsolutePath();
            if (!Files.exists(currentPath)) {
                throw new IllegalArgumentException("wrong current folder name");
            }
            System.out.println(currentPath.toAbsolutePath());
            try (Scanner sc = new Scanner(System.in)) {
                while (true) {
                    String command = sc.nextLine();
                    if (command.equals("exit")) {
                        return;
                    } else if (command.equals("ls")) {
                        try (Stream<Path> paths = Files.list(currentPath)) {
                            paths.forEach(path -> {
                                try {
                                    System.out.println(path.getFileName() + " " + Files.size(path) / 1024 + " KB");
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                        }
                    } else if (command.startsWith("cd")) {
                        parts = command.split("\\s+");
                        if (parts.length > 2) {
                            System.out.println("wrong command");
                            continue;
                        }
                        Path newPath;
                        String folderName = "";
                        if (parts.length == 1) {
                            newPath = Paths.get(System.getProperty("user.home"));
                        } else {
                            folderName = parts[1];
                            if (folderName.equals("..")) {
                                Path parent = currentPath.getParent();
                                newPath = (parent != null) ? parent : currentPath;
                            } else {
                                newPath = currentPath.resolve(folderName).normalize();
                            }
                        }

                        if (Files.exists(newPath) && Files.isDirectory(newPath)) {
                            currentPath = newPath;
                        } else {
                            System.out.println("No such file or directory");
                        }

                        System.out.println(currentPath.toAbsolutePath());

                    } else if (command.startsWith("mv")) {
                        parts = command.split("\\s+");
                        if (parts.length != 3){
                            System.out.println("wrong command");
                            continue;
                        }
                        String file1 = parts[1];
                        String file2 = parts[2];
                        Path file1Path = currentPath.resolve(file1);
                        Path file2Path = currentPath.resolve(file2);

                        if (!Files.exists(file1Path)) {
                            System.out.println("No such file or directory");
                            continue;
                        }
                        else {
                            if (Files.isDirectory(file2Path)) {
                                Files.move(file1Path, file2Path.resolve(file1Path.getFileName()));
                            } else {
                                Files.move(file1Path, file2Path);
                            }
                        }
                    }
                    else{
                        System.out.println("wrong command");
                        continue;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}