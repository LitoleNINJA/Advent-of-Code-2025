package day8;

import java.io.PrintStream;
import java.util.ArrayList;

import common.utils;

public class Playground {
    public static PrintStream out = utils.initializeOutputStream();

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
        
    }
}
