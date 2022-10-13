package com.puissance4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game{
    protected Integer numberOfPlayers;
    protected Grid grid;
    protected ArrayList<Player> allPlayers = new ArrayList<Player>();
    protected Player currentPlayer;
    private int alingToWin = 4;
    public void menu()throws Exception{
        System.out.println("Hello Welcome to our Power 4 game !");
        Integer choice = 0;
        choice = Integer.parseInt(askInfo("1) Start Game\n2) Three players game \n3) Server Local play (2Player) \n5) Leave"));
        switch(choice){
            case 1:
                classicGame();
                break;
            case 2:
                threeGame();
                break;
            case 3:
                LocalHostMode();
                break;
            case 5:
                System.out.print("Bye, see you soon !");
                break;
            default:
                menu();
        }
    }
    protected void classicGame(){
        numberOfPlayers = 2;
        grid = new Grid(6,8);
        generatePlayers();
        lauchGame();
    }
    private void threeGame(){
        numberOfPlayers = 3;
        grid = new Grid(12,10);
        generatePlayers();
        lauchGame();
    }
    private void placeIntoGrid(Character choice){
        int column = choice - 'a';
        for (int i = grid.height-1;i>-1;i--){
            String place = grid.grid.get(i).get(column);
            if (place == " "){
                grid.grid.get(i).set(column,currentPlayer.symbole);
                System.out.println("placing symbole");
                winCondition(i, column);
                break;
            }
        }
    }
    private void lauchGame(){
        grid.printGrid();
        while(!checkIfWinner()){
            for (Player player : allPlayers) {
                currentPlayer = player;
                System.out.println(player.name + "'s turn");
                grid.printGrid();
                Character choice = askPlace();
                placeIntoGrid(choice);
                if (player.haveWin){
                    System.out.println(player.name + " have win");
                    break;
                }
            }
        }
    }
    private boolean checkIfWinner(){
        try{
            for (int i=0;i<allPlayers.size();i++) {
                if (allPlayers.get(i).haveWin == true) return true;
            }
            return false;
        } catch (NullPointerException e){
            System.out.println("error checkIfWinner "+ e);
        }
        return false;
    }

    private boolean winCondition(int line, int column){
        if (checkColumn(column)) currentPlayer.haveWin =  true;
        if (checkLine(line)) currentPlayer.haveWin = true;
        if (checkLeftToRight(line, column)) currentPlayer.haveWin = true;
        if (checkRightToLeft(line, column)) currentPlayer.haveWin = true;
        return false;
    }
    private boolean checkColumn(int column){
        int columnSuite = 0;
        for (int i = 0;i<grid.height;i++){
            if (grid.grid.get(i).get(column) == currentPlayer.symbole)columnSuite ++;
            else columnSuite = 0;
        }
        if (columnSuite >= alingToWin)return true;
        return false;
    }

    private boolean checkLine(int line){
        int lineSuite = 0;
        for (int i = 0; i<grid.width;i++){
            if (grid.grid.get(line).get(i) == currentPlayer.symbole)lineSuite++;
            else lineSuite = 0;
            if (lineSuite >= alingToWin)return true;
        }
        return false;
    }
    private boolean checkLeftToRight(int line, int column){
        int diagonaleSuite = 0;
        while(line != grid.height -1){
            if (column == 0)break;
            if (line == grid.height-1)break;
            line ++;
            column --;
        }
        if (grid.width - column >= alingToWin){
            for (int i =0;i<grid.width - column;i++){
                if (line <= 0)break;
                if(grid.grid.get(line).get(column) == currentPlayer.symbole)diagonaleSuite++;
                else diagonaleSuite = 0;
                if (diagonaleSuite >= alingToWin)return true;
                line --;
                column++;
            }
        }
        return false;
    }
    private boolean checkRightToLeft(int line, int column){
        int diagonaleSuite = 0;
        while(line != grid.height-1){
            if (column == grid.width)break;
            if (line == grid.height-1)break;
            line ++;
            column --;
        }
        if (column >= alingToWin){
            for (int i =0;i<column;i++){
                if (line <= 0)break;
                if(grid.grid.get(line).get(column) == currentPlayer.symbole)diagonaleSuite++;
                else diagonaleSuite = 0;
                if (diagonaleSuite >= alingToWin)return true;
                line --;
                column--;
            }
        }
        return false;
    }
    private void LocalHostMode(){
        Client test = new Client();
    }


    
    private Character askPlace(){
        String playerChoice = askInfo("Which column ?");
        try{
            char[] charChoice = playerChoice.toCharArray();
            if (charChoice.length != 1 || charChoice[0]> 'a' + grid.height-1){
                System.out.println("Enter a valid position (only one character are needed)");
                askPlace();
            }
            else return charChoice[0];
        } catch (Error e){
            System.out.println("Erroir in player choice " + e);
            askPlace();
        }
        return 'a';
    }
    protected void generatePlayers(){
        try{
            for (int i=0;i<numberOfPlayers;i++){
                allPlayers.add(new Player(askInfo("Player " + (i+1) + " name"), askInfo("Player "+(i+1)+" Symbol"))) ;
            }
        } catch (Error e){
            System.out.println("Error creating players "+ e);
        }
    }
    public String askInfo(String inputSentence){
        try{
            java.io.InputStreamReader byteInfo = new InputStreamReader(System.in);
            java.io.BufferedReader StringInfo = new BufferedReader(byteInfo);
            System.out.println(inputSentence);
            try{
                return StringInfo.readLine();
            } catch (IOException e){
                return ("AskInfo:   Error IOExpection " + e);
            }
        }
        catch(Error e){
            return("Error into AskInfo func: " + e);
        }
    }
}
