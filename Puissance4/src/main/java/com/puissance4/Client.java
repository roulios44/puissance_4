package com.puissance4;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client extends Game {

    SocketChannel socket;
    Player clientPlayer = new Player(" ", " ");
    private boolean haveclientPlayer = false;
    private boolean nbPlayerReceived = false;
    private int numberOfPlayers = 2;
    private boolean gameEnd =false;
    private String playedSymbole;
    private String ip;

    Client(int numberOfPlayers, String ip){
        this.ip = ip;
        this.numberOfPlayers = numberOfPlayers;
    }
    public void start(){
        try{
            try{
                socket = SocketChannel.open();
                socket.connect(new InetSocketAddress(ip.trim(), 4004));
            } catch (ConnectException eConnect){
                System.err.println("Error into serveur connection , check if the IP is good");
            }
            // first listen is for getting the number of player into the game
            Listen();
            // Second listen is to get the player info ( symbole )
            Listen();
            clientGame();
            socket.close();
            return;
        } catch (IOException e){
            System.err.println(e.toString());
        }
    }

    public void send(String message){
        try{
            ByteBuffer bytes = ByteBuffer.wrap(message.getBytes("UTF-16"));
            while(bytes.hasRemaining()){
                socket.write(bytes);
            } 
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public void close(){
        try {
            socket.close();
        }
        catch(IOException e){
            System.err.println(e.toString());
        }
    }

    public String Listen(){
        ByteBuffer bytes = ByteBuffer.allocate(1024);
        while(true){
            bytes.clear();
            try {
                int bytesRead = socket.read(bytes);
                if(bytesRead <= 0){
                    socket.close();
                    return "";
                }
                String message = new String(bytes.array(),"UTF-16");
                if (nbPlayerReceived == false){
                    numberOfPlayers = Integer.parseInt(String.valueOf(message.trim().charAt(8)));
                    nbPlayerReceived = true;
                } else if (haveclientPlayer == false) {
                    getPlayer(message);
                    haveclientPlayer = true;
                }
                return message;
            }catch (IOException e){
                close();
                return "";
            }            
        }
    }

    private void getPlayer(String message){
        for (PlayerSymbole symbole : PlayerSymbole.values()) {
            if (message.trim().equals(symbole.toString())){
                clientPlayer = new Player("Player " + symbole.toString(), symbole.toString());
            }
        }
    }

    private void clientGame(){
        this.currentPlayer = clientPlayer;
        chooseGrid();
        while(!gameEnd){
            grid.printGrid();
            String message = Listen();
            System.out.println(message);
            if (message.trim().equals("Your Turn")){
                System.out.println("You are : " + clientPlayer.symbole);
                Character place = askPlace(grid);
                playedSymbole = clientPlayer.symbole;
                placeIntoGrid(grid, place, playedSymbole);
                String toSend = "Turn " + playedSymbole + " "  + place;
                send(toSend);
                if(gameEnd)clientPlayer.haveWin = true;
            } else {
                Character toPlace = message.charAt(7);
                String symboleOtherPlayer = String.valueOf(message.charAt(5));
                playedSymbole = symboleOtherPlayer;
                placeIntoGrid(grid, toPlace,playedSymbole);
            }
            if(grid.drowGame()){
                send("DROW");
                System.out.println("This is a drow game, nobody win");
                return;
            }
        }
        if(clientPlayer.haveWin)System.out.println("You win");
        else System.out.println("You loose");
        System.out.println("Game end");
        send("STOP");
    }

    private void chooseGrid(){
        switch(numberOfPlayers){
            case 2:
                grid = new Grid(6,8 , alingToWin);
                break;
            case 3:
                grid = new Grid(12,10,alingToWin);
                break;
            default :
                grid = new Grid(6,8 , alingToWin);
                break;
        }
    }

    @Override
    protected boolean winCondition(int line, int column){
        if (grid.checkColumn(column, playedSymbole)) gameEnd = true;
        if (grid.checkLine(line, playedSymbole)) gameEnd = true;
        if (grid.checkLeftToRight(line, column, playedSymbole)) gameEnd = true;
        if (grid.checkRightToLeft(line, column, playedSymbole)) gameEnd = true;
        return false;
    }
}
