import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class MinimumScalarProduct {
    
    public static final int MAX_SIZE = 10000;
    public static long [] first = new long[MAX_SIZE];
    public static long [] second = new long[MAX_SIZE];

    public static long minimumScalarProduct(long [] a, long [] b, int from, int to) {
       long minimum = 0;
       
       Arrays.sort(a,from, to);
       Arrays.sort(b,from, to);
       for(int i = from; i < to; ++i) {
           minimum += a[i] * b[to-1-i];
       }
       
       return minimum;
    }
    
    public static void main(String [] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        int currentCase = 1;
        int cases = scanner.nextInt();
        while (cases > 0) {
            int size = scanner.nextInt();
            for(int i = 0; i < size; ++i){
                first[i] = scanner.nextLong();
            }
            for(int i = 0 ; i < size; ++i){
                second[i] = scanner.nextLong();
            }
            long minimum = (minimumScalarProduct(first, second, 0, size));
            System.out.println("Case #" + currentCase 
                        + ": " + minimum);

            ++currentCase;
            --cases;
        }
        scanner.close();
        
    }
}
