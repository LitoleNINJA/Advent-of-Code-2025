package day4;

import common.utils;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class PrintingDepartment {
    public static PrintStream out = utils.initializeOutputStream();
    public static void main(String[] args) {
        try {
            long startTime = utils.getCurrentTime();
            
            ArrayList<String> lines = utils.getInputLines();
            
            int n = lines.size(), m = lines.getFirst().length();
            
            int ans = 0;
            ArrayDeque<int[]> queue = new ArrayDeque<>();

            long processStartTime = utils.getCurrentTime();
            for(int i=0; i<n; i++) {
                for(int j=0; j<m; j++) {
                    if(isRemovable(lines, i, j, n, m))
                        queue.add(new int[]{i, j});
                }
            }

            ans = recursiveRemove(queue, lines, n, m);
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

    private static boolean isRemovable(ArrayList<String> lines, int i, int j, int n, int m) {
        if(lines.get(i).charAt(j) == '@') {
            int count = countAdjacentRolls(lines, i, j, n, m); 
            if(count < 4) {
                return true;
            }
        }

        return false;
    }

    private static int recursiveRemove(ArrayDeque<int[]> queue, ArrayList<String> lines, int n, int m) {
        int ans = 0;

        while(!queue.isEmpty()) {
            int[] first = queue.pollFirst();
            int i = first[0], j = first[1];

            // this is not a valid roll and cant be removed
            if(!isRemovable(lines, i, j, n, m)) 
                continue;

            ans++;

            // Replace character at position j with an 'x'
            String currentLine = lines.get(i);
            String newLine = currentLine.substring(0, j) + 'x' + currentLine.substring(j + 1);
            lines.set(i, newLine);

            // mark all it's neighbours as possibly removable
            for(int[] dir : utils.DIRECTIONS_8) {
                int new_i = i + dir[0], new_j = j + dir[1];
                if (utils.isInGrid(new_i, new_j, n, m)) {
                    if (lines.get(new_i).charAt(new_j) == '@') {
                        queue.add(new int[]{new_i, new_j});
                    }
                }
            }
        }

        return ans;
    }
}
