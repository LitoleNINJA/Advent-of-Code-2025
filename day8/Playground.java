package day8;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeSet;

import common.DSU;
import common.Point;
import common.utils;

public class Playground {
    public static PrintStream out = utils.initializeOutputStream();
    public static DSU dsu;

    public static void main(String[] args) {
        try {
            long startTime = utils.getCurrentTime();

            long ans = utils.readInputAndSolve(Playground::solve);

            long endTime = utils.getCurrentTime();

            utils.printTimeStats(out, startTime, endTime);
            out.printf("Asnwer : %d\n", ans);
            System.out.println("Answer : " + ans);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace(out);
        }
    }

    private static long solve(ArrayList<String> lines) {
        ArrayList<Point> points = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            points.add(new Point(x, y, z));
        }
        // out.printf("Points : %s\n", points);

        dsu = new DSU(points.size());

        return runIterations(points, 10);
    }

    private static long runIterations(ArrayList<Point> points, int count) {
        for (int k = 0; k < count; k++) {
            long minDistance = Long.MAX_VALUE;

            int x = -1, y = -1;
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    long distance = points.get(i).distance(points.get(j));
                    if (distance < minDistance && dsu.find(i) != dsu.find(j)) {
                        x = i;
                        y = j;
                        minDistance = distance;
                    }
                }
            }

            if (x != -1 && y != -1) {
                out.printf("Point %s, %s at dist=%d\n", points.get(x), points.get(y), minDistance);
                dsu.union(x, y);
            }
        }
        dsu.print(out);

        TreeSet<Integer> set = dsu.components();
        if (set.size() >= 3) {
            int largest = set.pollLast();
            int secondLargest = set.pollLast();
            int thirdLargest = set.pollLast();

            return (long) largest * secondLargest * thirdLargest;
        }
        return -1;
    }
}
