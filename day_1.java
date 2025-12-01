import java.io.BufferedReader;
import java.io.FileReader;

public class day_1
{
    public static void main(String[] args) {
        int zeroesCount = 0;
        int currentPos = 50;

        try(BufferedReader br = new BufferedReader(new FileReader("input.txt")))
        {
            String line = br.readLine();

            while(line!=null)
            {
                char direction = line.charAt(0);
                int digits = Integer.parseInt(line.substring(1,line.length()));

                if(direction == 'L')
                {
                    currentPos -= digits;
                    if(currentPos<0)
                    {
                        currentPos = currentPos%100;
                    }
                }
                else
                {
                    currentPos += digits;
                    if(currentPos>100)
                    {
                        currentPos = currentPos%100;
                    }
                }
                if(currentPos == 0)
                {
                    zeroesCount++;
                }
                br.readLine();
            }
            
            System.out.printf("Answer : %d", zeroesCount);

        }
        catch(Exception e)
        {
            System.out.printf("File was not found :!! %s", e );
        }
    }
}