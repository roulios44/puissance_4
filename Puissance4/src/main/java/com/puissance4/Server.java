package com.puissance4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Server {

    private ArrayList<SocketChannel> allClients = new ArrayList<SocketChannel>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private int numberOfPlayer = 0;

    public static void main(String[] args){
        Server server = new Server();
        server.launch();
    }
    
    private void launch(){
        try {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(4004));
            while(true){
                SocketChannel clientSocket = serverSocket.accept();
                players.add(new Player("Player" + numberOfPlayer+1, PlayerSymbole.values()[numberOfPlayer].toString()));
                allClients.add(clientSocket);
                numberOfPlayer++;
                if (numberOfPlayer == 2){
                    sendPlayersInfo();

                    while(true){
                        for (int i=0;i<allClients.size();i++){
                            System.out.println("Whilel loop server 2");
                            send("Your Turn", allClients.get(i));
                            System.out.println("Message send");
                            Listen(allClients.get(i));

                        }
                    }
                }
            }
        }catch (IOException e){
            System.err.println(e.toString());
            System.err.println("Server stopped due to unexpected exception");
        }
    }

    private void Listen(SocketChannel socket){
        System.out.println("Into listen");
        ByteBuffer bytes = ByteBuffer.allocate(1024);
        bytes.clear();
        try {
            int bytesRead = socket.read(bytes);
            if(bytesRead <= 0){
                socket.close();
                return;
            }
            String message = new String(bytes.array(),"UTF-16");
            System.out.println("Message " +message);
            for (int i =0;i<allClients.size();i++) {
                if (socket != allClients.get(i))send("Turn " + message + " " + players.get(i).symbole, allClients.get(i));
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

    private void send(String message, SocketChannel socket) throws IOException{
        ByteBuffer bytes = ByteBuffer.wrap(message.getBytes("UTF-16"));
        while(bytes.hasRemaining()){
            socket.write(bytes);
        }
    }

    private void sendPlayersInfo(){
        try {
            for (int i=0;i<allClients.size();i++){
                // Send at every client their symbole
                send(players.get(i).symbole, allClients.get(i));
            }
            // Send at every client other palyer symbole
        } catch (IOException e) {
            System.err.println("Error int serveru sendPlayerInfo " + e.toString());
        }
    }

}
