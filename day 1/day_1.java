import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class day_1 {
    private static final String INPUT_FILENAME = "../" + utils.INPUT_FILENAME;
    private static final String OUTPUT_FILENAME = "../" + utils.OUTPUT_FILENAME;
    private static final int STARTING_POSITION = 50;

    public static void main(String[] args) {
        try (PrintStream out = new PrintStream(Files.newOutputStream(Paths.get(OUTPUT_FILENAME)))) {
            int currentPosition = STARTING_POSITION;
            int count = 0;

            for (String line : Files.readAllLines(Paths.get(INPUT_FILENAME))) {

                char direction = line.charAt(0);
                int value = Integer.parseInt(line.substring(1));

                count += value / 100;
                // out.printf("Value: %d, Position: %d%n", value, currentPosition);

                int steps = value % 100;
                int nextPosition = (direction == 'L') ? currentPosition - steps : currentPosition + steps;

                if (nextPosition < 0) {
                    nextPosition = 100 + nextPosition;
                } else if (nextPosition >= 100) {
                    nextPosition = nextPosition - 100;
                }

                if (nextPosition != 0 && (nextPosition < currentPosition && direction == 'R' || 
                    nextPosition > currentPosition && direction == 'L')) {
                    count++;
                }

                if (nextPosition == 0) {
                    count++;
                }

                currentPosition = nextPosition;
                out.printf("Final Position: %d, Count: %d%n", currentPosition, count);
            }

            out.printf("Answer: %d%n", count);

        } catch (Exception e) {
            System.err.printf("Error: %s%n", e.getMessage());
        }
    }
}