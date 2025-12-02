import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class utils {
    public static final String INPUT_FILENAME = "input.txt";
    public static final String OUTPUT_FILENAME = "output.txt";
    public static PrintStream out;
    
    public static void initializeOutputStream() {
        try {
            out = new PrintStream(Files.newOutputStream(Paths.get(OUTPUT_FILENAME)));
        } catch (Exception e) {
            System.err.printf("Error initializing output stream: %s%n", e.getMessage());
        }
    }
}
