package day11;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import common.utils;


public class Reactor {
    private static PrintStream out = utils.initializeOutputStream();
    private static HashMap<String, Long> memo = new HashMap<>();

    public static void main(String[] args) {
        try {
            long startTime = utils.getCurrentTime();
            
            long ans = utils.readInputAndSolve(Reactor::solve);

            long endTime = utils.getCurrentTime();

            utils.printTimeStats(out, startTime, endTime);
            out.printf("Asnwer : %d\n", ans);
            System.out.println("Answer : " + ans);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace(out);
        }  
    }

    private static long solve(ArrayList<String> input) {
        HashMap<String, ArrayList<String>> adj = new HashMap<>();
        for(String line : input)
        {
            String[] parts = line.split(": ");
            adj.put(parts[0], Arrays.stream(parts[1].split(" ")).collect(Collectors.toCollection(ArrayList::new)));
        }
        
        HashSet<String> visited = new HashSet<>();
        return dfs(adj, "svr", "out", visited, false, false);
    }

    private static long dfs(HashMap<String, ArrayList<String>> adj, String source, String target, 
                            HashSet<String> visited, boolean hasDac, boolean hasFft) {
        
        if(visited.contains(source))
            return 0;
            
        if(source.equals(target)) {
            return (hasDac && hasFft) ? 1 : 0;
        }
        
        if(!adj.containsKey(source))
            return 0;
        
        if(source.equals("dac")) {
            hasDac = true;
        } 
        if (source.equals("fft")) {
            hasFft = true;
        }
        
        String key = source + "," + hasDac + "," + hasFft;
        if(memo.containsKey(key) && !visited.contains(source)) {
            return memo.get(key);
        }
        
        visited.add(source);
        long count = 0;
        
        for(String v : adj.get(source)) {
            count += dfs(adj, v, target, visited, hasDac, hasFft);
        }
        
        visited.remove(source);
        
        if(!visited.contains(source)) {
            memo.put(key, count);
        }
        
        return count;
    }
}