package com.puissance4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Client extends Game {

    SocketChannel socket;
    Player clientPlayer = new Player(" ", " ");
    ArrayList<Player> allPlayers = new ArrayList<Player>();
    boolean isTurn = false;
    private boolean haveclientPlayer = false;
    private int numberOfPlayers = 2;
    private boolean gameEnd =false;

    Client(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }
    public void start(){
        try { 
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress("localhost", 4004));
            while(true){
                Listen();
                clientGame();
            }
        } catch (IOException e){
            System.err.println("Error into Client creation " +e.toString());
        }
    }

    public void send(String message) throws IOException{
        ByteBuffer bytes = ByteBuffer.wrap(message.getBytes("UTF-16"));
        while(bytes.hasRemaining()){
            socket.write(bytes);
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
                System.out.println(bytesRead);
                if(bytesRead <= 0){
                    socket.close();
                    return "";
                }
                System.out.println("Into listen func");
                String message = new String(bytes.array(),"UTF-16");
                System.out.println("Print message into Listen " + message);
                if (!haveclientPlayer){
                    System.out.println("haveClientPlayezr = false");
                    getPlayer(message);
                    haveclientPlayer = true;
                    return "";
                } 
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
                allPlayers.add(clientPlayer);
                return;
            }
        }
    }

    private void clientGame(){
        Grid grid;
        switch(numberOfPlayers){
            case 2:
                grid = new Grid(6,8,4);
                break;
            case 3:
                grid = new Grid(8,10,4);
                break;
            default:
                grid = new Grid(6,8,4);
                break;
        }
        try{
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress("localhost", 4004));
            while(!gameEnd){
                grid.printGrid();
                String message = Listen();
                System.out.println("After Listen " + message);
                if (message.trim().equals("Your Turn")){
                    System.out.println(message);
                    Character place = askPlace();
                    String toSend = "Turn " + clientPlayer.symbole + " "  + place;
                    send(toSend);
                } else {
                    Character toPlace = message.charAt(7);
                    System.out.println(toPlace);
                    placeIntoGrid(toPlace);
                }
                
            }
        } catch (IOException e ){
            System.err.println(e.toString());
        }
    }

    protected void winConditionReseau(int line, int column, String symbole){
        if (grid.checkColumn(column, symbole)) gameEnd = true;
        if (grid.checkLine(line, symbole)) gameEnd = true;
        if (grid.checkLeftToRight(line, column, symbole)) gameEnd = true;
        if (grid.checkRightToLeft(line, column, symbole)) gameEnd = true;
    }

    // public String askInfo(String sentenceString){
    //     InputStreamReader bis = new InputStreamReader(System.in);
    //     BufferedReader br = new BufferedReader(bis);
    //     System.out.println(sentenceString);
    //     try {
    //         try {
    //             send(br.readLine());
    //             return "";
    //         }
    //         catch(IOException e){
    //             System.err.println(e.toString());
    //         }
    //         return br.readLine();
    //     }
    //     catch(IOException e){
    //         System.err.println("Something went wrong : " + e.getMessage());
    //         System.err.println("Please retry : ");
    //         return askInfo(sentenceString);
    //     }
    // }
    @Override
    public void generatePlayers(){
    }
    @Override
    protected void lauchGame(){
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
    
}
