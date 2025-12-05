package day5;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.TreeSet;

import common.utils;

public class Cafeteria {
    public static PrintStream out = utils.initializeOutputStream();

    private static TreeSet<long[]> freshRange = new TreeSet<>((a, b) -> {
        if(a[0] != b[0])
            return Long.compare(a[0], b[0]);
        return Long.compare(a[1], b[1]);
    });

    public static void main(String[] args) {
        try {
            BufferedReader reader = utils.initilaizeReader();
            
            long startTime = utils.getCurrentTime();
            
            String line = reader.readLine();
            boolean isRange = true;
            long ans = 0;
            while(line != null) {
                if(line.length() == 0) {
                    isRange = false;
                    line = reader.readLine();
                    continue;
                }

                if(isRange) {
                    String[] parts = line.split("-");
                    long start = Long.parseLong(parts[0]), end = Long.parseLong(parts[1]);
                    addRange(start, end);
                    // out.printf("Range : %d-%d\n", start, end);
                } else {
                    long ingredient = Long.parseLong(line);
                    if(isInRange(ingredient))
                        ans++;
                }
                line = reader.readLine();
            }
            long endTime = utils.getCurrentTime();

            utils.printTimeStats(out, startTime, endTime);
            out.printf("Answer : %d\n", ans);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

    private static void addRange(long start, long end) {
        freshRange.add(new long[]{start, end});
    }
    private static boolean isInRange(long ing) {
        Iterator<long[]> itr = freshRange.iterator();

        while(itr.hasNext()) {
            long[] range = itr.next();

            long start = range[0], end = range[1];
            if(start <= ing && ing <= end) 
                return true;
        }

        return false;
    }
}

