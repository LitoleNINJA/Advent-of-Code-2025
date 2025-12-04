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
            long ans = 0;
            while(line != null) {
                // ans += findMaxJoltage_2digits(line);
                ans += findMaxJoltage_12digits(line);

                line = reader.readLine();
            }

            long endTime = utils.getCurrentTime();
            out.printf("Total time elapsed : %d ms\n", endTime - startTime);

            System.out.println("Answer : " + ans);
        } catch (Exception e) {
            System.out.println("Exception ocurred : " + e);
        }
    }

    private static int findMaxJoltage_2digits(String line) {
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

    private static long findMaxJoltage_12digits(String line) {
        // pick the best digit for each position starting from left, 
        // while keeping enough remaining digits on the right to still make a 12 digit number
        long ans = 0;
        int lastDigitPos = -1;
        for(int i=0; i<12; i++) {
            int bestDigitPos = findBestDigitPos(line, lastDigitPos+1, line.length() - 12 + i);
            int bestDigit = line.charAt(bestDigitPos) - '0';
            ans = ans * 10 + bestDigit;
            lastDigitPos = bestDigitPos;
        }

        return ans;
    }

    private static int findBestDigitPos(String line, int start, int end) {
        int pos = -1, best = 0;
        for(int i=start; i<=end; i++) {
            int cur = line.charAt(i) - '0';
            if(cur > best) {
                best = cur;
                pos = i;
            }
        }

        return pos;
    }
}
