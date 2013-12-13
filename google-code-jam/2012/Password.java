import java.io.File;
import java.util.Scanner;

public class Password {
    
    private static int typed;
    private static int length;
    private static double [] pCorrect;
    
    private static double [] product;
    private static double [] mistake; //made a mistake before char i
    private static double [] allRight; //all right before char i    
    
    private static double solve() {
        product[0] = pCorrect[0];
        for(int i = 1; i <= typed; ++i){
            product[i] = pCorrect[i] * product[i-1];
        }        
        
        mistake[0] = 0.0;
        allRight[0] = 1.0;
        for(int i = 1; i <= typed; ++i){
            allRight[i] = product[i-1];
            mistake[i] = 1.0 - allRight[i];
        }

        int missing = length-typed;
        double min = Double.MAX_VALUE;
        double temp = 0.0;
        for(int i = 0; i <= typed; ++i){
            //press backspace i times
            temp = (i + i + missing + 1) * allRight[typed-i] /*i backspaces, i types, rest of chars, enter */
                    + (i + i + missing + 1 + length + 1) * mistake[typed-i]; /* i backspaces, i types, rest of chars, enter, whole password */
            min = Math.min(temp, min);
        }
        min = Math.min(min, length+2); //give up
        return min;
    }
    
    
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        int caseNumber = 1;
        int cases = in.nextInt();
        while(cases > 0){
            typed = in.nextInt(); length = in.nextInt();
            pCorrect = new double[typed+1];
            mistake = new double[typed+1];
            allRight = new double[typed+1];
            product = new double[typed+1];
            for(int i = 0; i < typed; ++i){
                pCorrect[i] = in.nextDouble();
            }
            System.out.printf("Case #%d: %f\n", caseNumber++, solve());
            
            --cases;
        }
    }
}
