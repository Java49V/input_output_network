package telran.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextCommentsSeparation {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java TextCommentsSeparation <sourceFile> <textOnlyFile> <commentsOnlyFile>");
            return;
        }

        String sourceFile = args[0];
        String textOnlyFile = args[1];
        String commentsOnlyFile = args[2];

        separateTextAndComments(sourceFile, textOnlyFile, commentsOnlyFile);
//        // Start ReadFiles program to display the contents of all files
//        try {
//            ProcessBuilder pb = new ProcessBuilder("java", "ReadFiles", sourceFile, textOnlyFile, commentsOnlyFile);
//            pb.inheritIO(); // Redirects the subprocess standard I/O to the current process
//            Process process = pb.start();
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Error while executing ReadFiles: " + e.getMessage());
//        }
    }

    private static void separateTextAndComments(String sourceFile, String textOnlyFile, String commentsOnlyFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter textWriter = new BufferedWriter(new FileWriter(textOnlyFile));
             BufferedWriter commentsWriter = new BufferedWriter(new FileWriter(commentsOnlyFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("//")) {
                    commentsWriter.write(line);
                    commentsWriter.newLine();
                } else if (line.trim().startsWith("#")) {
                        commentsWriter.write(line);
                        commentsWriter.newLine();
                } else {
                    textWriter.write(line);
                    textWriter.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Error while processing the files: " + e.getMessage());
        }
        
    }

}

