package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class utils {
    public static final String INPUT_FILENAME = "input.txt";
    public static final String OUTPUT_FILENAME = "output.txt";

    // 8 directions: N, NE, E, SE, S, SW, W, NW
    public static final int[][] DIRECTIONS_8 = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},           {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

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
        return System.currentTimeMillis();
    }
    public static void printTimeStats(PrintStream out, long processStartTime, long startTime, long endTime) {
        out.println("\n---------------------------------");
        out.printf("%-20s : %6d ms%n", "Input read time", processStartTime - startTime);
        out.printf("%-20s : %6d ms%n", "Process time", endTime - processStartTime);
        out.printf("%-20s : %6d ms%n", "Total time", endTime - startTime);
        out.println("---------------------------------");
    }
    public static void printTimeStats(PrintStream out, long startTime, long endTime) {
        out.println("\n---------------------------------");
        out.printf("%-20s : %6d ms%n", "Total time", endTime - startTime);
        out.println("---------------------------------");
    }

    public static ArrayList<String> getInputLines() throws IOException {
        BufferedReader reader = initilaizeReader();
        ArrayList<String> lines = new ArrayList<>();

        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();

        return lines;
    }

    public static boolean isInGrid(int i, int j, int rows, int cols) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }

}
