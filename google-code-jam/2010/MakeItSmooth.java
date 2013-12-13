import java.io.File;
import java.util.Scanner;

//Dynamic programming problem, Round 1A 2010

public class MakeItSmooth {
    
    public static final int AMOUNT_OF_COLORS = 256;
    
    private int [] v;
    private int [][] minCost;
    private int insertCost;
    private int deleteCost;
    private int maxDif;
    
    public MakeItSmooth(int [] v, int insertCost, int deleteCost, int maxDif){
        this.v = v;
        this.minCost = new int[v.length+1][AMOUNT_OF_COLORS];
        this.insertCost = insertCost;
        this.deleteCost = deleteCost;
        this.maxDif = maxDif;
    }
    
    public int solve(){
        if(v.length <= 1){
            return 0;
        }
        
        for(int j = 0; j < AMOUNT_OF_COLORS; ++j){
            minCost[0][j] = Math.min(this.cost(v[0], j), this.deleteCost);
        }
        int insCost;
        int modCost;
        for(int i = 1; i < v.length; ++i){
            for(int j = 0; j < AMOUNT_OF_COLORS; ++j){
                minCost[i][j] = Math.min(Integer.MAX_VALUE, minCost[i-1][j] + this.deleteCost);
                for(int k = 0; k < AMOUNT_OF_COLORS; ++k){
                    insCost = this.costOfInserting(k, j);
                    modCost = Integer.MAX_VALUE;
                    if(this.cost(k, j) <= maxDif){
                        modCost = this.cost(v[i], j);
                    }
                    else if(insCost != Integer.MAX_VALUE){
                        modCost = this.cost(v[i], j) + insCost;
                    }
                    if(modCost != Integer.MAX_VALUE){
                        minCost[i][j] = Math.min(minCost[i][j], modCost + minCost[i-1][k]);
                    }
                }
            }
        }
        
        int cost = Integer.MAX_VALUE;
        for(int j = 0; j < AMOUNT_OF_COLORS; ++j){
            if(minCost[v.length-1][j] < cost){
                cost = minCost[v.length-1][j];
            }
        }
        return cost;
    }
    
    private int costOfInserting(int from, int to){        
        if(this.cost(from, to) == 0){
            return 0;
        }
        if(this.maxDif == 0){
            return Integer.MAX_VALUE;
        }
        return ( (this.cost(from, to)-1) / this.maxDif) * this.insertCost;
    }
    
    
    private int cost(int i, int j){
        return Math.abs(i-j);
    }
    
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        int cases = in.nextInt();
        int delCost, insCost, dif;
        int size = 0;
        int caseNumber = 1;
        int [] v = null;
        while(cases > 0){
            
            delCost = in.nextInt();
            insCost = in.nextInt();
            dif = in.nextInt();
            size = in.nextInt();
            v = new int[size];
            for(int i = 0; i < size; ++i){
                v[i] = in.nextInt();
            }
            int solution = (new MakeItSmooth(v, insCost, delCost, dif).solve());
            System.out.printf("Case #%d: %d\n", caseNumber++, solution);
            --cases;
        }
    }
    
    
}
