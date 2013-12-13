import java.util.Scanner;
 
public class FallingDiamonds {
    
    private int diamonds, x, y;
    
    public FallingDiamonds(int diamonds, int x, int y){
        this.diamonds = diamonds;
        this.x = Math.abs(x);
        this.y = y;
    }
        
    //how many diamonds do we need in order to know for sure that
    //a diamond will land on (x,y)?
    public int diamondsRequiredForOneProbability(int x, int y){
        if(x == 0){
            return ( (y+1)*(y+2) ) / 2; //we know how to calculate top of pyramids
        }
        return diamondsRequiredForOneProbability(0, y+x) - x; //move up to the top of the pyramid
    }
    
    //how many diamonds do we need to have a probability greater than 0
    //of a diamond landing in (x,y)?
    public int diamondsRequiredForGreaterThanZeroProbability(int x, int y){
        if(x == 0){
            return diamondsRequiredForOneProbability(x, y);
        }
        else{
            return diamondsRequiredForOneProbability(x, y) - (x+y); 
        }
    }
    
    public double solve(){
        int diamondsForOne = diamondsRequiredForOneProbability(x, y);
        int diamondsForGreaterThanZero = diamondsRequiredForGreaterThanZeroProbability(x, y);
        if(diamonds >= diamondsForOne){ 
            return 1.0; //there are too much diamonds, a diamond will surely land here
        }
        else if(diamonds < diamondsForGreaterThanZero){
            return 0.0; //there are not enough diamonds, there's no way that a diamond will land here
        }
        else{
            //we don't know what will happen! Use binomial sum
            diamondsForGreaterThanZero = diamondsRequiredForGreaterThanZeroProbability(x+y, 0);
            return 1.0 - binomialSum(0.5, diamonds - diamondsForGreaterThanZero+1, y+1);
        }
    }
    
    // *** math methods *** 
  
    //probability of getting exactly k successes on n trials. A success has a probability of p
    public double binomial(double p, int n, int k){
        return (double)combination(n,k) * Math.pow(p, k) * Math.pow(1-p, n-k);
    }
    
    //probability of getting k or more successes on n trials. A success has a probability of p
    public double binomialSum(double p, int n, int k){
        double sum = 0.0;
        for(int i = 0; i < k; ++i){
            sum += binomial(p,n,i);
        }
        return sum;
    }
    
    //in how many ways can we choose k objects out of n (order does not matter)
    public long combination(long n, long k){
        if( k == 0 || n == k){ return 1; } //avoid tough work
        return factorial(k+1,n) / factorial(n-k);
    }
    
    //calculates n!
    public long factorial(long n){
        return factorial(1, n);
    }
    
   //calculates to! / (from-1)!
    public long factorial(long from, long to){
        if(from > to){
            return 1;
        }
        long result = from;
        for(long i = from+1; i <= to; ++i){
            result *= i;
        }
        return result;
    }
    
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        int cases = in.nextInt();
        int currentCase = 0;
        while(cases > 0){
            FallingDiamonds diamonds = new FallingDiamonds(in.nextInt(),in.nextInt(),in.nextInt());
            System.out.printf("Case #%d: %f\n", ++currentCase, diamonds.solve() );
            --cases;
        }
    }
}