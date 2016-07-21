import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int a, b, n, current;
        
        if(t<0 || t>500){            
            System.out.println("t out of bounds!");
        }
        
        for(int i = 0; i<t; i++){
            a = sc.nextInt();
            
            if(a<0 || a>50){
                System.out.println("a value is out of bounds!");
            }
            
            b = sc.nextInt();
            
            if(b<0 || b>50){
                System.out.println("b value is out of bounds!");
            }
            
            n = sc.nextInt();
            
            if(n<1 || n>15){
                System.out.println("n value is out of bounds!");
            }


            current = a;
            for(int j=0; j<n; j++){                                 
                current += b<<j;
                System.out.print(current + " ");       
            }
            System.out.println();
        }
    }
    
}