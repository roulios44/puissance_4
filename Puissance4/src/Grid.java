import java.util.ArrayList;
import java.util.List;

public class Grid {
    private static int column = 8;
    private static int second = 6;
    private static String blanc = "x";

    static ArrayList<ArrayList <String>> List2 = new ArrayList<ArrayList<String>>();

    public static ArrayList<ArrayList<String>> getGrid(){

      
        
        
        for (int i = 0 ; i < column; i++){
            ArrayList<String> List = new ArrayList<String>();
            for(int j = 0 ; j < second ; j++){
                List.add(blanc);
            }
            List2.add(List);

        }
        return List2;
    }
    public static void DisplayList(){
        for (int i = 0 ; i < second; i++){
            System.out.print("#");
            for(int j = 0 ; j < column ; j++){
                System.out.print( List2.get(j).get(i));
    }
    System.out.print("#\n");
        }
        
}}


   

    
   

