import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;


///
/// 
/// This code is owned and composed by Anjuman Hasan (TM)
/// All rights reserved. Trespassers will be exucuted
/// 
// Java streams have a :
// 1. SOURCE - (e.g., Collection, Array, I/O channel, generator function) 
//
// 2. INTERMEDIATE OPERATIONS (Lazy - return new stream)
//    - filter(Predicate)        : select elements matching condition
//    - map(Function)            : transform elements 1:1
//    - flatMap(Function)        : transform elements 1:many, flatten
//    - distinct()               : remove duplicates
//    - sorted()                 : order elements (natural order)
//    - sorted(Comparator)       : order with custom comparator
//    - limit(n)                 : take first n elements
//    - skip(n)                  : skip first n elements
//    - peek(Consumer)           : inspect elements (for debugging)
//
// 3. TERMINAL OPERATIONS (Eager - trigger execution)
//    - forEach(Consumer)        : perform action on each element
//    - collect(Collector)       : gather into List/Set/Map
//    - reduce(BinaryOperator)   : combine into single result
//    - count()                  : count elements
//    - anyMatch(Predicate)      : test if any match
//    - allMatch(Predicate)      : test if all match
//    - noneMatch(Predicate)     : test if none match
//    - findFirst()              : get first element as Optional
//    - findAny()                : get any element as Optional
//    - min/max(Comparator)      : find min/max element
//    - toArray()                : convert to array
// 1212121212
// 12121212

public class day_2
{
     public static String INPUT_FILENAME = "input.txt";
    public static String OUTPUT_FILENAME = "output.txt";
    
    public static void main(String[] args)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(INPUT_FILENAME))){
            PrintStream out = new PrintStream(Files.newOutputStream(Paths.get(OUTPUT_FILENAME)));
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

            long sum = sumOfInvalidIDs(list, out);
            System.out.printf("Answer : %d\n", sum);
            
        } catch(Exception e) {
            System.out.printf("Exception occured : %s", e);
        }
       
    }

    private static long sumOfInvalidIDs(ArrayList<ArrayList<Long>> list, PrintStream out)
    {
        AtomicLong sum = new AtomicLong(0);
        list.forEach(range -> {
            long start = range.get(0);
            long end = range.get(1);
            for(long i = start;i<=end;i++)
            {
                if(isInvalid(i, out))
                {
                    out.printf("Invalid Num : %d\n", i);
                    sum.addAndGet(i);
                }
            }
        });
        return sum.get();
    }

   private static boolean isInvalid(long num, PrintStream out)
    {
        String s = Long.toString(num);
        if(s.length()%2==1)
            return false;

        long first = Long.parseLong(s.substring(0,s.length()/2));
        long second =  Long.parseLong(s.substring(s.length()/2, s.length()));
        return first==second;
    }
}