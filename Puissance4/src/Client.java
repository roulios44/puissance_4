import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args){
        try {
            SocketChannel clientSocket = SocketChannel.open();
            clientSocket.connect(new InetSocketAddress("localhost", 8000));
            
            ByteBuffer bytes = ByteBuffer.wrap(message.getBytes("UTF-8"));
            while(bytes.hasRemaining()){
                clientSocket.write(bytes);
            }
            clientSocket.close();
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
}

