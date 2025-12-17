package common;

import java.util.TreeSet;

public class DSU {
    private int[] par;
    private int[] size;

    public DSU(int n) {
        par = new int[n];
        size = new int[n];

        for(int i=0; i<n; i++) {
            par[i] = i;
            size[i] = 0;
        }
    }

    public int find(int i) {
        if(i == par[i])
            return i;

        return par[i] = find(par[i]);
    }

    public boolean union(int i, int j) {
        i = find(i);
        j = find(j);

        if(i == j)
            return false;

        if(size[i] < size[j]) {
            int temp = i;
            i = j;
            j = temp;
        }

        size[i] += size[j];
        par[j] = i;

        return true;
    }

    public void print(java.io.PrintStream out) {
        out.println("\n=== DSU Structure ===");
        
        // Group nodes by their root
        java.util.HashMap<Integer, java.util.ArrayList<Integer>> groups = new java.util.HashMap<>();
        for(int i = 0; i < par.length; i++) {
            int root = find(i);
            groups.computeIfAbsent(root, k -> new java.util.ArrayList<>()).add(i);
        }
        
        // Sort by component size (largest first)
        java.util.ArrayList<java.util.Map.Entry<Integer, java.util.ArrayList<Integer>>> sorted = 
            new java.util.ArrayList<>(groups.entrySet());
        sorted.sort((a, b) -> Integer.compare(b.getValue().size(), a.getValue().size()));
        
        out.printf("Total Components: %d\n", groups.size());
        out.printf("Total Nodes: %d\n\n", par.length);
        
        int componentNum = 1;
        for(java.util.Map.Entry<Integer, java.util.ArrayList<Integer>> entry : sorted) {
            int root = entry.getKey();
            java.util.ArrayList<Integer> members = entry.getValue();
            
            out.printf("Component %d (Root=%d, Size=%d): ", 
                componentNum++, root, members.size());
            
            if(members.size() <= 10) {
                out.println(members);
            } else {
                out.printf("[%d, %d, %d, ... %d more ... %d, %d, %d]\n",
                    members.get(0), members.get(1), members.get(2),
                    members.size() - 6,
                    members.get(members.size()-3), 
                    members.get(members.size()-2), 
                    members.get(members.size()-1));
            }
        }
        out.println("===================\n");
    }

    public int findMaxSize() {
        int ans = 0;
        for(int s : size) {
            ans = Math.max(ans, s);
        }
        return ans;
    }

    public TreeSet<Integer> components() {
        TreeSet<Integer> set = new TreeSet<>();
        for(int i : par)
            set.add(i);
        return set;
    }
}
