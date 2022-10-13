package com.puissance4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client extends Game {

    SocketChannel socket;
    Player clientPlayer;

    Client(){
        try { 
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress("localhost", 4004));
        } catch (IOException e){
            System.err.println(e.toString());
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

    public void Listen(){
        ByteBuffer bytes = ByteBuffer.allocate(1024);
        while(true){
            bytes.clear();
            try {
                int bytesRead = socket.read(bytes);
                if(bytesRead <= 0){
                    socket.close();
                    return;
                }
                String message = new String(bytes.array(),"UTF-16");
                System.out.println(message);
            }catch (IOException e){
                try{
                    socket.close();
                } catch(IOException e2){
                    System.err.println("Error into closing socket "+ e2.toString());
                }
                return;
            }            
        }
    }

    @Override
    public String askInfo(String sentenceString){
        InputStreamReader bis = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(bis);
        System.out.println("Using the ask info override func");
        System.out.println(sentenceString);
        try {
            try {
                send(br.readLine());
            }
            catch(IOException e){
                System.err.println(e.toString());
            }
            return br.readLine();
        }
        catch(IOException e){
            System.err.println("Something went wrong : " + e.getMessage());
            System.err.println("Please retry : ");
            return askInfo(sentenceString);
        }
    }
    @Override
    public void generatePlayers(){
        System.out.println("skip generate player, already make into serveur side");
    }
}
