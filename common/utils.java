package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class utils {
    public static final String INPUT_FILENAME = "input.txt";
    public static final String OUTPUT_FILENAME = "output.txt";

    public static PrintStream initializeOutputStream() {
        try {
            return new PrintStream(Files.newOutputStream(Paths.get(OUTPUT_FILENAME)));
        } catch (Exception e) {
            System.out.println("Error while initializing output stream : " + e);
            return null;
        }
    }

    public static BufferedReader initilaizeReader() throws IOException {
        return new BufferedReader(new FileReader(INPUT_FILENAME));
    }

    public static long getCurrentTime() {
        return Instant.now().getNano() / 1000;
    }
}
