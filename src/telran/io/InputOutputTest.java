package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputOutputTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void pathTests() {
		Path pathParent = Path.of("../..");
		System.out.println(pathParent.toAbsolutePath().normalize()
				.getName(4));
	}
	@Test
	void displayDirContentTest() {
		displayDirContent(Path.of("../.."), 3);
	}

    private void displayDirContent(Path dirPath, int maxDepth) {
        if (!Files.isDirectory(dirPath)) {
            throw new IllegalArgumentException("The provided path is not a directory.");
        }

        try (Stream<Path> pathStream = Files.walk(dirPath, maxDepth)) {
            pathStream.filter(Files::isReadable)
                    .forEach(this::printPathInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printPathInfo(Path path) {
        String fileType = Files.isDirectory(path) ? "dir" : "file";
        String indentation = "  ".repeat(Math.max(0, path.getNameCount() - 3));
        System.out.println(indentation + "<" + path.getFileName() + "> - " + fileType);
    }
	


}