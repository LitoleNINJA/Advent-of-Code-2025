package day2;

import common.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class GiftShop
{
    public static PrintStream out = utils.initializeOutputStream();
    public static void main(String[] args)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(utils.INPUT_FILENAME))){
            ArrayList<ArrayList<Long>> list = new ArrayList<>(); 
            String input = br.readLine();
            
            Arrays.stream(input.split(","))
                .forEach((element) -> {
                    String[] ranges = element.split("-");
                    ArrayList<Long> temp = new ArrayList<>();
                    temp.add(Long.parseLong(ranges[0]));
                    temp.add(Long.parseLong(ranges[1]));
                    list.add(temp);
                });

            long sum = sumOfInvalidIDs(list);
            System.out.printf("\nAnswer : %d\n", sum);
            
        } catch(Exception e) {
            System.out.printf("\nException occured : %s\n", e.getMessage());
        }
       
    }

    private static long sumOfInvalidIDs(ArrayList<ArrayList<Long>> list) {
        AtomicLong sum = new AtomicLong(0);
        list.forEach(range -> {
            long start = range.get(0);
            long end = range.get(1);
            for(long i = start;i<=end;i++)
            {
                if(isInvalid(i))
                {
                    out.printf("Invalid Num : %d\n", i);
                    sum.addAndGet(i);
                }
            }
        });
        return sum.get();
    }

    private static boolean isInvalid(long num) {
        String s = Long.toString(num);
        
        // for each possible prefix, check if the string is a repetition of this prefix ;)
        for(int i = 1; i < s.length(); i++) {
            String cur = s.substring(0, i);
            // Old Code (boring, not smarty enough ;)
            // repeat this cur substring to make original
            // if(s.length() % cur.length() == 0) {
            //     int k = s.length() / cur.length();

            //     String new_S = cur.repeat(k); 
            //     if(s == new_S) 
            //         return true;
            // }

            // thoda zyada hero ban leta hu
            if (s.length() % cur.length() == 0 && s.replace(cur, "").isEmpty()) {
                return true;
            }
        }
        return false;
    }
}