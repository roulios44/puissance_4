import java.util.ArrayList;

public class Grid{

    public int height;
    public int width;
    public ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();

    Grid(Integer height, Integer width){
        this.height = height;
        this.width = width;
        generateGrid();
    }
    private void generateGrid(){
        try{
            for (int i = 0; i<this.height;i++){
                ArrayList<String> line = new ArrayList<String>();
                for(int j = 0; j<this.width;j++){
                    line.add(" ");
                }
                this.grid.add(line);
            }
            for (int k=0;k<grid.size();k++)System.out.println(grid.get(k));
        } catch (Exception e){
            System.out.println("error in grid generation "+ e);
        }
    }
}