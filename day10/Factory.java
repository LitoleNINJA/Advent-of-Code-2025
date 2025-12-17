package day10;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import common.utils;

public class Factory {
    public static PrintStream out = utils.initializeOutputStream();

    public static void main(String[] args) {
        try {
            long startTime = utils.getCurrentTime();
            
            long ans = utils.readInputAndSolve(Factory::solve);

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
        long ans = 0;
        
        for(String line : lines) {
            String[] parts = line.split(" ");

            ans += findMinMoves(parts);
        }

        return ans;
    }
    
    private static long findMinMoves(String[] parts) {
        String lightMap = parts[0].substring(1, parts[0].length()-1);

        ArrayList<int[]> buttons = extractButtons(parts);

        out.printf("LightMap : %s\n", lightMap);
        out.print("Buttons : ");
        for(int i=0; i<buttons.size(); i++) {
            out.print(Arrays.toString(buttons.get(i)));
            if(i < buttons.size()-1) out.print(", ");
        }
        out.println();

        return permutations(buttons, lightMap);
    }

    private static ArrayList<int[]> extractButtons(String[] parts) {
        ArrayList<int[]> buttons = new ArrayList<>();

        for(int i=1; i<parts.length-1; i++) {
            int[] cur = Arrays.stream(parts[i].substring(1, parts[i].length()-1)
                              .split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();
            buttons.add(cur);
        }

        return buttons;
    }

    private static long permutations(ArrayList<int[]> buttons, String lightMap) {
        long ans = Long.MAX_VALUE;

        int n = buttons.size();
        for(int i=1; i<=(1<<n)-1; i++) {
            out.println("Calculating for mask " + i + "---------");
            String curMap = pressButtons(buttons, i, n, lightMap.length());
            if(lightMap.equals(curMap)) {
                out.printf("Match found at i=%d, count=%d\n", i, Integer.bitCount(i));
                ans = Math.min(ans, Integer.bitCount(i));
            }
        }
        
        return ans;
    }
    
    private static String pressButtons(ArrayList<int[]> buttons, int mask, int n, int len) {
        StringBuilder cur = new StringBuilder();
        cur.repeat('.', len);
        
        for(int i=0; i<n; i++) {
            if(utils.isBitSet(mask, i)) {
                out.printf("Clicking button %d for mask %d\n", i, mask);
                int[] button = buttons.get(i);
                for(int key : button) {
                    if(cur.charAt(key) == '.')
                        cur.setCharAt(key, '#');
                    else
                        cur.setCharAt(key, '.');
                }
            }
        }
        
        
        out.printf("Mask = %d : Map = %s\n", mask, cur.toString());

        return cur.toString();
    }
}
