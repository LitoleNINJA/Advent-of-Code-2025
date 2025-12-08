package day5;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.TreeSet;

import common.utils;

public class Cafeteria {
    public static PrintStream out = utils.initializeOutputStream();

    private static TreeSet<long[]> freshRange = new TreeSet<>((a, b) -> {
        if (a[0] != b[0])
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
            while (line != null) {
                if (line.isEmpty()) {
                    isRange = false;
                    mergeRanges();
                    // printTree();
                    line = reader.readLine();
                    continue;
                }

                if (isRange) {
                    String[] parts = line.split("-");
                    long start = Long.parseLong(parts[0]), end = Long.parseLong(parts[1]);
                    addRange(start, end);
                } else {
                    long ingredient = Long.parseLong(line);
                    if (isInRange(ingredient))
                        ans++;
                }
                line = reader.readLine();
            }
            long endTime = utils.getCurrentTime();

            utils.printTimeStats(out, startTime, endTime);
            out.printf("Answer Part 1: %d\n", ans);
            out.printf("Answer Part 2: %d\n", countAllFresh());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace(out);
        }
    }

    private static void addRange(long start, long end) {
        freshRange.add(new long[] { start, end });
    }

    private static boolean isInRange(long ing) {
        Iterator<long[]> itr = freshRange.iterator();

        while (itr.hasNext()) {
            long[] range = itr.next();

            long start = range[0], end = range[1];
            if (start <= ing && ing <= end)
                return true;
        }

        return false;
    }

    private static void mergeRanges() {
        TreeSet<long[]> mergedRanges = new TreeSet<>((a, b) -> {
            if (a[0] != b[0])
                return Long.compare(a[0], b[0]);
            return Long.compare(a[1], b[1]);
        });

        Iterator<long[]> itr = freshRange.iterator();
        while (itr.hasNext()) {
            long[] range = itr.next();
            long start = range[0], end = range[1];
            if (mergedRanges.isEmpty()) {
                mergedRanges.add(range);
                continue;
            }

            long prevStart = mergedRanges.last()[0], prevEnd = mergedRanges.last()[1];

            // If merged is empty or current interval does not overlap
            if (prevEnd < start)
                mergedRanges.add(range);
            else {
                // overlap
                mergedRanges.remove(mergedRanges.last());

                // find new range end, start will be same as prevStart, end can increase
                end = Math.max(prevEnd, end);
                mergedRanges.add(new long[] { prevStart, end });
            }
        }

        freshRange = mergedRanges;
    }

    private static long countAllFresh() {
        long ans = 0;
        Iterator<long[]> itr = freshRange.iterator();

        while(itr.hasNext()) {
            long[] range = itr.next();
            long start = range[0], end = range[1];

            ans += end - start + 1;
        }

        return ans;
    }

    private static void printTree() {
        Iterator<long[]> itr = freshRange.iterator();

        while (itr.hasNext()) {
            long[] range = itr.next();

            long start = range[0], end = range[1];
            out.printf("Range : %d - %d\n", start, end);
        }
    }
}
