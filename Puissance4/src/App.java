import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        int line =6;
        int tour = 0 ;
        boolean win = true;
        Scanner sc = new Scanner(System.in);
        
        ArrayList<ArrayList<String>> grid = Grid.getGrid();
        //for( int i=0 ; i < line ;i++){
         //   System.out.println(grid); 
        //}

        Grid.DisplayList();

        while (!win){
            if (tour %2 == 0){
                System.out.println("Tour du joueur 1 : Quel colonne choisissez vous ? [0-8]");
                int reponse = sc.nextInt();
                tour = tour+1;
            }else{
                System.out.println("Tour du joueur 2 :  Quel colonne choisissez vous ? [0-8]");
                int reponse = sc.nextInt();
                tour = tour+1;
            }
        }
                   
        }

        
    }

