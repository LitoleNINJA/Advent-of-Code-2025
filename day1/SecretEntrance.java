package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class SecretEntrance
{
    public static String INPUT_FILENAME = "input.txt";
    public static String OUTPUT_FILENAME = "output.txt";
    public static void main(String[] args) {
        // int zeroesCount = 0;
        int currentPos = 50;
        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(INPUT_FILENAME));
            PrintStream out = new PrintStream(new FileOutputStream(OUTPUT_FILENAME)))
        {
            String line = br.readLine();
            
            while(line != null)
            {
                char direction = line.charAt(0);
                int digits = Integer.parseInt(line.substring(1,line.length()));
                
                count += digits / 100;
                out.printf("Digits : %d, Pos : %d\n", digits,  currentPos);
                
                digits %= 100;
                int nextPos = -1;

                if(direction == 'L')
                    nextPos = currentPos - digits;
                else
                    nextPos = currentPos + digits;

                if(nextPos < 0) {
                    nextPos = 100 + nextPos;
                    if(currentPos != 0)
                        count++;
                } else if (nextPos > 99) {
                    nextPos = nextPos - 100;
                    if (nextPos != 0)
                        count++;
                }

                if(nextPos == 0)
                {
                    // zeroesCount++;
                    count++;
                }
                currentPos = nextPos;
                line = br.readLine();
                out.printf("Final Pos : %d, Count : %d\n", currentPos, count);
            };
            
            out.printf("Answer : %d\n", count);

        }
        catch(Exception e)
        {
            System.out.printf("Exception :!! %s", e );
        }
    }
}