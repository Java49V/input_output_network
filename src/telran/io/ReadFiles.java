package telran.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFiles {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java ReadFiles <sourceFile> <textOnlyFile> <commentsOnlyFile>");
            return;
        }

        String sourceFile = args[0];
        String textOnlyFile = args[1];
        String commentsOnlyFile = args[2];

        try {
            // Read source file
            System.out.println("Contents of Source File:");
            readAndPrintFileContents(sourceFile);

            // Read text-only file
            System.out.println("\nContents of Text-Only File:");
            readAndPrintFileContents(textOnlyFile);

            // Read comments-only file
            System.out.println("\nContents of Comments-Only File:");
            readAndPrintFileContents(commentsOnlyFile);
        } catch (IOException e) {
            System.err.println("Error while reading the files: " + e.getMessage());
        }
    }

    private static void readAndPrintFileContents(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
