package telran.io;

import telran.io.CopyFileStreams;

import java.io.File;
import java.nio.file.Files;

public class CopyFilePerformanceAppl {
    public static void main(String[] args) {
        String pathToSourceFile = "source_file.txt"; 
        String pathToDestinationFile = "dest_file.txt"; 

        File sourceFile = new File(pathToSourceFile);
        long fileSize = sourceFile.length();

        int[] bufferLengths = {10_000, 100_000, 1_000_000, 100_000_000};

        for (int bufferLength : bufferLengths) {
            CopyFileStreams copyFileStreams = new CopyFileStreams(bufferLength);
            CopyPerformanceTest copyPerformanceTest =
                    new CopyPerformanceTest("Copy Test with buffer length: " + bufferLength, 3, pathToSourceFile, pathToDestinationFile, copyFileStreams);

            System.out.println("File size: " + fileSize + " bytes");
            System.out.println("Buffer length: " + bufferLength + " bytes");
            copyPerformanceTest.run();
            System.out.println("-----------------");
        }
    }
}





