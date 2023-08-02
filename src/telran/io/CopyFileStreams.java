package telran.io;

import java.io.*;

public class CopyFileStreams implements CopyFile {
    private int bufferLength;

    public CopyFileStreams(int bufferLength) {
        this.bufferLength = bufferLength;
    }

    @Override
    public void copy(String pathToSource, String pathToDestination) {
        try (InputStream inputStream = new FileInputStream(pathToSource);
             OutputStream outputStream = new FileOutputStream(pathToDestination)) {

            byte[] buffer = new byte[bufferLength];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
