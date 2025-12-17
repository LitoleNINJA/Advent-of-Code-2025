package day6;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import common.utils;

public class TrashCompactor {
    public static PrintStream out = utils.initializeOutputStream();

    public static void main(String[] args) {
        try {
            ArrayList<String> lines = utils.getInputLines();
            long ans = 0;

            long startTime = utils.getCurrentTime();
            ArrayList<int[]> grid = new ArrayList<>();
            for(int i=0; i<lines.size()-1; i++) {
                String[] parts = lines.get(i).trim().split("\\s+");

                int[] numbers = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
                grid.add(numbers);
            }

            String[] operators = lines.getLast().trim().split("\\s+");
            int n = grid.size(), m = grid.getFirst().length;
            for(int i=0; i<m; i++) {
                // part 1
                // long columnValue = (operators[i].equals("*")) ? 1 : 0; 
                // for(int j=0; j<n; j++) {
                //     int currentNumber = grid.get(j)[i];

                //     if(operators[i].equals("*"))
                //         columnValue *= currentNumber;
                //     else
                //         columnValue += currentNumber;
                // }

                // ans += columnValue;

                // part 2
                ans += doSomeStupidShit(grid, n, i, operators[i]);
            }
            
            long endTime = utils.getCurrentTime();
            utils.printTimeStats(out, startTime, endTime);

            out.printf("Answer : %d\n", ans);
            System.out.println("Answer : " + ans);
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace(out);
        }
    }

    private static long doSomeStupidShit(ArrayList<int[]> grid, int n, int col, String op) {
        int operator = (op.equals("*")) ? 1 : 0;
        long ans = 0;
        
        int maxDigits = 0;
        for(int i=0; i<n; i++) {
            int numDigits = String.valueOf(grid.get(i)[col]).length();
            maxDigits = Math.max(maxDigits, numDigits);
        }
        
        for(int digitPos=0; digitPos<maxDigits; digitPos++) {
            long cur = 0;
            for(int i=0; i<n; i++) {
                int num = grid.get(i)[col];
                String numStr = String.format("%0" + maxDigits + "d", num);
                out.printf("Num : %d, String : %s\n", num, numStr);
                
                int digit = 0;
                if(digitPos < numStr.length()) {
                    digit = numStr.charAt(digitPos) - '0';
                }
                
                if(digit != 0)
                    cur = cur*10 + digit;
            }

            out.printf("%d : %d\n", digitPos+1, cur);
        }
        
        return ans;
    }
}
