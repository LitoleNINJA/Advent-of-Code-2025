package day9;

import java.io.PrintStream;
import java.util.ArrayList;

import common.Point;
import common.utils;

public class MovieTheater {
    public static PrintStream out = utils.initializeOutputStream();

    public static void main(String[] args) {
        try {
            long startTime = utils.getCurrentTime();
            long ans = utils.readInputAndSolve(MovieTheater::findMaxArea);
            long endTime = utils.getCurrentTime();
    
            utils.printTimeStats(out, startTime, endTime);
            out.printf("Answer : %d\n", ans);
            System.out.println("Answer : " + ans);
        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace(out);
        }
    }

    private static long findMaxArea(ArrayList<String> lines) {
        long ans = 0;

        ArrayList<Point> tiles = new ArrayList<>();
        for(String line : lines) {
            String[] parts = line.split(",");
            int x = Integer.parseInt(parts[0]); 
            int y = Integer.parseInt(parts[1]);
            tiles.add(new Point(x, y)); 
        }

        for(int i=0; i<tiles.size(); i++) {
            for(int j=i+1; j<tiles.size(); j++) {
                Point a = tiles.get(i);
                Point b = tiles.get(j);

               long length = a.findHorizontalRelative(b);
               long breadth = a.findVerticalRelative(b); 
               long area = length*breadth;
                if(area>ans)
                {
                    out.printf("Points are : %s, %s\n Area : %d\n", a, b, area);
                }
               ans = Math.max(ans, area);
            }
        }

        return ans;
    }
}
