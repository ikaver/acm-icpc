import java.util.Scanner;

//Round 1A, 2010

public class Rotate {
    
    public static final char RED = 'R';
    public static final char BLACK = 'B';
    public static final char EMPTY = '.';
    
    private static int [] DIR_ROW = new int[]{-1,0,-1,-1};
    private static int [] DIR_COL = new int[]{0,1,1, -1};
    
    private char [][] m;
    private char [][] temp;
    private int goal;
    
    public Rotate(char [][] m, char [][] temp, int goal){
        this.m = m;
        this.temp = temp;
        copy(m,temp);
        this.goal = goal;
    }
    
    public String solve(){
        this.rotateLeft();
        this.applyGravity();
        boolean foundRed = this.search(RED);
        boolean foundBlack = this.search(BLACK);
        
        if(foundRed && foundBlack){
            return "Both";
        }
        else if(foundRed){
            return "Red";
        }
        else if(foundBlack){
            return "Blue";
        }
        else{
           return "Neither";
        }

    }

    private void rotateLeft(){
        for(int i = 0; i < m.length; ++i){
            for(int j = 0; j < m.length; ++j){
                this.m[j][m.length - 1 - i] = this.temp[i][j];
            }
        }
    }
    
    private void applyGravity(){
        int currentI = 0;
        for(int j = 0; j < m.length; ++j){
            for(int i = m.length-2; i >= 0; --i){
                if(m[i][j] != EMPTY){
                    currentI = i;
                    for(int k = i+1; k < m.length; ++k){
                        if(m[k][j] != EMPTY){ break; }
                        else{         
                            swap(k,j,currentI,j); 
                            currentI = k;
                        } 
                    }
                }
            }
        }
    }
    
    private boolean search(char color){
        for(int i = 0; i < m.length; ++i){
            for(int j = 0; j < m.length; ++j){
                for(int k = 0; k < DIR_ROW.length; ++k){
                    if(search(i,j,color,k,goal)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean search(int f, int c, char color, int dir, int missing){
        if(missing <= 0){
            return true;
        }
        if(!validPosition(m, f, c)){
            return false;
        }
        if(m[f][c] == color){
             return search(f+DIR_ROW[dir],c+DIR_COL[dir],color,dir,missing-1);
        }
        else{
            return false;
        }
    }
    
    private void swap(int f1, int c1, int f2, int c2) {
        char temp = this.m[f1][c1];
        this.m[f1][c1] = this.m[f2][c2];
        this.m[f2][c2] = temp;
    }

    private static boolean validPosition(char [][] m, int f, int c){
        return f >= 0 && c >= 0 && f < m.length && c < m[f].length;
    }

    public static void copy(char [][] from, char [][] to){
        for(int i = 0; i < from.length; ++i){
            for(int j = 0; j < from[i].length; ++j){
                to[i][j] = from[i][j];
            }
        }
    }
    
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        int cases = in.nextInt();
        int caseNumber = 1;
        while(cases > 0){
            int dim = in.nextInt();
            int goal = in.nextInt();
            in.nextLine();
            String str;
            char [][] m = new char[dim][dim];
            for(int i = 0; i < dim; ++i){
                str = in.nextLine();
                for(int j = 0; j < dim; ++j){
                    m[i][j] = str.charAt(j);
                }
            }
            Rotate rotate = new Rotate(m, new char[dim][dim], goal);
            String out = rotate.solve();
            --cases;
            System.out.printf("Case #%d: %s\n", caseNumber++, out);
        }
    }
}
