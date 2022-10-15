package com.puissance4;

import java.util.ArrayList;

public class Grid{

    public int height;
    public int width;
    public int alingToWin;
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

    public boolean checkColumn(int column, String symbole){
        int columnSuite = 0;
        for (int i = 0;i<height;i++){
            if (grid.get(i).get(column).equals(symbole))columnSuite ++;
            else columnSuite = 0;
        }
        if (columnSuite >= alingToWin)return true;
        return false;
    }

    public boolean checkLine(int line,String symbole){
        int lineSuite = 0;
        for (int i = 0; i<width;i++){
            if (grid.get(line).get(i).equals(symbole))lineSuite++;
            else lineSuite = 0;
            if (lineSuite >= alingToWin)return true;
        }
        return false;
    }
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

    public boolean drowGame(){
        for (int i = 0; i<this.height;i++){
            for(int j = 0; j<this.width;j++){
                if (grid.get(i).get(j) == " ")return false;
            }
        }
        return true;
    }
}
