import java.util.ArrayList;

public class Grid {
   public ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();
    private int height;
    private int width;

   Grid(int height, int width) {
    this.height = height;
    this.width = width;
    generateGrid();
   }

   private void generateGrid() {
      for(int i = 0; i <= height; i++ ) {
         ArrayList<String> row = new ArrayList<String>();
         for(int j = 0 ; j <= width; j++) {  
               row.add("=");
         }  
         grid.add(row);
      }
   }
   
}