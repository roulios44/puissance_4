package main.java.com.puissance4;

import java.util.ArrayList;
/**
 * Class of the grid, where piece of player will be display
 */
public class Grid{
    /**
     * height of the gird
     */
    public int height;
    /**
     * width of the grid
     */
    public int width;
    /**
     * number of piece who must be align to the game end
     */
    public int alingToWin;
    /**
     * the grid (double ArrayList)
     */
    public ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();

    Grid(Integer height, Integer width, Integer alingToWin){
        this.height = height;
        this.width = width;
        this.alingToWin = alingToWin;
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
        } catch (Exception e){
            System.err.println("error in grid generation "+ e);
        }
    }
    /**
     * Func to print the grid in the terminal
     */
    public void printGrid(){
        for (ArrayList<String> line : grid) {
            String lineContent = "#";
            for (String content : line) {
                lineContent += content;
            }
            lineContent += "#";
            System.out.println(lineContent);
        }
        String bottomLimit = " ";
        String coordIndicator = " ";
        for (Integer i = 0;i<grid.get(0).size();i++){
            coordIndicator += Character.toString(('a') + i);
            bottomLimit += "#";
        }
        System.out.println(bottomLimit);
        System.out.println(coordIndicator);
    }
    /**
     * funct to check if the column where the piece has been place make the player win
     * @param column column of the placed piece
     * @param symbole symbole of the currentPlayer
     * @return true if they are a winner false is not 
     */
    public boolean checkColumn(int column, String symbole){
        int columnSuite = 0;
        for (int i = 0;i<height;i++){
            if (grid.get(i).get(column).equals(symbole))columnSuite ++;
            else columnSuite = 0;
        }
        if (columnSuite >= alingToWin)return true;
        return false;
    }
    /**
     * funct to check if the Line where the piece has been place make the player win
     * @param line line where the player place his piece
     * @param symbole symbole of the currentPlayer
     * @return true if they are a winner false is not 
     */
    public boolean checkLine(int line,String symbole){
        int lineSuite = 0;
        for (int i = 0; i<width;i++){
            if (grid.get(line).get(i).equals(symbole))lineSuite++;
            else lineSuite = 0;
            if (lineSuite >= alingToWin)return true;
        }
        return false;
    }
    /**
     * func to check if the diagonal left to right make the player win
     * @param line line where the player place his piece
     * @param column column where the player place his piece
     * @param symbole symbole of the currentPlayer
     * @return true if they are a winner false is not 
     */
    public boolean checkLeftToRight(int line, int column,String symbole){
        int diagonaleSuite = 0;
        while(line != height -1){
            if (column == 0)break;
            if (line == height-1)break;
            line ++;
            column --;
        }
        if (width - column >= alingToWin){
            for (int i =0;i<width - column;i++){
                if (line <= 0)break;
                if(grid.get(line).get(column).equals(symbole))diagonaleSuite++;
                else diagonaleSuite = 0;
                if (diagonaleSuite >= alingToWin)return true;
                line --;
                column++;
            }
        }
        return false;
    }
    /**
     * func to check if the diagonal right to left make the player win
     * @param line line where the player place his piece
     * @param column column where the player place his piece
     * @param symbole symbole of the currentPlayer
     * @return true if they are a winner false is not 
     */
    public boolean checkRightToLeft(int line, int column, String symbole){
        int diagonaleSuite = 0;
        while(line != height-1){
            if (column == width)break;
            if (line == height-1)break;
            line ++;
            column --;
        }
        if (column >= alingToWin){
            for (int i =0;i<column;i++){
                if (line <= 0)break;
                if(grid.get(line).get(column).equals(symbole))diagonaleSuite++;
                else diagonaleSuite = 0;
                if (diagonaleSuite >= alingToWin)return true;
                line --;
                column--;
            }
        }
        return false;
    }
    /**
     * Func to check if the game is drow (no winner, gird is full)
     * @return true if is a drow game , false if not 
     */
    public boolean drowGame(){
        for (int i = 0; i<this.height;i++){
            for(int j = 0; j<this.width;j++){
                if (grid.get(i).get(j) == " ")return false;
            }
        }
        return true;
    }
}
