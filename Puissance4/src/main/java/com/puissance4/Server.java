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
                    while(true){
                        for (int i=0;i<allClients.size();i++){
                            send("Your Turn", allClients.get(i));
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
}
