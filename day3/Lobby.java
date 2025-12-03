package day3;

import java.io.BufferedReader;
import java.io.PrintStream;
import common.utils;

public class Lobby {
    public static PrintStream out = utils.initializeOutputStream();

    public static void main(String[] args) {
        try {
            BufferedReader reader = utils.initilaizeReader();

            long startTime = utils.getCurrentTime();
            String line = reader.readLine();
            int ans = 0;
            while(line != null) {
                ans += findMaxJoltage(line);

                line = reader.readLine();
            }

            long endTime = utils.getCurrentTime();
            out.printf("Total time elapsed : %d ms\n", endTime - startTime);

            System.out.println("Answer : " + ans);
        } catch (Exception e) {
            System.out.println("Exception ocurred : " + e);
        }
    }

    private static int findMaxJoltage(String line) {
        int length = line.length();

        int max_so_far = -1;
        int ans = 0;
        for(int i=0; i<length; i++) {
            int digit = line.charAt(i) - '0';

            if(max_so_far == -1) {
                max_so_far = digit;
                continue;
            }

            int joltage = max_so_far * 10 + digit;
            ans = Math.max(ans, joltage);

            max_so_far = Math.max(max_so_far, digit);
        }

        return ans;
    }
}
