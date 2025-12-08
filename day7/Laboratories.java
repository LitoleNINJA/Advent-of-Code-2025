package day7;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeSet;

import common.utils;

public class Laboratories {
    public static PrintStream out = utils.initializeOutputStream();

    public static void main(String[] args) {
        try {
            ArrayList<String> lines = utils.getInputLines();
            int n = lines.size(), m = lines.getFirst().length();

            long startTime = utils.getCurrentTime();
            int count = 0;
            int startPos = lines.getFirst().indexOf("S");
            TreeSet<Integer> beams = new TreeSet<>();
            beams.add(startPos);
            for(int i=1; i<n; i++) {
                String cur = lines.get(i);
                for(int j=0; j<cur.length(); j++) {
                    if(cur.charAt(j) == '^' && beams.contains(j)) {
                        beams.remove(j);
                        beams.add(j > 0 ? j-1 : j);
                        beams.add(j < n-1 ? j+1 : j);
                        count++;
                    }
                }
            }
            long endTime = utils.getCurrentTime();

            utils.printTimeStats(out, startTime, endTime);
            System.out.println("Answer : " + count);
            out.printf("Answer : %d\n", count);

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace(out);
        }
    }
}
