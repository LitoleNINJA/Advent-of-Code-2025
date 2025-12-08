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
                ans += doSomeStupidShit(grid, i, operators[i]);
            }
            
            long endTime = utils.getCurrentTime();
            utils.printTimeStats(out, startTime, endTime);

            out.printf("Asnwer : %d\n", ans);
            System.out.println("Answer : " + ans);
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace(out);
        }
    }

    private static long doSomeStupidShit(ArrayList<int[]> grid, int col, String op) {
        long cur = 0;


        return cur;
    }
}
