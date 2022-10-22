package com.puissance4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Server class is the class for the server
 */
public class Server implements Runnable {

    private ArrayList<SocketChannel> allClients = new ArrayList<SocketChannel>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private int numberOfPlayer = 0;
    private int playersNeeded;
    private String messageListen;

    Server(int numberOfPlayer){
        this.playersNeeded = numberOfPlayer;
    }
    
    private void launch(){
        try {
            // Open the server at the 4004 port
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(4004));
            // First while loop is a step of admission of players, loop break when they are enough players
            while(true){
                SocketChannel clientSocket = serverSocket.accept();
                players.add(new Player("Player" + numberOfPlayer+1, PlayerSymbole.values()[numberOfPlayer].toString()));
                System.out.println("Client connected");
                allClients.add(clientSocket);
                String sPlayerNeeded = String.valueOf(playersNeeded);
                send("Players " + sPlayerNeeded,clientSocket);
                numberOfPlayer++;
                if (numberOfPlayer == playersNeeded)break;
            }
            // shuffle array of allClients to make a random play order
            Collections.shuffle(allClients);
            sendPlayersInfo();
            while(true){
                for (int i=0;i<allClients.size();i++){
                    send("Your Turn", allClients.get(i));
                    Listen(allClients.get(i),serverSocket);
                    if(messageListen.trim().equals("STOP")){
                        serverSocket.close();
                        return;
                    }
                    if(messageListen.trim().equals("DROW")){
                        serverSocket.close();
                        return;
                    }
                }
            }
        }catch (IOException e){
            System.err.println(e.toString());
            System.err.println("Server stopped due to unexpected exception");
        }
    }

    // Func to listen what client sends
    private void Listen(SocketChannel socket, ServerSocketChannel serveurSocket){
        ByteBuffer bytes = ByteBuffer.allocate(1024);
        bytes.clear();
        try {
            int bytesRead = socket.read(bytes);
            if(bytesRead <= 0){
                socket.close();
                return;
            }
            String message = new String(bytes.array(),"UTF-16");
            messageListen = message;
            for (int i =0;i<allClients.size();i++) {
                if (socket != allClients.get(i))send(message, allClients.get(i));
            }
            return;
        }catch (IOException e){
            try{
                socket.close();
            } catch (IOException e2){
                System.err.println("error into close socket "+ e2.toString());
            }
            return;
        } 
    }

    // Func that take a message(String) and a SocketChannel to send the message to the socketChannel
    private void send(String message, SocketChannel socket) throws IOException{
        ByteBuffer bytes =  ByteBuffer.wrap(message.getBytes("UTF-16"));
        while(bytes.hasRemaining()){
            socket.write(bytes);
        }
    }

    // Func that send to all players the symbole in this game
    private void sendPlayersInfo(){
        try {
            // make server sleep for 100 ms to avoid double send by the server
            Thread.sleep(100);
            for (int i=0;i<allClients.size();i++){
                // Send at every client their symbole
                send(players.get(i).symbole, allClients.get(i));
            }
        } catch (IOException e) {
            System.err.println("Error into serveur sendPlayerInfo " + e.toString());
        }
        catch (InterruptedException eTime){
            System.err.println(eTime.toString());
        }
    }

    // Override method run, to make Thread work
    @Override
    public void run(){
        launch();
    }
}
