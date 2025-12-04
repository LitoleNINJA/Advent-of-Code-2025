package day4;

import common.utils;

import java.io.PrintStream;
import java.util.ArrayList;

public class PrintingDepartment {
    public static PrintStream out = utils.initializeOutputStream();
    public static void main(String[] args) {
        try {
            long startTime = utils.getCurrentTime();
            
            ArrayList<String> lines = utils.getInputLines();
            int n = lines.size(), m = lines.getFirst().length();
            
            long processStartTime = utils.getCurrentTime();
            int ans = 0;
            for(int i=0; i<n; i++) {
                for(int j=0; j<m; j++) {
                    if(lines.get(i).charAt(j) == '@') {
                        int count = countAdjacentRolls(lines, i, j, n, m); 
                        if(count < 4) {
                            // out.printf("Roll found at i=%d, j=%d\n", i, j);
                            ans++;
                        }
                    }
                }
            }
            long endTime = utils.getCurrentTime();
            
            utils.printTimeStats(out, processStartTime, startTime, endTime);
            
            System.out.println("Answer : " + ans);
            out.printf("Answer : %d", ans);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

    private static int countAdjacentRolls(ArrayList<String> lines, int i, int j, int n, int m) {
        int count = 0;
        
        for(int[] dir : utils.DIRECTIONS_8) {
            int new_i = i + dir[0], new_j = j + dir[1];
            if (utils.isInGrid(new_i, new_j, n, m)) {
                if (lines.get(new_i).charAt(new_j) == '@') {
                    count++;
                }
            }
        }

        return count;
    }
}
