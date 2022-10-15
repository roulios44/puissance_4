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
    protected int alingToWin = 4;
    public void menu(){
        System.out.println("Hello Welcome to our Power 4 game !");
        Integer choice = 0;
        try{
            choice = Integer.parseInt(askInfo("1) Start Game\n2) Three players game \n3) Server Local play (2Player) \n5) Leave"));
        } catch (NumberFormatException e){
            System.out.println("Please enter a valide character");
            this.menu();
        }
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
        grid = new Grid(6,8,alingToWin);
        generatePlayers();
        lauchGame();
    }
    protected void threeGame(){
        numberOfPlayers = 3;
        grid = new Grid(12,10,alingToWin);
        generatePlayers();
        lauchGame();
    }
    protected void placeIntoGrid(Grid grid,Character choice, String symbole){
        int column = choice - 'a';
        for (int i = grid.height-1;i>-1;i--){
            String place = grid.grid.get(i).get(column);
            if (place == " "){
                grid.grid.get(i).set(column,symbole);
                winCondition(i, column);
                break;
            }
        }
    }
    protected void lauchGame(){
        grid.printGrid();
        while(!checkIfWinner()){
            for (Player player : allPlayers) {
                currentPlayer = player;
                System.out.println(player.name + "'s turn");
                grid.printGrid();
                Character choice = askPlace(grid);
                placeIntoGrid(grid, choice, currentPlayer.symbole);
                if (grid.drowGame()){
                    System.out.println("Grid is full, nobody have win");
                    return;
                }
                if (player.haveWin){
                    System.out.println(player.name + " have win");
                    break;
                }
            }
        }
    }
    protected boolean checkIfWinner(){
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

    protected boolean winCondition(int line, int column){
        if (grid.checkColumn(column, currentPlayer.symbole)) currentPlayer.haveWin =  true;
        if (grid.checkLine(line, currentPlayer.symbole)) currentPlayer.haveWin = true;
        if (grid.checkLeftToRight(line, column, currentPlayer.symbole)) currentPlayer.haveWin = true;
        if (grid.checkRightToLeft(line, column, currentPlayer.symbole)) currentPlayer.haveWin = true;
        return false;
    }
    
    private void LocalHostMode(){
        try{
            if (ask1true2false("1) Host game\n2) Join Game")){
                // Create server and Connect client
                boolean choice = ask1true2false("1) 2 Players\n2) 3 Players");
                if (choice)numberOfPlayers = 2;
                else numberOfPlayers = 3;
                Server server = new Server(numberOfPlayers);
                Thread serverThread = new Thread(server);
                serverThread.start();
                Client clienthost = new Client(numberOfPlayers, "localhost");
                clienthost.start();
            } else {
                // Connect Client to the Ip
                String IP = askInfo("Enter Ip address");
                Client client = new Client(numberOfPlayers = 2, IP);
                numberOfPlayers = 2;
                client.start();
            }
        } catch (NullPointerException e){
            System.out.println("error client creation " + e.toString());
        }
    }

    private boolean ask1true2false(String sentence){
        String choice = askInfo(sentence);
        switch(choice){
            case "1":
                return true;
            case "2":
                return false;
            default:
                System.out.println("Enter a valid proposition");
                return(ask1true2false(sentence));
        }
    }

    protected Character askPlace(Grid grid){
        String playerChoice = askInfo("Which column ?");
        try{
            char[] charChoice = playerChoice.toCharArray();
            if (charChoice.length != 1 || charChoice[0]> 'a' + grid.height+1 || charChoice[0] < 'a'){
                    System.out.println("Enter a valid position (only one character are needed)");
                    askPlace(grid);
            }
            else return charChoice[0];
        } catch (Error e){
            System.out.println("Error in player choice " + e);
            askPlace(grid);
        }
        return 'a';
    }
    protected void generatePlayers(){
        try{
            boolean alreadyTake = false;
            for (int i=0;i<numberOfPlayers;i++){
                String name = askInfo("Player " + (i+1) + " name ?");
                String symbole = askInfo("Player " + (i+1) + " symbole ?");
                for (int j=0;j<i;j++){
                    if (symbole != allPlayers.get(j).symbole){
                        alreadyTake = true;
                    }
                    if (alreadyTake){
                        while(alreadyTake){
                            System.out.println("Symbole already taken, please choose another one");
                            symbole = askInfo("Player " + (i+1) + " symbole ?");
                            boolean lookSymboleSame = false;
                            for (Player player : allPlayers) {
                                if (player.symbole == symbole) lookSymboleSame = true;
                            }
                            if(!lookSymboleSame) alreadyTake = false;
                        }
                    }
                }
                allPlayers.add(new Player(name,symbole));   
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
                String response = StringInfo.readLine();
                if (response.length() < 1){
                    System.out.println("Enter at least 1 character");
                    return askInfo(inputSentence);
                }else {
                    return response;
                }
            } catch (IOException e){
                return ("AskInfo:   Error IOExpection " + e);
            }
        }
        catch(Error e){
            return("Error into AskInfo func: " + e);
        }
    }
}
